package com.zex.cloud.haircut.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonListLongSerializer;
import com.zex.cloud.haircut.entity.SyUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SyUserDetail extends SyUser {
    @JsonSerialize(using = JsonListLongSerializer.class)
    List<Long> groupIds;

    @JsonSerialize(using = JsonListLongSerializer.class)
    List<Long> roleIds;
}
