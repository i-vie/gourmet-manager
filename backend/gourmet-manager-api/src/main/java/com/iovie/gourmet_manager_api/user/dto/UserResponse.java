package com.iovie.gourmet_manager_api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String email;
    private boolean enabled;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

