package bettercare.wic.service.manual;

import bettercare.wic.dal.entity.Category;
import org.junit.Test;

public class CategoryImageUpdater extends InitSetup {

    @Test
    public void updateCategoryImages() {
        for (Category category : categories) {
            String url = "category/" + category.getName().replaceAll("[\\s]", "").replaceAll("&", "_").toLowerCase() + ".jpg";
            if(category.getImageUrl() != null && !category.getImageUrl().isEmpty() && category.getImageUrl().equals(url)) {
                continue;
            }
            category.setImageUrl(url);
            category = entityService.saveOrUpdate(Category.class, category);
            wicLogger.info(String.format("Created a Category %s", category.toString()), CategoryImageUpdater.class);
        }
    }
}