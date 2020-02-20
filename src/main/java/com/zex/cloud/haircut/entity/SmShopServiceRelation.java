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
@ApiModel(value="SmShopServiceRelation对象", description="")
public class SmShopServiceRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long shopId;

    private Long titleId;


}
