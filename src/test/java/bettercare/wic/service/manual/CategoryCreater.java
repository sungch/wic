package bettercare.wic.service.manual;

import bettercare.wic.dal.entity.Category;
import org.junit.Test;

import java.util.List;

public class CategoryCreater extends InitSetup {

  @Test
  public void addNewCategory() {
    for(String categoryName : categoryNames) {
      if (isCategoryEmpty(categoryName)) {
        Category category = prepareCategory(categoryName);
        Category persistedCategory = entityService.saveOrUpdate(Category.class, category);
        wicLogger.info(String.format("Created a Category %s", persistedCategory.toString()), Category.class);
      }
      else {
        wicLogger.warn("The same Category name already found in the system. No transactions" + categoryName, CategoryCreater.class);
      }
    }
  }

  private boolean isCategoryEmpty(String name) {
    List<Category> categories = entityService.findCategoryByName(name);
    return categories.isEmpty();
  }

  private Category prepareCategory(String categoryName) {
    Category category = new Category();
    category.setName(categoryName);
    return category;
  }

}