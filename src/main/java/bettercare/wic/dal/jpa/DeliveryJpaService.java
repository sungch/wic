package bettercare.wic.dal.jpa;

import bettercare.wic.dal.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DeliveryJpaService extends JpaRepository<Delivery, Long> {

}
