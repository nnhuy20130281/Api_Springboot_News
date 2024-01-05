package vn.edu.hcmuaf.apiNews.service;

import vn.edu.hcmuaf.apiNews.entity.News;
import vn.edu.hcmuaf.apiNews.model.dto.NewsDto;

import java.util.List;

public interface NewsService {

    List<News> getAllNews();

    List<News> getNewsAll();

    List<News> getAllNewsHidden();

    News getNewsById(long id);

    String createNews(NewsDto newsDto);

    String updateNews(long id, NewsDto newsDto);

    void updateNewsHidden(long id);

    void deleteNews(long id);

}

