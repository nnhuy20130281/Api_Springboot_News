package vn.edu.hcmuaf.apiNews.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.apiNews.entity.Category;
import vn.edu.hcmuaf.apiNews.entity.News;
import vn.edu.hcmuaf.apiNews.model.dto.CateDto;
import vn.edu.hcmuaf.apiNews.model.dto.NewsDto;
import vn.edu.hcmuaf.apiNews.model.dto.UpdateCate;
import vn.edu.hcmuaf.apiNews.model.mapper.CateMapper;
import vn.edu.hcmuaf.apiNews.model.mapper.NewsMapper;
import vn.edu.hcmuaf.apiNews.repository.CategoryRepository;
import vn.edu.hcmuaf.apiNews.repository.NewsRepository;
import vn.edu.hcmuaf.apiNews.service.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findByIsDeleteFalse();
    }

    @Override
    public List<Category> getCategoryAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> getAllCategoryHidden() {
        return categoryRepository.findByIsDeleteTrue();
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public String createCategory(UpdateCate updateCate) {
        Category category = new Category();
        category.setName(updateCate.getName());
        category.setDelete(updateCate.isDelete());
        category.setCreatedBy(updateCate.getCreatedBy());
        category.setCreatedDate(updateCate.getCreatedDate());
        categoryRepository.save(category);
        return "Category created successfully!";
    }

    @Override
    public String updateCategory(long id, UpdateCate updateCate) {
        Category existingCategory = categoryRepository.findById(id).get();
        existingCategory.setName(updateCate.getName());
        existingCategory.setDelete(updateCate.isDelete());
        categoryRepository.save(existingCategory);
        return "Category updated successfully!";
    }

    @Override
    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void hideCategory(long id) {
        Category category = getCategoryById(id);
        category.setDelete(!category.isDelete());
        categoryRepository.save(category);
    }

    @Override
    public List<NewsDto> getNewsByCategory(long id) {
        return NewsMapper.toNewsDto(categoryRepository.findById(id).get().getListNews().stream().toList());
    }

    @Override
    public Set<CateDto> getListCategoryFromNews(long newsId) {
        return CateMapper.toCateDto(newsRepository.findById(newsId).get().getCategories());
    }
}
