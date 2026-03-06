package com.iovie.gourmet_manager_api.user;

import com.iovie.gourmet_manager_api.user.dto.PasswordChangeRequest;
import com.iovie.gourmet_manager_api.user.dto.UserMapper;
import com.iovie.gourmet_manager_api.user.dto.UserRegistrationRequest;
import com.iovie.gourmet_manager_api.user.dto.UserUpdateRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    public User create(UserRegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public User update(Long userId, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("User not found"));
        user.setEmail(userUpdateRequest.getEmail());
        user.setName(userUpdateRequest.getName());
        return userRepository.save(user);
    }

    public void updateLastLogin(User user) {
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }

    public User deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setEnabled(false);
        return userRepository.save(user);
    }

    public User activateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setEnabled(true);
        return userRepository.save(user);
    }

    public void changePassword(Long userId, PasswordChangeRequest passwordChangeRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (!passwordEncoder.matches(passwordChangeRequest.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
        userRepository.save(user);
    }
}
