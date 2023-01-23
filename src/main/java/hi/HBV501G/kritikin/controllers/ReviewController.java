package hi.HBV501G.kritikin.controllers;

import java.net.URI;
import java.util.Base64;

/**
 * This class is the controller for everything related to reviews. It handles the
 * creation, deletion and updating of reviews from REST requests.
 * 
 * @author Sara Þórhallsdóttir
 */

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import hi.HBV501G.kritikin.persistence.entites.Review;
import hi.HBV501G.kritikin.persistence.entites.DTOs.ReviewJSON;
import hi.HBV501G.kritikin.services.ReviewService;

@RestController
@CrossOrigin()
public class ReviewController {

    private ReviewService reviewService;

    Logger logger = org.slf4j.LoggerFactory.getLogger(ReviewController.class);

    /**
     * Constructor for ReviewController which uses autowired to inject the
     * ReviewService with JPA
     * 
     * @param reviewService
     */
    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * Returns a list of all reviews as a json object for a particular company with
     * the given id from a get request to /api/company/{id}/reviews .
     * 
     * @param id the id of the company
     * @return a list of all reviews for a particular company
     */
    @GetMapping(value = CompanyController.APIURL + "/company/{id}/reviews")
    public ResponseEntity<List<ReviewJSON>> fetchReviewsByCompany(@PathVariable long id) {
        return ResponseEntity.ok(reviewService.findByCompany(id));
    }

    /**
     * Returns a list of all reviews as a json object for a particular user with the
     * given id from a get request to /api/user/{id}/reviews .
     * 
     * @param id the id of the user
     * @return a list of all reviews for a particular user
     */
    @GetMapping(value = CompanyController.APIURL + "/user/{id}/reviews")
    public ResponseEntity<List<ReviewJSON>> fetchReviewsByUser(@PathVariable long id) {
        return ResponseEntity.ok(reviewService.findByUser(id));
    }

    /**
     * Adds a review to the database from a post request to
     * /api/company/{id}/review with the review to be inserted in the body, the
     * user id in the request parameter and the company id in the path.
     * 
     * @param review    the review to be inserted, fetched from the body of the post
     *                  request
     * @param userId    the id of the user who wrote the review, fetched from the
     *                  request parameter
     * @param companyId the id of the company the review is about, fetched from the
     *                  path
     * @return the review that was inserted
     */
    @PostMapping(value = CompanyController.APIURL + "/company/{id}/review")
    public ResponseEntity<Review> addReview(@RequestBody Review review, @PathVariable("id") long companyId,
            @RequestHeader("Authorization") String auth) {
        logger.info("addReview() called with authorization header: {}", auth);
        String token = auth.replace("Bearer ", "").split("\\.")[1];
        String decodedPayload = new String(Base64.getDecoder().decode(token));
        try {
            Long userId = Long.parseLong(decodedPayload.split(",")[1].split(":")[1].split("\"")[1]);
            logger.info("User id: {}", userId);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(CompanyController.APIURL + "/company/" + companyId + "/review").toUriString());
            return ResponseEntity.created(uri).body(reviewService.addReview(review, userId, companyId));
        } catch (Exception e) {
            logger.error("Error parsing user id from token: {}", e.getMessage());
            return ResponseEntity.badRequest().body(review);
        }

    }
}
