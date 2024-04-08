package criff.academy.project.repository;

import criff.academy.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    <optional>User findByUsername(String username);
}

