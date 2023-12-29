package vn.edu.hcmuaf.apiNews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.apiNews.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long> {

   User findByEmail(String email);

    List<User> getAllUserByStatusTrueAndIsAdminFalse();

    List<User> getAllByStatusFalseAndIsAdminFalse();

    List<User> getAllUserByStatusTrueAndIsAdminTrue();

    List<User> getAllByStatusFalseAndIsAdminTrue();

    boolean existsByEmail(String email);
}
