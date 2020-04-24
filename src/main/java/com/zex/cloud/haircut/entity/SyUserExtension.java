package com.zex.cloud.haircut.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
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
@ApiModel(value="SyUserExtension对象", description="")
public class SyUserExtension implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private String unionId;

    private Long userId;

    private LocalDate birthDay;

    private String job;

    private String education;

    private String face;

    private String hairHeight;

    private String hairQuality;

    private String hairStyle;

    private String idol;


}
