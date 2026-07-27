package com.example.demo.global.security.jwt;

import com.example.demo.domain.user.refreshToken.RefreshToken;
import com.example.demo.domain.user.refreshToken.RefreshTokenRepository;
import com.example.demo.global.response.dto.ResponseTokenDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.demo.global.security.SecurityConfig.hashToken;
import static com.example.demo.global.security.jwt.jwtFilter.AUTHORIZATION_HEADER;
import static com.example.demo.global.security.jwt.jwtFilter.BEARER_PREFIX;

@Component
public class JwtTokenProvider {
    private final SecretKey key;
    private final RefreshTokenRepository refreshTokenRepository;
    private final Long expAccessTime=1000L*60*30;
    private final Long expRefreshTime=1000L*60*60*24*7;
    private static final String AUTHORITIES_KEY = "rols";
    private final JwtProperties jwtProperties;
    public JwtTokenProvider(
            JwtProperties jwtProperties,
            RefreshTokenRepository refreshTokenRepository
    ){
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtProperties=jwtProperties;
        byte[] temp = jwtProperties.secretKey().getBytes();
        this.key = Keys.hmacShaKeyFor(temp);
    }
    //토큰 생성
    private String createToken(String sub, Long expTime, Map<String,?> customData){
        Date now = new Date();
        Date expiration = new Date(now.getTime()+expTime);
        return Jwts.builder()
                .claims(customData)
                .subject(sub)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }
    private String createAccessToken(String sub, Map<String,?> customData){
        return createToken(sub, expAccessTime, customData);
    }
    private String createRefreshToken(String sub, Map<String,?> customData){
        String token = createToken(sub, expRefreshTime, customData);
        try {
            refreshTokenRepository.save(
                    new RefreshToken(
                            sub,
                            hashToken(token),
                            new Date(new Date().getTime()+expRefreshTime)
                    )
            );
            return token;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseTokenDTO createJwt(String sub, Map<String,?> customData){
        return new ResponseTokenDTO(createAccessToken(sub, customData), createRefreshToken(sub, customData));
    }
    public ResponseTokenDTO createJwt(String sub){
        return createJwt(sub, new HashMap<>());
    }

    public Claims jwtParser(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getSub(String token){
        return jwtParser(token).getSubject();
    }

    //jwt필터에서 사용하는 메서드
    //헤더에서 토큰값만 추출, 없거나 틀리면 null
    private String resolveToken(HttpServletRequest request) {
        // Authorization: 토큰 형태의 문자열을 읽음
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        // 값에 내용이 있고, "Bearer "로 시작하는지 검증합니다.
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            // "Bearer " (7자) 이후의 토큰 값만 잘라내어 반환
            return bearerToken.substring(BEARER_PREFIX.length());
        }

        return null; // 헤더에 토큰이 없거나 잘못된 형식인 경우 null 반환
    }
    //jwt 토큰 검증
    public boolean validateToken(String token) {
        try {
            // 토큰 파싱 시도 (비밀키로 서명 검증 및 만료 시간 확인)
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new IllegalArgumentException("잘못된 JWT 서명입니다.", e);
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("만료된 JWT 토큰입니다.", e);
        } catch (UnsupportedJwtException e) {
            throw new IllegalArgumentException("지원되지 않는 JWT 토큰입니다.", e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("JWT 토큰이 잘못되었습니다.", e);
        }
    }

    // JWT 토큰에서 Authentication 객체 추출
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화하여 내부 Claim 추출
        Claims claims = jwtParser(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기 (예: "ROLE_USER,ROLE_ADMIN")
        //SimpleGrantedAuthority으로 이루어진 리스트(authorities)에 저장
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어 Authentication 리턴
        //(DB 조회 없이 토큰 정보만으로 생성)
        //UserDetails : 스프링 시큐리티에서 "사용자 정보"를 다루기 위한 표준 규격
        //(인터페이스)
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        //UsernamePasswordAuthenticationToken: Authentication를 구현한 실체 클래스
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }
}
