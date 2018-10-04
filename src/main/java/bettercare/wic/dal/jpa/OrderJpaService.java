package bettercare.wic.dal.jpa;

import bettercare.wic.dal.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderJpaService extends JpaRepository<Order, Long> {

}
