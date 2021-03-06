package com.zex.cloud.haircut.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonListLongSerializer;
import com.zex.cloud.haircut.entity.SyUserGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SyUserGroupDetail extends SyUserGroup {

    @JsonSerialize(using = JsonListLongSerializer.class)
    List<Long> roleIds;
}
