package com.zex.cloud.haircut.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2020-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="HmStylistDomainRelation对象", description="")
public class HmStylistDomainRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private Long domainId;

    private Long stylistId;


}
