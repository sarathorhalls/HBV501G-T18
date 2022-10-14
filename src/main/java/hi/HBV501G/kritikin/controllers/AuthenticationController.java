package hi.HBV501G.kritikin.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hi.HBV501G.kritikin.persistence.entites.User;
import hi.HBV501G.kritikin.services.CompanyUserService;
import hi.HBV501G.kritikin.services.UserService;

@RestController
public class AuthenticationController {
    // TODO: use actual authentication

    UserService userService;
    CompanyUserService companyUserService;

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
     * Adds a user to the database from a post request to /api/users with the user
     * to be inserted in the body.
     * 
     * @param user the user to be inserted, fetched from the body of the post
     *             request
     * @return the user that was inserted
     */
    @PostMapping(value = HomeController.APIURL + "/users")
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
}
