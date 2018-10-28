package bettercare.wic.dal.dao;

import bettercare.wic.dal.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CustomerDao extends JpaRepository<Customer, Long> {

  List<Customer> findByWicNumberAndPhoneAndAddressAndName(String wicNumber, String phone, String address, String name);
}
