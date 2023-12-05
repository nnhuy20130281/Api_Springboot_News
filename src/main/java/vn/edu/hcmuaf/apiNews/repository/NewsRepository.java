package vn.edu.hcmuaf.apiNews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.apiNews.entity.News;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    // Custom queries can be added here if needed
    List<News> findByIsDeleteFalse();
}
