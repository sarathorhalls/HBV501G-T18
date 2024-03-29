package hi.HBV501G.kritikin.controllers;

import java.io.IOException;
import java.net.URI;
import java.util.Base64;
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

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import hi.HBV501G.kritikin.persistence.entites.Authority;
import hi.HBV501G.kritikin.persistence.entites.Company;
import hi.HBV501G.kritikin.persistence.entites.EAuthority;
import hi.HBV501G.kritikin.persistence.entites.User;
import hi.HBV501G.kritikin.services.CompanyService;
import hi.HBV501G.kritikin.services.UserService;

@RestController
@CrossOrigin()
public class AuthenticationController {

    private UserService userService;
    private CompanyService companyService;

    Logger logger = org.slf4j.LoggerFactory.getLogger(AuthenticationController.class);

    /**
     * Constructor for the AuthenticationController which uses Autowired to inject
     * the UserService and CompanyUserService from JPA
     * 
     * @param userService
     * @param companyUserService
     */
    @Autowired
    public AuthenticationController(UserService userService, CompanyService companyService) {
        this.userService = userService;
        this.companyService = companyService;
    }

    /**
     * Returns a list of all users as a json object from a get request to /api/users
     * .
     * 
     * @return a list of all users
     */
    @GetMapping(value = CompanyController.APIURL + "/users")
    public ResponseEntity<List<User>> fetchAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * Adds a user to the database from a post request to /api/user with the user
     * to be inserted in the body.
     * 
     * @param user the user to be inserted, fetched from the body of the post
     *             request
     * @return the user that was inserted
     */
    @PostMapping(value = CompanyController.APIURL + "/auth/signup")
    public ResponseEntity<User> addUser(User user) {
        if (user == null || user.getUsername() == null || user.getUsername().equals("")) {
            return ResponseEntity.badRequest().body(null);
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>(null, null, HttpStatus.CONFLICT);
        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(CompanyController.APIURL + "/auth/signup").toUriString());
        return ResponseEntity.created(uri).body(userService.save(user));
    }

    /**
     * Deletes a user from the database from a delete request to /api/users/{id} .
     * 
     * @param id the id of the user to be deleted
     * @return the user that was deleted
     */
    @DeleteMapping(value = CompanyController.APIURL + "/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
        }
        userService.delete(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping(value = CompanyController.APIURL + "/companyUser/{companyId}")
    public ResponseEntity<String> redeemAccess(@PathVariable long companyId, @RequestHeader("Authorization") String auth) {
        logger.info("redeemAccess() called with authorization header: {}", auth);
        String token = auth.replace("Bearer ", "").split("\\.")[1];
        String decodedPayload = new String(Base64.getDecoder().decode(token));
        try {
            Long userId = Long.parseLong(decodedPayload.split(",")[1].split(":")[1].split("\"")[1]);
            logger.info("User with id {} and username {} is requesting access to company with id {} and name {}", userId,
                    userService.findById(userId).getUsername(), companyId, companyService.findById(companyId).getName());
            return ResponseEntity.ok().body("Access requested");
        } catch (Exception e) {
            logger.error("Error parsing user id from token: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Error parsing user id from token: " + e.getMessage());
        }
    }

    /**
     * Returns a new access token for the user with the given refreshToken from the
     * authorization bearer header.
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping(value = CompanyController.APIURL + "/auth/refreshToken")
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
                        .withAudience(Long.toString(user.getId()))
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

    @PatchMapping(value = CompanyController.APIURL + "/users/{id}")
    public ResponseEntity<?> setManagedCompanyToUser(@PathVariable("id") long userId, long companyId) {
        User user = userService.findById(userId);
        if (user == null) {
            return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
        }
        Company company = companyService.findById(companyId);
        user.setManagedCompany(company);
        user.addAuthority(new Authority(EAuthority.ROLE_COMPANY));
        userService.save(user);
        return ResponseEntity.ok(user);
    }
}
