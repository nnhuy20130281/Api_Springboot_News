package vn.edu.hcmuaf.apiNews.service;

import vn.edu.hcmuaf.apiNews.entity.Category;
import vn.edu.hcmuaf.apiNews.entity.News;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategory();

    List<Category> getAllCategoryHidden();

    Category getCategoryById(long id);

    Category createCategory(Category category);

    Category updateCategory(long id, Category category);

    void deleteCategory(long id);

    void hideCategory(long id);

    List<News> getNewsByCategory(long id);
}
