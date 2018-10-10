package bettercare.wic.dal.dao;

import bettercare.wic.dal.entity.Customer;
import bettercare.wic.dal.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CustomerDao extends JpaRepository<Customer, Long> {

}
