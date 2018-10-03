package bettercare.wic.jpa.service;

import bettercare.wic.jpa.model.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class DeliveryService {

  @Resource
  WicService wicService;

  public Delivery find(Long id) {
    return wicService.find(Delivery.class, id);
  }

  public void create(Delivery obj) {
    wicService.create(obj);
  }

  public void remove(Delivery obj) {
    wicService.remove(obj);
  }

  public Delivery merge(Delivery obj) {
    return wicService.merge(obj);
  }

}
