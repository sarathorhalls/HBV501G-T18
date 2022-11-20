package hi.HBV501G.kritikin.persistence.entites.DTOs;

import java.util.ArrayList;
import java.util.List;

import hi.HBV501G.kritikin.persistence.entites.Question;

public class QuestionJSON {
    private long id;
    private long companyId;
    private long userId;
    private String username;
    private String questionText;
    private String answerString;

    public QuestionJSON() {
    }

    public QuestionJSON(Question question) {
        this.id = question.getId();
        this.companyId = question.getCompany().getId();
        this.userId = question.getUser().getId();
        this.username = question.getUser().getUsername();
        this.questionText = question.getQuestionText();
        this.answerString = question.getAnswerString();
    }

    public static List<QuestionJSON> convert(List<Question> questions) {
        List<QuestionJSON> questionJsons = new ArrayList<>();
        for (Question question : questions) {
            questionJsons.add(new QuestionJSON(question));
        }
        return questionJsons;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswerString() {
        return answerString;
    }

    public void setAnswerString(String answerString) {
        this.answerString = answerString;
    }

}
