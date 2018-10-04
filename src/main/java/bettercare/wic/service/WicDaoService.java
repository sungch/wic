package bettercare.wic.service;

import bettercare.wic.dal.dao.CategoryDao;
import bettercare.wic.dal.entity.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class WicDaoService {

    @Resource
    private CategoryDao categoryDao; // requires @EnableJpaRepositories("bettercare.wic.dal") in somewhere configuration

    public Category saveAndFlushCategory(Category category) {
        return categoryDao.saveAndFlush(category);
    }
}
