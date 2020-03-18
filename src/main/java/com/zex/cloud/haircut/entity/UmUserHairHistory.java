package com.zex.cloud.haircut.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Zex
 * @since 2020-03-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("um_user_hair_history")
@ApiModel(value="UmUserHairHistory对象", description="")
public class UmUserHairHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String images;

    private LocalDateTime createAt;

    private Long userId;


}
