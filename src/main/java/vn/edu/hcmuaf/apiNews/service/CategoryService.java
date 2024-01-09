package vn.edu.hcmuaf.apiNews.service;

import vn.edu.hcmuaf.apiNews.entity.Category;
import vn.edu.hcmuaf.apiNews.entity.News;
import vn.edu.hcmuaf.apiNews.model.dto.CateDto;
import vn.edu.hcmuaf.apiNews.model.dto.NewsDto;
import vn.edu.hcmuaf.apiNews.model.dto.UpdateCate;

import java.util.List;
import java.util.Set;

public interface CategoryService {

    List<Category> getAllCategory();

    List<Category> getCategoryAll();

    List<Category> getAllCategoryHidden();

    Category getCategoryById(long id);

    String createCategory(UpdateCate updateCate);

    String updateCategory(long id, UpdateCate updateCate);

    void deleteCategory(long id);

    void hideCategory(long id);

    List<NewsDto> getNewsByCategory(long id);


    Set<CateDto> getListCategoryFromNews(long newsId);

}
