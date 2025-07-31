package uz.ludito.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private Long id;
    private Long userId;
    private String userFullName;
    private BigDecimal amount;
    private String type;
    private LocalDateTime timestamp;
    private String description;
}