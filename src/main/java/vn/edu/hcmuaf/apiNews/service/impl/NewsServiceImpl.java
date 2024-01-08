package vn.edu.hcmuaf.apiNews.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.apiNews.entity.Category;
import vn.edu.hcmuaf.apiNews.entity.News;
import vn.edu.hcmuaf.apiNews.model.dto.NewsDto;
import vn.edu.hcmuaf.apiNews.model.dto.UpdateNews;
import vn.edu.hcmuaf.apiNews.model.mapper.NewsMapper;
import vn.edu.hcmuaf.apiNews.repository.CategoryRepository;
import vn.edu.hcmuaf.apiNews.repository.NewsRepository;
import vn.edu.hcmuaf.apiNews.service.NewsService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // get all news not hidden
    @Override
    public List<NewsDto> getAllNews() {
        return NewsMapper.toNewsDto(newsRepository.findByIsDeleteFalse());
    }

    // get all news
    @Override
    public List<NewsDto> getNewsAll() {
        return NewsMapper.toNewsDto(newsRepository.findAll());
    }

    // get all news hidden
    @Override
    public List<NewsDto> getAllNewsHidden() {
        return NewsMapper.toNewsDto(newsRepository.findByIsDeleteTrue());
    }

    // get news by id
    @Override
    public NewsDto getNewsById(long id) {
        return NewsMapper.toNewsDto(newsRepository.findById(id).orElse(null));
    }

    // create news
    @Override
    public String createNews(UpdateNews updateNews) {
        LocalDate currentDate = LocalDate.now();
        News news = new News();
        news.setTitle(updateNews.getTitle());
        news.setDescription(updateNews.getDescription());
        news.setImage(updateNews.getImage());
        news.setContent(updateNews.getContent());
        news.setDelete(false);
        news.setCreatedBy(updateNews.getCreatedBy());
        news.setCreatedDate(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        newsRepository.save(news);

        for (Long id : updateNews.getIdCategories()) {
            addNewsToCate(news.getId(), id);
        }


        return "Create news success";
    }

    // update news
    @Override
    public String updateNews(long id, UpdateNews updateNews) {
        News existingNews = newsRepository.findById(id).get();
        existingNews.setTitle(updateNews.getTitle());
        existingNews.setDescription(updateNews.getDescription());
        existingNews.setImage(updateNews.getImage());
        existingNews.setContent(updateNews.getContent());
        existingNews.setDelete(updateNews.isDelete());

        System.out.println(updateNews.isDelete());
        removeAllCategoryByNews(existingNews.getId());

        for (Long idCate : updateNews.getIdCategories()) {
            addNewsToCate(existingNews.getId(), idCate);
        }

        newsRepository.save(existingNews);

        return "Update news success";
    }

    // hide news
    @Override
    public void updateNewsHidden(long id) {
        News news = newsRepository.findById(id).get();
        news.setDelete(!news.isDelete());
        newsRepository.save(news);
    }


    // delete news
    @Override
    public void deleteNews(long id) {
        newsRepository.deleteById(id);
    }

    // add news to category
    private void addNewsToCate(Long newsId, Long cateId) {
        Optional<News> news = newsRepository.findById(newsId);

        Optional<Category> category = categoryRepository.findById(cateId);

        category.get().getListNews().add(news.get());

        categoryRepository.save(category.get());
    }

    // remove all category by news
    private void removeAllCategoryByNews(long newsId) {
        Optional<News> newsOptional = newsRepository.findById(newsId);

        if (newsOptional.isPresent()) {
            News news = newsOptional.get();

            for (Category category : news.getCategories()) {
                category.getListNews().remove(news);
                categoryRepository.save(category);
            }
            news.getCategories().clear();
            newsRepository.save(news);
        }
    }
}

