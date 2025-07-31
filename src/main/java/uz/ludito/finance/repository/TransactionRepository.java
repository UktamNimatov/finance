package uz.ludito.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ludito.finance.entity.Transaction;
import uz.ludito.finance.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
    List<Transaction> findByTimestampBetween(LocalDateTime from, LocalDateTime to);
}
