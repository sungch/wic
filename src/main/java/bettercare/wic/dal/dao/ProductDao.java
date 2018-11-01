package bettercare.wic.dal.dao;

import bettercare.wic.dal.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProductDao extends JpaRepository<Product, Long> {

  public List<Product> findByIsHandling(String isHandling);

//  public List<Product> findByCategoryId(long categoryId);

//  public List<Product> findByCategoryIdAndNameAndIsHandling(long categoryId, String name, String isHandling);
}
