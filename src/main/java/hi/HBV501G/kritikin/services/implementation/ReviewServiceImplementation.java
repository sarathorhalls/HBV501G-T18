package hi.HBV501G.kritikin.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hi.HBV501G.kritikin.persistence.entites.Review;
import hi.HBV501G.kritikin.persistence.repositories.ReviewRepository;
import hi.HBV501G.kritikin.services.CompanyService;
import hi.HBV501G.kritikin.services.ReviewService;
import hi.HBV501G.kritikin.services.UserService;

@Service
public class ReviewServiceImplementation implements ReviewService {

    ReviewRepository reviewRepository;
    CompanyService companyService;
    UserService userService;

    /**
     * Constructor for ReviewServiceImplementation which uses AutoWired to inject
     * the ReviewRepository, CompanyService and UserService from JPA.
     * 
     * @param reviewRepository
     * @param companyService
     * @param userService
     */
    @Autowired
    public ReviewServiceImplementation(ReviewRepository reviewRepository, CompanyService companyService,
            UserService userService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
        this.userService = userService;
    }

    /**
     * Saves a review to the database.
     * 
     * @param review the review to be saved.
     */
    @Override
    public void save(Review review) {
        reviewRepository.save(review);
    }

    /**
     * Deletes a review from the database.
     * 
     * @param review the review to be deleted.
     */
    @Override
    public void delete(Review review) {
        reviewRepository.delete(review);
    }

    /**
     * Returns a review from the database with a given id.
     * 
     * @param id the id of the review.
     * @return the review with the given id.
     */
    @Override
    public Review findById(long id) {
        return reviewRepository.findById(id);
    }

    /**
     * Returns a list of all reviews from the database.
     * 
     * @return a list of all reviews.
     */
    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    /**
     * Returns a list of all reviews from the database with a given company id.
     * 
     * @param companyId the id of the company.
     * @return a list of all reviews with the given company id.
     */
    @Override
    public List<Review> findByCompany(long companyId) {
        return reviewRepository.findByCompany(companyId);
    }

    /**
     * Returns a list of all reviews from the database with a given user id.
     * 
     * @param userId the id of the user.
     * @return a list of all reviews with the given user id.
     */
    @Override
    public List<Review> findByUser(long userId) {
        return reviewRepository.findByUser(userId);
    }

    /**
     * Adds a review to the database with a given company id and user id.
     * 
     * @param review    the review to be added.
     * @param userId    the id of the user.
     * @param companyId the id of the company.
     * @return the review that was added.
     */
    @Override
    public Review addReview(Review review, long userId, long companyId) {
        if (review == null) {
            return null;
        }
        if (review.getCompany() == null) {
            review.setCompany(companyService.getReferenceById(companyId));
        }
        if (review.getUser() == null) {
            review.setUser(userService.getReferenceById(userId));
        }
        reviewRepository.save(review);
        return review;
    }

}
