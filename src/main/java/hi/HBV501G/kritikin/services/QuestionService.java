package hi.HBV501G.kritikin.services;

import java.util.List;

import hi.HBV501G.kritikin.persistence.entites.Question;

public interface QuestionService {
    void save(Question question);

    void delete(Question question);

    List<Question> findAll();

    Question findById(long id);

    List<Question> findByCompany(long companyId);

    List<Question> findByUser(long userId);
}
