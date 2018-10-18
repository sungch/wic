package bettercare.wic.service.manual;

import bettercare.wic.dal.entity.Category;
import bettercare.wic.service.common.InitSetup;
import org.junit.Test;

import java.util.Map;

public class CategoryImageUpdater extends InitSetup {

    @Test
    public void updateCategoryImages() {
        for (Category category : categories) {
            String newImageName = category.getName().replaceAll("[\\s]", "").trim().toLowerCase() + categoryImageName + "_" + category.getId() + ".png";
            String newCategoryImageUrl = category.getId() + "/" + newImageName;
            if(category.getImageUrl() != null && !category.getImageUrl().isEmpty() && category.getImageUrl().equals(newCategoryImageUrl)) {
                continue;
            }
            category.setImageUrl(newCategoryImageUrl);
            category = wicTransactionManager.saveOrUpdateCategory(category);
            wicLogger.log(String.format("Created a Category %s", category.toString()));
        }
    }
}