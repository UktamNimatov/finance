package uz.ludito.finance.controller;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ludito.finance.dto.TransactionDto;
import uz.ludito.finance.service.TransactionService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TransactionDto> create(
            @Valid @RequestBody TransactionDto transactionDto) {
        return ResponseEntity.ok(service.create(transactionDto));
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> listAll() {
        return ResponseEntity.ok(service.listAllAsDto());
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<TransactionDto>> listByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.listByUserAsDto(userId));
    }

    @GetMapping("/by-period")
    public ResponseEntity<List<TransactionDto>> listByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return ResponseEntity.ok(service.listByPeriodAsDto(from, to));
    }
}
