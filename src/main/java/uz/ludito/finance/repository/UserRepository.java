package uz.ludito.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ludito.finance.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
