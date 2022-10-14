package hi.HBV501G.kritikin.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hi.HBV501G.kritikin.persistence.entites.Question;
import hi.HBV501G.kritikin.persistence.entites.Review;
import hi.HBV501G.kritikin.persistence.entites.User;
import hi.HBV501G.kritikin.persistence.repositories.UserRepository;
import hi.HBV501G.kritikin.services.UserService;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;

        //this.save(new User("Test", "password"));
        //this.save(new User("Test2", "password"));
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Review addReview(Review review, long userId, long companyId) {
        return userRepository.getReferenceById(userId).addReview(review);
    }

    @Override
    public Question addQuestion(Question question, long userId, long companyId) {
        return userRepository.getReferenceById(userId).addQuestion(question);
    }

    @Override
    public List<Review> getReviews(long userId) {
        return userRepository.findById(userId).getReviews();
    }

    @Override
    public List<Question> getQuestions(long userId) {
        return userRepository.findById(userId).getQuestions();
    }

}
