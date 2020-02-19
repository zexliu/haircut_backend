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
 * @since 2020-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SmShop对象", description="")
public class SmShop implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

    private String logo;

    private String coverImage;

    private String introduction;

    private String address;

    private String provinceCode;

    private String cityCode;

    private String districtCode;

    private String businessLicense;

    private String leaderName;

    private String leaderMobile;

    private Boolean enable;

    private Double longitude;

    private Double latitude;

    private LocalDateTime createAt;

    private Float score;

    private String htmlInfo;

}
