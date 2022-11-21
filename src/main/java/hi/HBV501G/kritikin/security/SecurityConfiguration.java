package hi.HBV501G.kritikin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import hi.HBV501G.kritikin.controllers.CompanyController;
import hi.HBV501G.kritikin.services.UserService;

@Configuration
@EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

        private final UserService userService;

        @Autowired
        public SecurityConfiguration(UserService userService) {
                this.userService = userService;
        }

    /**
     * Allows us to use the AuthenticationManager in our custom filter
     * 
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Handles the security of the application through the use of filters
     * 
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Create the authentication filter
        CustomAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter(
                authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), userService);
        // Set the filter login url to /api/login
        String APIURL = CompanyController.APIURL;
        authenticationFilter.setFilterProcessesUrl(APIURL + "/auth/signin");

        http.cors()
                .and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.GET, APIURL + "/companies", APIURL + "/company/**",
                        APIURL + "auth/refreshtoken/**", APIURL + "/auth/signup/**", APIURL + "/users/**", APIURL + "/findCompany/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, APIURL + "/auth/signup/**").permitAll()
                .antMatchers(HttpMethod.POST, APIURL + "/company/**/review/**").authenticated()
                .antMatchers(HttpMethod.POST, APIURL + "/company/**/question/**").authenticated()
                .antMatchers(HttpMethod.POST, APIURL + "/company/**/answer/**").hasRole("COMPANY")
                .antMatchers(HttpMethod.DELETE, APIURL + "/company/**", APIURL + "/users/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, APIURL + "/company/**").hasRole("ADMIN")
                .and().httpBasic()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilter(authenticationFilter)
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
