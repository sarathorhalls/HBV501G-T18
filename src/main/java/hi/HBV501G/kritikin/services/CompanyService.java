package hi.HBV501G.kritikin.services;

import java.util.List;

import hi.HBV501G.kritikin.persistence.entites.Company;
import hi.HBV501G.kritikin.persistence.entites.Question;
import hi.HBV501G.kritikin.persistence.entites.Review;

public interface CompanyService {
    public void save(Company company);
    public void delete(Company company);
    public Company findById(long id);
    public Company findByName(String name);
    public List<Company> findAll();
    public Review addReview(Review review, long userId, long companyId);
    public Question addQuestion(Question question, long userId, long companyId);
}
