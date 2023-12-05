package vn.edu.hcmuaf.apiNews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.apiNews.entity.Category;
import vn.edu.hcmuaf.apiNews.entity.News;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByIsDeleteFalse();
}
