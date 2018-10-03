package bettercare.wic.jpa.service;

import bettercare.wic.jpa.model.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class ProductService {

  @Resource
  WicService WicService;

  public Product find(Long id) {
    return WicService.find(Product.class, id);
  }

  public void create(Product obj) {
    WicService.create(obj);
  }

  public void remove(Product obj) {
    WicService.remove(obj);
  }

  public Product merge(Product obj) {
    return WicService.merge(obj);
  }

}
