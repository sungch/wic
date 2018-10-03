package bettercare.wic.jpa.service;

import bettercare.wic.jpa.model.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class VoucherService {

  @Resource
  WicService wicService;

  public Voucher find(Long id) {
    return wicService.find(Voucher.class, id);
  }

  public void create(Voucher obj) {
    wicService.create(obj);
  }

  public void remove(Voucher obj) {
    wicService.remove(obj);
  }

  public Voucher merge(Voucher obj) {
    return wicService.merge(obj);
  }

}
