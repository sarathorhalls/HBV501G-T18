package hi.HBV501G.kritikin.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    Logger logger = LoggerFactory.getLogger(CustomAuthenticationManager.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.info("Attmepting to authenticate user: {}", authentication.getName());
        final UserDetails userDetail = userDetailsService.loadUserByUsername(authentication.getName());
        if (userDetail == null) {
            logger.error("User: {}, not found", authentication.getName());
            throw new BadCredentialsException("User not found");
        }
        if (!passwordEncoder.matches(authentication.getCredentials().toString(), userDetail.getPassword())) {
            logger.error("Wrong password");
            throw new BadCredentialsException("Wrong password");
        }
        logger.info("User: {}, authenticated", userDetail.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetail.getUsername(), userDetail.getPassword(),
                userDetail.getAuthorities());
    }
}
