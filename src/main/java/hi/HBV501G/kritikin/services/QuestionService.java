package hi.HBV501G.kritikin.services;

import java.util.List;

import hi.HBV501G.kritikin.persistence.entites.Question;

public interface QuestionService {
    void save(Question question);

    void delete(Question question);

    Question findById(long id);

    List<Question> findAll();

    List<Question> findByCompany(long companyId);

    List<Question> findByUser(long userId);

    Question addQuestion(Question question, long userId, long companyId);

    Question addAnswer(String answer, long questionId);
}
