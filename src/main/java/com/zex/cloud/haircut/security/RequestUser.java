package com.zex.cloud.haircut.security;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonLongSerializer;
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

    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    @JsonSerialize(using = JsonLongSerializer.class)
    private Long shopId;

    private String username;

    private String nickname;

    private String avatar;

    private String mobile;

    private List<String> roles;

}
