package hi.HBV501G.kritikin.persistence.entites.DTOs;

import java.util.ArrayList;
import java.util.List;

import hi.HBV501G.kritikin.persistence.entites.Review;

public class ReviewJSON {
    private long id;
    private long companyId;
    private long userId;
    private String username;
    private Double starRating;
    private String reviewText;

    public ReviewJSON() {
    }

    public ReviewJSON(Review review) {
        this.id = review.getId();
        this.companyId = review.getCompany().getId();
        this.userId = review.getUser().getId();
        this.username = review.getUser().getUsername();
        this.starRating = review.getStarRating();
        this.reviewText = review.getReviewText();
    }

    public static List<ReviewJSON> convert(List<Review> reviews) {
        List<ReviewJSON> reviewJsons = new ArrayList<>();
        for (Review review : reviews) {
            reviewJsons.add(new ReviewJSON(review));
        }
        return reviewJsons;
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

    public Double getStarRating() {
        return starRating;
    }

    public void setStarRating(Double starRating) {
        this.starRating = starRating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
