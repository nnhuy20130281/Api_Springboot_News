package vn.edu.hcmuaf.apiNews.service;

import vn.edu.hcmuaf.apiNews.entity.News;

import java.util.List;

public interface NewsService {

    List<News> getAllNews();

    List<News> getNewsAll();

    List<News> getAllNewsHidden();

    News getNewsById(long id);

    News createNews(News news);

    News updateNews(long id, News news);

    void updateNewsHidden(long id);

    void deleteNews(long id);

}

