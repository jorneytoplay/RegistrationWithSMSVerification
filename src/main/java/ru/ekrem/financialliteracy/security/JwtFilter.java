package ru.ekrem.financialliteracy.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.ekrem.financialliteracy.dto.MyUserDetails;
import ru.ekrem.financialliteracy.entity.Role;
import ru.ekrem.financialliteracy.entity.RoleEnum;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = getTokenFromRequest(request);
        if(token!=null && jwtProvider.validateAccessToken(token)){
            final Claims claims = jwtProvider.getAccessClaims(token);
            System.out.println(token);
            // Парсинг роли
            LinkedHashMap roleMap = claims.get("role", LinkedHashMap.class);
            Set<Role> roles;

            if (roleMap != null) {
                Role role = new Role();
                role.setId(Long.valueOf((Integer) roleMap.get("id")));
                role.setRole((String) roleMap.get("role"));
                roles = Collections.singleton(role);
            } else {
                roles = Collections.emptySet();
            }




            List<GrantedAuthority> authorities = new ArrayList<>();
            for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
            }
            boolean phoneVerified = (Boolean) claims.get("phoneVerified");

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
            auth.setDetails(phoneVerified);
            SecurityContextHolder.getContext().setAuthentication(auth);


        }
        filterChain.doFilter(request,response);
    }


    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
