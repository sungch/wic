package bettercare.wic.dal.jpa;

import bettercare.wic.dal.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerJpaService extends JpaRepository<Customer, Long> {


}
