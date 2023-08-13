package com.vtm.config.security;
import com.vtm.exception.EErrorType;
import com.vtm.exception.VTMProjectException;
import com.vtm.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter{
    @Autowired
    JwtTokenManager jwtTokenManager;
    @Autowired
    JwtUserDetail jwtUserDetail;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeaderBearerToken = request.getHeader("Authorization");
        System.out.println("SecurityConfig ==> "+ authHeaderBearerToken);

        if(authHeaderBearerToken!=null && authHeaderBearerToken.startsWith("Bearer ")
                &&  SecurityContextHolder.getContext().getAuthentication() == null
        )
        {
            String token = authHeaderBearerToken.substring(7);
            Optional<Long> userId =  jwtTokenManager.getIdFromToken(token);
            if(userId.isEmpty())
                throw new VTMProjectException(EErrorType.INVALID_TOKEN);

            UserDetails userDetails = jwtUserDetail.getUserByAuthId(userId.get());

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,null,userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }
        filterChain.doFilter(request,response);
    }
}
