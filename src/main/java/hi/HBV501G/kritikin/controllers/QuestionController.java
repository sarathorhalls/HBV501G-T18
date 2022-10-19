package hi.HBV501G.kritikin.controllers;

/**
 * This class is the controller for everything related to questions. It handles the
 * creation and updating of questions and answers from REST requests.
 * 
 * @author Sara Þórhallsdóttir
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hi.HBV501G.kritikin.persistence.entites.Question;
import hi.HBV501G.kritikin.services.QuestionService;

@RestController
public class QuestionController {

    private QuestionService questionService;

    /**
     * Constructor for QuestionController which uses autowired to inject the
     * QuestionService with JPA.
     * 
     * @param questionService
     */
    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Returns a list of all questions as a json object for a particular company
     * with
     * the given id from a get request to /api/company/{id}/questions .
     * 
     * @param id the id of the company
     * @return a list of all questions for a particular company
     */
    @GetMapping(value = HomeController.APIURL + "/company/{id}/questions")
    public List<Question> fetchQuestionsByCompany(@PathVariable long id) {
        return questionService.findByCompany(id);
    }

    /**
     * Returns a list of all questions as a json object for a particular user with
     * the
     * given id from a get request to /api/user/{id}/questions .
     * 
     * @param id the id of the user
     * @return a list of all questions for a particular user
     */
    @GetMapping(value = HomeController.APIURL + "/user/{id}/questions")
    public List<Question> fetchQuestionsByUser(@PathVariable long id) {
        return questionService.findByUser(id);
    }

    /**
     * Adds a question to the database from a post request to
     * /api/company/{id}/question with the question to be inserted in the body, the
     * user id in the request parameter and the company id in the path.
     * 
     * @param question  the question to be inserted, fetched from the body of the
     *                  post
     * @param userId    the id of the user who wrote the question, fetched from the
     *                  path
     * @param companyId the id of the company the question is for, fetched from the
     *                  path
     * @return the question that was inserted
     */
    @PostMapping(value = HomeController.APIURL + "/company/{id}/question")
    public Question addQuestion(@RequestBody Question question, @RequestParam long userId,
            @PathVariable("id") long companyId) {
        return questionService.addQuestion(question, userId, companyId);
    }

    /**
     * Adds an answer to a question in the database from a post request to
     * /api/question/{id}/answer with the answer to be inserted in the body as a
     * string and the question id of the question to add the answer too in the path.
     * 
     * @param answer     the answer to be inserted, fetched from the body of the
     *                   post request
     * @param questionId the id of the question to add the answer to, fetched from
     *                   the path
     * @return the question that was updated
     */
    @PostMapping(value = HomeController.APIURL + "/question/{id}/answer")
    public Question addAnswer(@RequestBody String answer, @PathVariable("id") long questionId) {
        return questionService.addAnswer(answer, questionId);
    }
}
