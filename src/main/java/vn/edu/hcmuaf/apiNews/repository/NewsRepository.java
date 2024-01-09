package vn.edu.hcmuaf.apiNews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.apiNews.entity.News;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByIsDeleteFalse();

    List<News> findByIsDeleteTrue();

    List<News> findTop3ByIsDeleteFalseOrderByCreatedDateDesc();

    List<News> findTop3ByCategories_IdAndIsDeleteFalseOrderByCreatedDateDesc(long categoryId);
}
