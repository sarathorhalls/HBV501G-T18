package hi.HBV501G.kritikin.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hi.HBV501G.kritikin.persistence.entites.Review;
import hi.HBV501G.kritikin.services.CompanyService;
import hi.HBV501G.kritikin.services.ReviewService;
import hi.HBV501G.kritikin.services.UserService;

@RestController
public class ReviewController {
    CompanyService companyService;
    ReviewService reviewService;
    UserService userService;

    /**
     * Constructor for ReviewController which uses autowired to inject the
     * CompanyService and UserService from JPA
     * 
     * @param companyService
     * @param userService
     * @param reviewController
     */
    @Autowired
    public ReviewController(CompanyService companyService, UserService userService, ReviewService reviewService) {
        this.companyService = companyService;
        this.userService = userService;
        this.reviewService = reviewService;
    }

    /**
     * Returns a list of all reviews for a particular company with the given id
     * 
     * @param id the id of the company
     * @return a list of all reviews for a particular company
     */
    @GetMapping(value = HomeController.APIURL + "/company/{id}/reviews")
    public List<Review> fetchReviewsByCompany(@PathVariable long id) {
        return reviewService.findByCompany(id);
    }

    /**
     * Returns a list of all reviews for a particular user with the given id
     * 
     * @param id the id of the user
     * @return a list of all reviews for a particular user
     */
    @GetMapping(value = HomeController.APIURL + "/user/{id}/reviews")
    public List<Review> fetchReviewsByUser(@PathVariable long id) {
        return reviewService.findByUser(id);
    }

    @PostMapping(value = HomeController.APIURL + "/company/{id}/reviews")
    public Review addReview(@RequestBody Review review, @RequestParam long userId, @PathVariable("id") long companyId) {
        return reviewService.addReview(review, userId, companyId);
    }
}
