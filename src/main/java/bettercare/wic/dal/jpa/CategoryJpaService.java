package bettercare.wic.dal.jpa;

import bettercare.wic.dal.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryJpaService extends JpaRepository<Category, Long> {

}
