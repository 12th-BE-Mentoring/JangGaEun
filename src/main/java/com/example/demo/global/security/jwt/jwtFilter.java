package com.example.demo.global.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class jwtFilter extends OncePerRequestFilter {

    // HTTP 요청 헤더에서 인증 정보를 담을 헤더 이름
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 토큰 접두사 (Bearer 방식 사용)
    public static final String BEARER_PREFIX = "Bearer ";
    // JWT 토큰 생성, 검증 및 Authentication 객체 생성을 담당하는 컴포넌트
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. 해더에서 토큰 추출. 없으면 null반환
        String jwt = jwtTokenProvider.resolveToken(request);

        // 2-1 토큰이 존재하고 유효성 검증을 통과한 경우에만 처리합니다.
        if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
            //StringUtils.hasText(Str) = 문자열이 null과 ""이 아닌경우 true

            //2-2 토큰의 정보를 추출 후 시큐리티 내부 저장소에 저장
            // 토큰 내부 데이터(Claims)를 기반으로 Security용 Authentication 객체를 만듭니다.
            Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
            // SecurityContextHolder에 Authentication 객체를 저장합니다.
            // 이 작업을 거치면 해당 요청(Request) 동안 컨트롤러 등에서 로그인된 사용자 정보를 조회할 수 있게 됩니다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 3. 다음 필터로 요청을 전달
        // 토큰이 없거나 유효하지 않아도 다음 필터로 넘어간 뒤, Spring Security 설정에 따라 접근 거부(401/403) 처리됩니다.
        filterChain.doFilter(request, response);
    }
}
