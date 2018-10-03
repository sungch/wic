package bettercare.wic.jpa.service;

import bettercare.wic.jpa.model.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class CategoryService {

  @Resource
  WicService wicService;

  public Category find(Long id) {
    return wicService.find(Category.class, id);
  }

  public void create(List<Product> products, String name, String imageId) {
    Category category = new Category();
    category.setImageId(imageId);
    category.setName(name);
    category.setProducts(products);
    wicService.create(category);
  }

  public void remove(Category obj) {
    wicService.remove(obj);
  }

  public Category merge(Category obj) {
    return wicService.merge(obj);
  }
}
