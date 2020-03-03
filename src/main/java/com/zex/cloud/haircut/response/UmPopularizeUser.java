package com.zex.cloud.haircut.response;

import com.zex.cloud.haircut.entity.UmPopularize;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UmPopularizeUser extends UmPopularize {

    private String mobile;

    private String nickname;

    private String avatar;

}
