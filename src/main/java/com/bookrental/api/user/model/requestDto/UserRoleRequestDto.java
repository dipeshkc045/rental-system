package com.bookrental.api.user.model.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleRequestDto {
    private Long userId;
    private Long roleId;
    private Boolean isActive;
}
