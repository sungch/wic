package bettercare.wic.dal.jpa;

import bettercare.wic.dal.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductJpaService extends JpaRepository<Product, Long> {

}
