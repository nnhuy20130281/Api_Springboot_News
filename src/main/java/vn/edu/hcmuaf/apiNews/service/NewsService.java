package vn.edu.hcmuaf.apiNews.service;

import vn.edu.hcmuaf.apiNews.entity.News;
import vn.edu.hcmuaf.apiNews.model.dto.NewsDto;
import vn.edu.hcmuaf.apiNews.model.dto.UpdateNews;

import java.util.List;

public interface NewsService {

    List<NewsDto> getAllNews();

    List<NewsDto> getNewsAll();

    List<NewsDto> getAllNewsHidden();

    NewsDto getNewsById(long id);

    String createNews(UpdateNews updateNews);

    String updateNews(long id, UpdateNews updateNews);

    void updateNewsHidden(long id);

    void deleteNews(long id);

    List<NewsDto> getNewsFlash();

    List<NewsDto> getNewsFlashByCategory(long categoryId);

}

