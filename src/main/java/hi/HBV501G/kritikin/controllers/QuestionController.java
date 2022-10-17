package hi.HBV501G.kritikin.controllers;

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
    QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping(value = HomeController.APIURL + "/company/{id}/questions")
    public List<Question> fetchQuestionsByCompany(@PathVariable long id) {
        return questionService.findByCompany(id);
    }

    @GetMapping(value = HomeController.APIURL + "/user/{id}/questions")
    public List<Question> fetchQuestionsByUser(@PathVariable long id) {
        return questionService.findByUser(id);
    }

    @PostMapping(value = HomeController.APIURL + "/company/{id}/questions")
    public Question addQuestion(@RequestBody Question question, @RequestParam long userId,
            @PathVariable("id") long companyId) {
        return questionService.addQuestion(question, userId, companyId);
    }

    @PostMapping(value = HomeController.APIURL + "/question/{id}/answers")
    public Question addAnswer(@RequestBody String answer, @PathVariable("id") long questionId) {
        return questionService.addAnswer(answer, questionId);
    }
}
