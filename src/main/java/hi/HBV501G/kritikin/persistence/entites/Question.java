package hi.HBV501G.kritikin.persistence.entites;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    @JsonBackReference(value = "company-question")
    private Company company;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonBackReference(value = "user-question")
    private User user;

    private String questionText;
    private String answerString;

    public Question() {
    }

    public Question(Company company, User user, String questionText, String answerString) {
        this.company = company;
        this.user = user;
        this.questionText = questionText;
        this.answerString = answerString;
    }

    public Question(String questionText) {
        this.questionText = questionText;
    }

    public long getId() {
        return id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
