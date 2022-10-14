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

    @Autowired
    public ReviewServiceImplementation(ReviewRepository reviewRepository, CompanyService companyService,
            UserService userService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
        this.userService = userService;
    }

    @Override
    public void save(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public void delete(Review review) {
        reviewRepository.delete(review);
    }

    @Override
    public Review findById(long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> findByCompany(long companyId) {
        return reviewRepository.findByCompany(companyId);
    }

    @Override
    public List<Review> findByUser(long userId) {
        return reviewRepository.findByUser(userId);
    }

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
