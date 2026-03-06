package com.iovie.gourmet_manager_api.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    private String email;
    private String name;
}