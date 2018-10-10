package bettercare.wic.dal.dao;

import bettercare.wic.dal.entity.WicOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface WicOrderDao extends JpaRepository<WicOrder, Long> {

}
