package uz.ludito.finance.dto;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String username;
    private String password;
}