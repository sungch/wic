package bettercare.wic.jpa.service;

import bettercare.wic.jpa.model.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class OrderService {

  @Resource
  WicService wicService;

  public Order find(Long id) {
    return wicService.find(Order.class, id);
  }

  public void create(Order obj) {
    wicService.create(obj);
  }

  public void remove(Order obj) {
    wicService.remove(obj);
  }

  public Order merge(Order obj) {
    return wicService.merge(obj);
  }

}
