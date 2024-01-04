package vn.edu.hcmuaf.apiNews.service;

import vn.edu.hcmuaf.apiNews.entity.News;
import vn.edu.hcmuaf.apiNews.model.dto.NewsDto;

import java.util.List;

public interface NewsService {

    List<News> getAllNews();

    List<News> getNewsAll();

    List<News> getAllNewsHidden();

    News getNewsById(long id);

    News createNews(NewsDto newsDto);

    News updateNews(long id, News news);

    void updateNewsHidden(long id);

    void deleteNews(long id);

}

