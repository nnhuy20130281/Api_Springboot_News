package vn.edu.hcmuaf.apiNews.service;

import vn.edu.hcmuaf.apiNews.entity.Category;
import vn.edu.hcmuaf.apiNews.entity.News;
import vn.edu.hcmuaf.apiNews.model.dto.UpdateCate;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategory();

    List<Category> getCategoryAll();

    List<Category> getAllCategoryHidden();

    Category getCategoryById(long id);

    String createCategory(UpdateCate updateCate);

    String updateCategory(long id, UpdateCate updateCate);

    void deleteCategory(long id);

    void hideCategory(long id);

    List<News> getNewsByCategory(long id);
}
