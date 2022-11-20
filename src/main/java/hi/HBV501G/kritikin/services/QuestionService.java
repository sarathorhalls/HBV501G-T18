package hi.HBV501G.kritikin.services;

import java.util.List;

import hi.HBV501G.kritikin.persistence.entites.Question;
import hi.HBV501G.kritikin.persistence.entites.DTOs.QuestionJSON;

public interface QuestionService {
    void save(Question question);

    void delete(Question question);

    Question findById(long id);

    List<Question> findAll();

    List<QuestionJSON> findByCompany(long companyId);

    List<QuestionJSON> findByUser(long userId);

    Question addQuestion(Question question, long userId, long companyId);

    Question addAnswer(String answer, long questionId);
}
