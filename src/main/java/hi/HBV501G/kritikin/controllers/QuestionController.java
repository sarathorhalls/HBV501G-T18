package hi.HBV501G.kritikin.controllers;

import java.net.URI;
import java.util.Base64;

/**
 * This class is the controller for everything related to questions. It handles the
 * creation and updating of questions and answers from REST requests.
 * 
 * @author Sara Þórhallsdóttir
 */

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import hi.HBV501G.kritikin.persistence.entites.Question;
import hi.HBV501G.kritikin.persistence.entites.DTOs.QuestionJSON;
import hi.HBV501G.kritikin.services.QuestionService;

@RestController
@CrossOrigin()
public class QuestionController {

    private QuestionService questionService;

    Logger logger = org.slf4j.LoggerFactory.getLogger(QuestionController.class);

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
    @GetMapping(value = CompanyController.APIURL + "/company/{id}/questions")
    public ResponseEntity<List<QuestionJSON>> fetchQuestionsByCompany(@PathVariable long id) {
        return ResponseEntity.ok(questionService.findByCompany(id));
    }

    /**
     * Returns a list of all questions as a json object for a particular user with
     * the
     * given id from a get request to /api/user/{id}/questions .
     * 
     * @param id the id of the user
     * @return a list of all questions for a particular user
     */
    @GetMapping(value = CompanyController.APIURL + "/user/{id}/questions")
    public ResponseEntity<List<QuestionJSON>> fetchQuestionsByUser(@PathVariable long id) {
        return ResponseEntity.ok(questionService.findByUser(id));
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
    @PostMapping(value = CompanyController.APIURL + "/company/{id}/question")
    public ResponseEntity<Question> addQuestion(Question question,
            @PathVariable("id") long companyId, @RequestHeader("Authorization") String auth) {
        logger.info("addQuestion() called with authorization header: {}", auth);
        String token = auth.replace("Bearer ", "").split("\\.")[1];
        String decodedPayload = new String(Base64.getDecoder().decode(token));
        try {
            Long userId = Long.parseLong(decodedPayload.split(",")[1].split(":")[1].split("\"")[1]);
            logger.info("User id: {}", userId);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(CompanyController.APIURL + "/company/" + companyId + "/question").toUriString());
            return ResponseEntity.created(uri).body(questionService.addQuestion(question, userId, companyId));
        } catch (Exception e) {
            logger.error("Error parsing user id from token: {}", e.getMessage());
            return ResponseEntity.badRequest().body(question);
        }
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
    @PostMapping(value = CompanyController.APIURL + "/question/{id}/answer")
    public ResponseEntity<Question> addAnswer(String answer, @PathVariable("id") long questionId) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(CompanyController.APIURL + "/question/" + questionId + "/answer").toUriString());
        return ResponseEntity.created(uri).body(questionService.addAnswer(answer, questionId));
    }
}
