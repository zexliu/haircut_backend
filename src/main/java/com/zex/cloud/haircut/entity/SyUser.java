package com.zex.cloud.haircut.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonLongSerializer;
import com.zex.cloud.haircut.enums.GenderType;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Zex
 * @since 2020-02-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SyUser对象", description="")
public class SyUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    private String email;

    private LocalDateTime createAt;

    private String mobile;

    private LocalDateTime loginAt;

    private LocalDateTime loginIp;
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long operatorId;

    private String operatorIp;

    private String remark;

    private LocalDateTime operatorAt;

    private Boolean enable;

    private LocalDateTime expireAt;

    private Boolean locked;

    private String nickname;

    private String avatar;

    private GenderType gender;

}
