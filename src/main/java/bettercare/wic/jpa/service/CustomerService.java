package bettercare.wic.jpa.service;

import bettercare.wic.jpa.model.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class CustomerService {

  @Resource
  WicService wicService;

  public Customer find(Long id) {
    return wicService.find(Customer.class, id);
  }

  public void create(Customer obj) {
    wicService.create(obj);
  }

  public void remove(Customer obj) {
    wicService.remove(obj);
  }

  public Customer merge(Customer obj) {
    return wicService.merge(obj);
  }

}
