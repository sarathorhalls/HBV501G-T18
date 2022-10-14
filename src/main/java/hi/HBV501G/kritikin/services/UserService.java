package hi.HBV501G.kritikin.services;

import java.util.List;

import hi.HBV501G.kritikin.persistence.entites.Question;
import hi.HBV501G.kritikin.persistence.entites.Review;
import hi.HBV501G.kritikin.persistence.entites.User;

public interface UserService {
    public void save(User user);
    public void delete(User user);
    public User findById(long id);
    public User findByUsername(String username);
    public List<User> findAll();
    public Review addReview(Review review, long userId, long companyId);
    public Question addQuestion(Question question, long userId, long companyId);
    public List<Review> getReviews(long userId);
    public List<Question> getQuestions(long userId);
}
