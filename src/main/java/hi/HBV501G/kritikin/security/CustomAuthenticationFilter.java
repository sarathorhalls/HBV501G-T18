package hi.HBV501G.kritikin.security;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

        Logger logger = LoggerFactory.getLogger(CustomAuthenticationFilter.class);

        private final AuthenticationManager authenticationManager;

        public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
                this.authenticationManager = authenticationManager;
        }

        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
                        throws AuthenticationException {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                logger.info("attempted authentication with username: {} and password: {}", username, password);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                username,
                                password);
                return authenticationManager.authenticate(authenticationToken);
        }

        /**
         * 
         */
        @Override
        protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                        FilterChain chain,
                        Authentication authentication) throws IOException, ServletException {
                org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication
                                .getPrincipal();
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                String accessToken = JWT.create()
                                .withSubject(user.getUsername())
                                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                                .withIssuer(request.getRequestURL().toString())
                                .withClaim("authorities",
                                                user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                                                .collect(Collectors.toList()))
                                .sign(algorithm);
                String refreshToken = JWT.create()
                                .withSubject(user.getUsername())
                                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                                .withIssuer(request.getRequestURL().toString())
                                .sign(algorithm);
                // response.setHeader("access_token", accessToken);
                // response.setHeader("refresh_token", refresh_token);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
        }

}
