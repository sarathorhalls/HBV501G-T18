package hi.HBV501G.kritikin.services;

import java.util.List;

import hi.HBV501G.kritikin.persistence.entites.Review;

public interface ReviewService {
    public void save(Review review);
    public void delete(Review review);
    public Review findById(long id);
    public List<Review> findAll();
    public List<Review> findByCompany(long companyId);
    public List<Review> findByUser(long userId);
}
