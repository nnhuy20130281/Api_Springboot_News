package vn.edu.hcmuaf.apiNews.service;

import vn.edu.hcmuaf.apiNews.entity.News;

import java.util.List;

public interface NewsService {

    List<News> getAllNews();

    News getNewsById(long id);

    News createNews(News news);

    News updateNews(long id, News news);

    void deleteNews(long id);
}

