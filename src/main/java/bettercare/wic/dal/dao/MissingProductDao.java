package bettercare.wic.dal.dao;

import bettercare.wic.dal.entity.MissingProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface MissingProductDao extends JpaRepository<MissingProduct, Long> {

}
