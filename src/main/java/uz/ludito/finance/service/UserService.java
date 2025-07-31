package uz.ludito.finance.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import uz.ludito.finance.dto.UserDto;
import uz.ludito.finance.entity.User;
import uz.ludito.finance.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public UserDto create(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return toDto(repo.save(user));
    }

    public UserDto update(Long id, UserDto userDto) {
        User existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        BeanUtils.copyProperties(userDto, existing);
        return toDto(repo.save(existing));
    }

    public UserDto getById(Long id) {
        return repo.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<UserDto> getAll() {
        return repo.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    private UserDto toDto(User user) {
        // TODO рассмотреть MapStruct или писать конвертеры вручную
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user, dto); //NOTE should not be used in real life
        return dto;
    }
}
