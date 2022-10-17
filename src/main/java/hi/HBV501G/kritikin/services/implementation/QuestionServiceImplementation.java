package hi.HBV501G.kritikin.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hi.HBV501G.kritikin.persistence.entites.Question;
import hi.HBV501G.kritikin.persistence.repositories.QuestionRepository;
import hi.HBV501G.kritikin.services.CompanyService;
import hi.HBV501G.kritikin.services.QuestionService;
import hi.HBV501G.kritikin.services.UserService;

@Service
public class QuestionServiceImplementation implements QuestionService {

    QuestionRepository questionRepository;
    CompanyService companyService;
    UserService userService;

    @Autowired
    public QuestionServiceImplementation(QuestionRepository questionRepository, CompanyService companyService,
            UserService userService) {
        this.questionRepository = questionRepository;
        this.companyService = companyService;
        this.userService = userService;
    }

    @Override
    public void save(Question question) {
        questionRepository.save(question);
    }

    @Override
    public void delete(Question question) {
        questionRepository.delete(question);
    }

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public Question findById(long id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<Question> findByCompany(long companyId) {
        return questionRepository.findByCompany(companyId);
    }

    @Override
    public List<Question> findByUser(long userId) {
        return questionRepository.findByUser(userId);
    }

    @Override
    public Question addQuestion(Question question, long userId, long companyId) {
        if (question.getCompany() == null) {
            question.setCompany(companyService.getReferenceById(companyId));
        }

        if (question.getUser() == null) {
            question.setUser(userService.getReferenceById(userId));
        }

        questionRepository.save(question);
        return question;
    }

}
