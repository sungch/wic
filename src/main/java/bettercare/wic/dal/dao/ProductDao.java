package bettercare.wic.dal.dao;

import bettercare.wic.dal.entity.Category;
import bettercare.wic.dal.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProductDao extends JpaRepository<Product, Long> {

  public List<Product> findByIsHandling(String isHandling);

  public List<Product> findByCategory(Category category);

  public List<Product> findByCategoryAndNameAndIsHandling(Category category, String productName, boolean isHandling);
}
