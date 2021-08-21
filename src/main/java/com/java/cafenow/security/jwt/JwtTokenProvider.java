package com.java.cafenow.security.jwt;

import com.java.cafenow.kakao_login.domain.Admin;
import com.java.cafenow.kakao_login.domain.enumType.Role;
import com.java.cafenow.security.authectication.MyUserDetails;
import com.java.cafenow.staff.domain.Staff;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey;
    private final MyUserDetails myUserDetails;

    final static public String ACCESS_TOKEN_NAME = "accessToken";
    public final static long TOKEN_VALIDATION_SECOND = 1000L * 3600 * 24;  //하루를 accessToken 만료 기간으로 잡는다

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String getUsername(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String getRole(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("auth", String.class);
    }

    public String createTokenAdmin(Admin admin){
        Claims claims = Jwts.claims().setSubject(admin.getUsername());
        claims.put("auth", admin.getRole());

        Date now = new Date();
        Date validity = new Date(now.getTime() + TOKEN_VALIDATION_SECOND); //Expire Time

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createTokenStaff(Staff staff){
        Claims claims = Jwts.claims().setSubject(staff.getStaffEmail());
        claims.put("auth", staff.getRole());

        Date now = new Date();
        Date validity = new Date(now.getTime() + TOKEN_VALIDATION_SECOND); //Expire Time

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    public String resolveToken(HttpServletRequest req){
        String bearerToken = req.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return  bearerToken.substring(7);
        } else {
            return null;
        }
    }

    public Authentication getAuthentication(String token){
        String role = getRole(token);
        if (role.equals("ROLE_ADMIN")) {
            UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        } else {
            UserDetails userDetails = myUserDetails.CustomLoadUserByUsername(getUsername(token));
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        }
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}