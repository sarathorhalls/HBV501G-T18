package hi.HBV501G.kritikin.services.implementation;

/**
 * This class is the implementation of the QuestionService interface. It handles
 * all business logic to and from the repositories for everything regarding
 * questions.
 * 
 * @author Sara Þórhallsdóttir
 */

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

    /**
     * Constructor for QuestionServiceImplementation which uses AutoWired to inject
     * the QuestionRepository, CompanyService and UserService from JPA.
     * 
     * @param questionRepository
     * @param companyService
     * @param userService
     */
    @Autowired
    public QuestionServiceImplementation(QuestionRepository questionRepository, CompanyService companyService,
            UserService userService) {
        this.questionRepository = questionRepository;
        this.companyService = companyService;
        this.userService = userService;
    }

    /**
     * Saves a question to the database.
     * 
     * @param question the question to be saved.
     */
    @Override
    public void save(Question question) {
        questionRepository.save(question);
    }

    /**
     * Deletes a question from the database.
     * 
     * @param question the question to be deleted.
     */
    @Override
    public void delete(Question question) {
        questionRepository.delete(question);
    }

    /**
     * Returns a question from the database with a given id.
     * 
     * @param id the id of the question to be returned.
     * @return the question with the given id.
     */
    @Override
    public Question findById(long id) {
        return questionRepository.findById(id);
    }

    /**
     * Returns a list of all questions from the database.
     * 
     * @return a list of all questions.
     */
    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    /**
     * Returns a list of all questions from the database with a given company id.
     * 
     * @param companyId the id of the company.
     * @return a list of all questions with the given company id.
     */
    @Override
    public List<Question> findByCompany(long companyId) {
        return questionRepository.findByCompanyId(companyId);
    }

    /**
     * Returns a list of all questions from the database with a given user id.
     * 
     * @param userId the id of the user.
     * @return a list of all questions with the given user id.
     */
    @Override
    public List<Question> findByUser(long userId) {
        return questionRepository.findByUserId(userId);
    }

    /**
     * Adds a question to the database with the given company id and user id.
     * 
     * @param question  the question to be added.
     * @param userId    the id of the user.
     * @param companyId the id of the company.
     * @return the question that was added.
     */
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

    /**
     * Updates a question with the given answer to it in the database with the given
     * question id.
     * 
     * @param answer     the answer to the question.
     * @param questionId the id of the question.
     */
    @Override
    public Question addAnswer(String answer, long questionId) {
        Question question = questionRepository.findById(questionId);
        question.setAnswerString(answer);
        questionRepository.save(question);
        return question;
    }

}
