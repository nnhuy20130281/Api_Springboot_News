package vn.edu.hcmuaf.apiNews.model.mapper;

import vn.edu.hcmuaf.apiNews.entity.News;
import vn.edu.hcmuaf.apiNews.model.dto.NewsDto;

import java.util.List;

public class NewsMapper {

    public static NewsDto toNewsDto(News news){
        NewsDto tmp = new NewsDto();
        tmp.setId(String.valueOf(news.getId()));
        tmp.setTitle(news.getTitle());
        tmp.setDescription(news.getDescription());
        tmp.setImage(news.getImage());
        tmp.setContent(news.getContent());
        tmp.setDelete(news.isDelete());
        tmp.setCreatedBy(news.getCreatedBy());
        tmp.setCreatedDate(news.getCreatedDate());
        tmp.setCategories(news.getCategories());
        return tmp;
    }

    public static List<NewsDto> toNewsDto(List<News> news){
        return news.stream().map(NewsMapper::toNewsDto).collect(java.util.stream.Collectors.toList());
    }
}
