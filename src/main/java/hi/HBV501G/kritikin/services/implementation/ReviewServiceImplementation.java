package hi.HBV501G.kritikin.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hi.HBV501G.kritikin.persistence.entites.Review;
import hi.HBV501G.kritikin.persistence.repositories.ReviewRepository;
import hi.HBV501G.kritikin.services.ReviewService;

@Service
public class ReviewServiceImplementation implements ReviewService {

    ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImplementation(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void save(Review review) {
        
    }

    @Override
    public void delete(Review review) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Review findById(long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Review> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Review> findByCompany(long companyId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Review> findByUser(long userId) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
