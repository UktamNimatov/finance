package uz.ludito.finance.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ludito.finance.dto.TransactionDto;
import uz.ludito.finance.entity.Transaction;
import uz.ludito.finance.entity.User;
import uz.ludito.finance.repository.TransactionRepository;
import uz.ludito.finance.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository txRepo;
    private final UserRepository userRepo;

    public TransactionService(TransactionRepository txRepo, UserRepository userRepo) {
        this.txRepo = txRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public TransactionDto create(TransactionDto transactionDto) {
        User user = userRepo.findById(transactionDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Transaction transaction = toEntity(transactionDto, user);
        saveNewBalanceForUser(user, transactionDto);

        return convertToDto(txRepo.save(transaction));
    }

    public List<TransactionDto> listAllAsDto() {
        return txRepo.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    public List<TransactionDto> listByUserAsDto(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return txRepo.findByUser(user).stream()
                .map(this::convertToDto)
                .toList();
    }


    public List<TransactionDto> listByPeriodAsDto(LocalDateTime from, LocalDateTime to) {
        return txRepo.findByTimestampBetween(from, to).stream()
                .map(this::convertToDto)
                .toList();
    }

    private Transaction toEntity(TransactionDto dto, User user) {
        Transaction tx = new Transaction();
        tx.setUser(user);
        tx.setAmount(dto.getAmount());
        tx.setType(dto.getType());
        tx.setTimestamp(dto.getTimestamp());
        tx.setDescription(dto.getDescription());
        return tx;
    }

    private TransactionDto convertToDto(Transaction transaction) {
        // TODO Можно использовать MapStruct
        return new TransactionDto(
                transaction.getId(),
                transaction.getUser().getId(),
                transaction.getUser().getFullName(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getTimestamp(),
                transaction.getDescription()
        );
    }

    private void saveNewBalanceForUser(User user, TransactionDto transactionDto) {
        BigDecimal newBalance = "CREDIT".equals(transactionDto.getType())
                ? user.getBalance().add(transactionDto.getAmount())
                : user.getBalance().subtract(transactionDto.getAmount());
        user.setBalance(newBalance);
        userRepo.save(user);
    }
}