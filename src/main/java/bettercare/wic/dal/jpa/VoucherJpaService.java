package bettercare.wic.dal.jpa;

import bettercare.wic.dal.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public interface VoucherJpaService extends JpaRepository<Voucher, Long> {

}
