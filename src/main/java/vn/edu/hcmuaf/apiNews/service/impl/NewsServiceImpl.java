package vn.edu.hcmuaf.apiNews.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.apiNews.entity.News;
import vn.edu.hcmuaf.apiNews.repository.NewsRepository;
import vn.edu.hcmuaf.apiNews.service.NewsService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public List<News> getAllNews() {
        return newsRepository.findByIsDeleteFalse();
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
    public News createNews(News news) {
        return newsRepository.save(news);
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

}

