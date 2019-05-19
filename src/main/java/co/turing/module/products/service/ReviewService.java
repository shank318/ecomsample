package co.turing.module.products.service;

import co.turing.module.products.domain.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getReviews(int productId);
    void createReview(Review review);
}
