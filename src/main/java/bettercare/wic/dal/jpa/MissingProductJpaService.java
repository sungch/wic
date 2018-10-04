package bettercare.wic.dal.jpa;

import bettercare.wic.dal.model.MissingProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MissingProductJpaService extends JpaRepository<MissingProduct, Long>  {

}
