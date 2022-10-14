package hi.HBV501G.kritikin.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hi.HBV501G.kritikin.persistence.entites.Company;
import hi.HBV501G.kritikin.persistence.entites.Question;
import hi.HBV501G.kritikin.persistence.entites.Review;
import hi.HBV501G.kritikin.persistence.repositories.CompanyRepository;
import hi.HBV501G.kritikin.persistence.repositories.UserRepository;
import hi.HBV501G.kritikin.services.CompanyService;

@Service
public class CompanyServiceImplementation implements CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Autowired
    public CompanyServiceImplementation(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;

        //this.save(new Company("Test", 5.0, "www.test.com", 1234567, "Test description", "Test address", "Test opening hours"));
        //this.save(new Company("Test2", 4.0, "www.test2.com", 1234567, "Test2 description", "Test2 address", "Test2 opening hours"));

    }

    @Override
    public void save(Company company) {
        companyRepository.save(company);
    }

    @Override
    public void delete(Company company) {
        companyRepository.delete(company);
    }

    @Override
    public Review addReview(Review review, long userId, long companyId) {
        review.setCompany(companyRepository.getReferenceById(companyId));
        review.setUser(userRepository.getReferenceById(userId));
        return companyRepository.getReferenceById(companyId).addReview(review);
    }

    @Override
    public Question addQuestion(Question question, long userId, long companyId) {
        return companyRepository.getReferenceById(companyId).addQuestion(question);
    }

    @Override
    public Company findById(long id) {
        return companyRepository.findById(id);
    }

    @Override
    public Company findByName(String name) {
        return companyRepository.findByName(name);
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public List<Review> getReviews(long companyId) {
        return companyRepository.findById(companyId).getReviews();
    }

    @Override
    public List<Question> getQuestions(long companyId) {
        return companyRepository.findById(companyId).getQuestions();
    }

}
