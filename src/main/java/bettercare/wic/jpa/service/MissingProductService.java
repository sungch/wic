package bettercare.wic.jpa.service;

import bettercare.wic.jpa.model.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class MissingProductService {

  @Resource
  WicService wicService;

  public MissingProduct find(Long id) {
    return wicService.find(MissingProduct.class, id);
  }

  public void create(MissingProduct obj) {
    wicService.create(obj);
  }

  public void remove(MissingProduct obj) {
    wicService.remove(obj);
  }

  public MissingProduct merge(MissingProduct obj) {
    return wicService.merge(obj);
  }

}
