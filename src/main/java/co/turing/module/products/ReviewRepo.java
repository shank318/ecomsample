package co.turing.module.products;

import co.turing.module.products.domain.Review;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Integer> {
    List<Review> findAllByProductId(int productId);
}
