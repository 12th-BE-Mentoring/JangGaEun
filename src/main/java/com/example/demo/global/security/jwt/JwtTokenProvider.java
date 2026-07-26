package com.example.demo.global.security.jwt;

import com.example.demo.domain.user.refreshToken.RefreshToken;
import com.example.demo.domain.user.refreshToken.RefreshTokenRepository;
import com.example.demo.global.response.dto.ResponseTokenDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.demo.global.security.SecurityConfig.hashToken;

@Component
public class JwtTokenProvider {
    private final SecretKey key;
    private final RefreshTokenRepository refreshTokenRepository;
    private final Long expAccessTime=1000L*60*30;
    private final Long expRefreshTime=1000L*60*60*24*7;
    public JwtTokenProvider(@Value("jwt.key")String key, RefreshTokenRepository refreshTokenRepository){
        this.refreshTokenRepository = refreshTokenRepository;
        byte[] temp = key.getBytes();
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
}
