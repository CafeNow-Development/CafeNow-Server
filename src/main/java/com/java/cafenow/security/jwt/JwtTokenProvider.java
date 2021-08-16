package com.java.cafenow.security.jwt;

import com.java.cafenow.kakao_login.domain.Admin;
import com.java.cafenow.kakao_login.domain.enumType.Role;
import com.java.cafenow.security.authectication.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;
    private final MyUserDetails myUserDetails;

    final static public String ACCESS_TOKEN_NAME = "accessToken";
    public final static long TOKEN_VALIDATION_SECOND = 1000L * 3600 * 24;  //하루를 accessToken 만료 기간으로 잡는다

    // Base64 encoded secret key
    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    /**
     * token을 복호화 하여 claim에서 사용자 이름(Username)을 가져오는 메소드입니다.
     * @param token Username을 추출 할 token
     * @return username
     */
    public String getUsername(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * 사용자의 인증정보를 조회하는 메소드입니다.
     * @param token 사용자의 인증정보를 조회 할 token
     * @return 인증정보 (true || false)
     */
    public Authentication getAuthentication(String token){
        UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * 사용자의 이름과 권한을 이용해서 accessToken을 만드는 메소드입니다.
     * @return accessToken
     */
    public String createToken(Admin admin){
        Claims claims = Jwts.claims().setSubject(admin.getUsername());
        claims.put("name", admin.getName());
        claims.put("role", admin.getRole());
        claims.put("provider", admin.getProvider());

        Date now = new Date();
        Date validity = new Date(now.getTime() + TOKEN_VALIDATION_SECOND); //Expire Time

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * http header에서 accessToken을 가져오는 메소드입니다.
     * @param req HttpServletRequest
     * @return true = accesstoken, false = null
     */
    public String resolveToken(HttpServletRequest req){
        String bearerToken = req.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return  bearerToken.substring(7);
        } else {
            return null;
        }
    }

    /**
     * token을 검증하는 메소드 (유효성, 만료일자 검증)
     * @param token 검증 할 token
     * @return 토큰이 검증됨 = true, 토큰이 검증되지않음 = false
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date()); //유효기간 만료 시 false 반환
        } catch (Exception e) { // 나중에 유효하지 않은 토큰입니다 Exception으로 변경?
            log.debug(e.getMessage()); //나중에 어떤 Exception이 터지는지 확인하기 위해 logging
            SecurityContextHolder.clearContext();
            return false;
        }
    }
}
