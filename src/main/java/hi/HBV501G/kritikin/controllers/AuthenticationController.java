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
    UserService userService;
    CompanyUserService companyUserService;

    @Autowired
    public AuthenticationController(UserService userService, CompanyUserService companyUserService) {
        this.userService = userService;
        this.companyUserService = companyUserService;
    }

    @GetMapping(value = HomeController.APIURL + "/users")
    public List<User> fetchAllUsers() {
        return userService.findAll();
    }

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
