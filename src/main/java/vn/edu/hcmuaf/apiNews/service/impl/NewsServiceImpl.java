package vn.edu.hcmuaf.apiNews.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.apiNews.entity.Category;
import vn.edu.hcmuaf.apiNews.entity.News;
import vn.edu.hcmuaf.apiNews.model.dto.NewsDto;
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


    @Override
    public List<News> getAllNews() {
        return newsRepository.findByIsDeleteFalse();
    }

    @Override
    public List<News> getNewsAll() {
        return newsRepository.findAll();
    }

    @Override
    public List<News> getAllNewsHidden() {
        return newsRepository.findByIsDeleteTrue();
    }

    @Override
    public News getNewsById(long id) {
        return newsRepository.findById(id).orElse(null);
    }

    @Override
    public News createNews(NewsDto newsDto) {
        LocalDate currentDate = LocalDate.now();
        News news = new News();
        news.setTitle(newsDto.getTitle());
        news.setDescription(newsDto.getDescription());
        news.setImage(newsDto.getImage());
        news.setContent(newsDto.getContent());
        news.setDelete(false);
        news.setCreatedBy(newsDto.getCreatedBy());
        news.setCreatedDate(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        newsRepository.save(news);

        for (Long id : newsDto.getIdCategories()) {
            addNewsToCate(news.getId(), id);
        }


        return news;
    }

    @Override
    public News updateNews(long id, News news) {
        Optional<News> existingNews = newsRepository.findById(id);
        if (existingNews.isPresent()) {
            news.setId(id);
            return newsRepository.save(news);
        }
        return news;
    }

    @Override
    public void updateNewsHidden(long id) {
        News news = getNewsById(id);
        news.setDelete(!news.isDelete());
        newsRepository.save(news);
    }


    @Override
    public void deleteNews(long id) {
        newsRepository.deleteById(id);
    }

    private void addNewsToCate(Long newsId, Long cateId) {
        Optional<News> news = newsRepository.findById(newsId);

        Optional<Category> category = categoryRepository.findById(cateId);

        category.get().getListNews().add(news.get());

        categoryRepository.save(category.get());
    }
}

