package com.zex.cloud.haircut.security;

import com.zex.cloud.haircut.entity.SyRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestUser {

    private Long id;

    private String username;

    private String nickname;

    private String avatar;

    private String mobile;

    private List<String> roles;

}
