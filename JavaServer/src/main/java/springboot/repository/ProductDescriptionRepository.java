package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.domein.ProductDescription;

import java.util.List;

@Repository
public interface ProductDescriptionRepository extends JpaRepository<ProductDescription, Integer> {
    ProductDescription findByProductIdAndLanguageId(int productId, String languageId);
}
