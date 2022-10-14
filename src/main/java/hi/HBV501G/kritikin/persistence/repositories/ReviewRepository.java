package hi.HBV501G.kritikin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hi.HBV501G.kritikin.persistence.entites.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review save(Review review);

    void delete(Review review);

    List<Review> findAll();

    Review findById(long id);

    List<Review> findByCompany(long companyId);

    List<Review> findByUser(long userId);
}
