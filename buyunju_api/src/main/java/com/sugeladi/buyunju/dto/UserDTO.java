package com.sugeladi.buyunju.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class UserDTO extends BaseDatabaseDTO{

    private String username;
}
