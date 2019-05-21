package co.turing.module.products.service;

import co.turing.module.products.ReviewRepo;
import co.turing.module.products.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepo reviewRepo;

    /**
     *
     * @param productId
     * @return
     */
    @Override
    public List<Review> getReviews(int productId) {
        return reviewRepo.findAllByProductId(productId);
    }

    /**
     *
     * @param review
     */
    @Override
    public void createReview(Review review) {
        reviewRepo.save(review);
    }
}
