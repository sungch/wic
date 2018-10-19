package bettercare.wic.service.manual;

import bettercare.wic.dal.entity.Category;
import bettercare.wic.service.common.InitSetup;
import org.junit.Test;

import java.util.Map;

public class CategoryImageUpdater extends InitSetup {

    @Test
    public void updateCategoryImages() {
        for (Category category : categories) {
            String url = "category/" + category.getName().replaceAll("[\\s]", "").replaceAll("&", "_").toLowerCase() + ".jpg";
            if(category.getImageUrl() != null && !category.getImageUrl().isEmpty() && category.getImageUrl().equals(url)) {
                continue;
            }
            category.setImageUrl(url);
            category = wicTransactionManager.saveOrUpdateCategory(category);
            wicLogger.log(String.format("Created a Category %s", category.toString()));
        }
    }
}