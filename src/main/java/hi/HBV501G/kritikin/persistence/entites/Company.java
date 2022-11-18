package hi.HBV501G.kritikin.persistence.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "companies")
public class Company {
    private @Id @GeneratedValue long id;
    private String name;
    private Double starRating;
    private String website;
    private int phoneNumber;
    private String description;
    private String address;
    private String openingHours;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "company-review")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "company-question")
    private List<Question> questions = new ArrayList<>();

    public Company() {
    }

    public Company(String name, Double starRating, String website, int phoneNumber, String description, String address,
            String openingHours) {
        this.name = name;
        this.starRating = starRating;
        this.website = website;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.address = address;
        this.openingHours = openingHours;
        reviews = null;
        questions = null;
    }

    public Company(String name) {
        this.name = name;
        reviews = null;
        questions = null;
    }

    public Review addReview(Review review) {
        reviews.add(review);
        return review;
    }

    public Question addQuestion(Question question) {
        questions.add(question);
        return question;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getStarRating() {
        return starRating;
    }

    public void setStarRating(Double starRating) {
        this.starRating = starRating;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

}
