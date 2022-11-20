package hi.HBV501G.kritikin.services;

import java.util.List;

import hi.HBV501G.kritikin.persistence.entites.Review;
import hi.HBV501G.kritikin.persistence.entites.DTOs.ReviewJSON;

public interface ReviewService {
    public void save(Review review);

    public void delete(Review review);

    public Review findById(long id);

    public List<Review> findAll();

    public List<ReviewJSON> findByCompany(long companyId);

    public List<ReviewJSON> findByUser(long userId);

    public Review addReview(Review review, long userId, long companyId);
}
