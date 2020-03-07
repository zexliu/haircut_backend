package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.GenderType;
import lombok.Data;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class SyUserParam {

    private String username;

    private String password;

    private String email;

    private String mobile;

    private Long operatorId;

    private String operatorIp;

    private String remark;

    private Boolean enable;

    private LocalDateTime expireAt;

    private Boolean locked;

    private String nickname;

    private String avatar;

    private GenderType gender;

    private List<Long> groupIds;

    private List<Long> roleIds;
}
