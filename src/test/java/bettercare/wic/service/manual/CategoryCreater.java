package bettercare.wic.service.manual;

import bettercare.wic.dal.entity.Category;
import bettercare.wic.service.common.InitSetup;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

public class CategoryCreater extends InitSetup {

  @Test
  public void addNewCategory() {
    Map<String, Object> where = new HashMap<>();
    where.put("name", categoryName);
    String query = composeQuery(Category.class, where, " limit 1 ");
    if (isEmpty(query, Category.class)) {
      Category category = prepareCategory(categoryName);
      category = wicTransactionManager.saveOrUpdateCategory(category);
      wicLogger.log(String.format("Created a Category %s", category.toString()));
    }
    else {
      wicLogger.log("The same Category name already found in the system. No transactions:" + categoryName);
    }
  }

  private boolean isEmpty(String query, Class clz) {
    return wicEntityManasger.findListByNativeQuery(query, clz).isEmpty();
  }

  private Category prepareCategory(String categoryName) {
    Category category = new Category();
    category.setName(categoryName);
    return category;
  }

  private String composeQuery(Class claz, Map<String, Object> whereSource, String otherClause) {
    String alias = "o";
    String whereClause = composeWhereClause(whereSource, alias);
    otherClause = otherClause == null ? "" : otherClause;
    return String.format("select * from %s as %s where %s %s",
        claz.getSimpleName().toLowerCase(), alias, whereClause, otherClause);
  }

  private String composeWhereClause(Map<String, Object> whereSource, String alias) {
    StringBuilder whereClause = new StringBuilder();
    whereSource.forEach((columnName, value) -> {
      if (whereClause.length() > 0) {
        whereClause.append(" and ");
      }
      whereClause.append(alias).append(".").append(columnName).append("='").append(value).append("'");
    });
    return whereClause.toString();
  }
}