package hi.HBV501G.kritikin.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * This class is the controller for the authentication of users. It handles the
 * login and registration of users from REST requests.
 * Still Work In Progress
 * 
 * @author Sara Þórhallsdóttir
 */

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import hi.HBV501G.kritikin.persistence.entites.Authority;
import hi.HBV501G.kritikin.persistence.entites.User;
import hi.HBV501G.kritikin.services.CompanyUserService;
import hi.HBV501G.kritikin.services.UserService;

@RestController
public class AuthenticationController {
    private UserService userService;
    private CompanyUserService companyUserService;

    /**
     * Constructor for the AuthenticationController which uses Autowired to inject
     * the UserService and CompanyUserService from JPA
     * 
     * @param userService
     * @param companyUserService
     */
    @Autowired
    public AuthenticationController(UserService userService, CompanyUserService companyUserService) {
        this.userService = userService;
        this.companyUserService = companyUserService;
    }

    /**
     * Returns a list of all users as a json object from a get request to /api/users
     * .
     * 
     * @return a list of all users
     */
    @GetMapping(value = HomeController.APIURL + "/users")
    public List<User> fetchAllUsers() {
        return userService.findAll();
    }

    /**
     * Adds a user to the database from a post request to /api/user with the user
     * to be inserted in the body.
     * 
     * @param user the user to be inserted, fetched from the body of the post
     *             request
     * @return the user that was inserted
     */
    @PostMapping(value = HomeController.APIURL + "/user")
    public User addUser(@RequestBody User user) {
        if (user == null || user.getUsername() == null || user.getUsername().equals("")) {
            return null;
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            return null;
        }
        userService.save(user);
        return user;
    }

    /**
     * Deletes a user from the database from a delete request to /api/users/{id} .
     * 
     * @param id the id of the user to be deleted
     * @return the user that was deleted
     */
    @DeleteMapping(value = HomeController.APIURL + "/users/{id}")
    public User deleteUser(@PathVariable long id) {
        User user = userService.findById(id);
        if (user == null) {
            return null;
        }
        userService.delete(user);
        return user;
    }

    /**
     * Returns a new access token for the user with the given refreshToken from the
     * authorization bearer header.
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping(value = HomeController.APIURL + "/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                User user = userService.findByUsername(username);
                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("authorities",
                                user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(403);
                // response.sendError(403);
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
