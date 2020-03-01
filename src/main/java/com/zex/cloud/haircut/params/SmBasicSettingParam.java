package com.zex.cloud.haircut.params;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Zex
 * @since 2020-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SmBasicSetting对象", description="")
public class SmBasicSettingParam implements Serializable {

    @NotNull
    private BigDecimal shopCommissionProportion;
    @NotNull
    private BigDecimal userCommissionProportion;
    @NotNull
    private BigDecimal userFirstAmount;
    @NotNull
    private Integer shopFreeMonth;

}
