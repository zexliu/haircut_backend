package com.zex.cloud.haircut.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@ApiModel(value="FlywaySchemaHistory对象", description="")
public class FlywaySchemaHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "installed_rank", type = IdType.ASSIGN_ID)
    private Integer installedRank;

    private String version;

    private String description;

    private String type;

    private String script;

    private Integer checksum;

    private String installedBy;

    private LocalDateTime installedOn;

    private Integer executionTime;

    private Boolean success;


}
