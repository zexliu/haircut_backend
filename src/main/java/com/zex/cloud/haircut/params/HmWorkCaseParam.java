package com.zex.cloud.haircut.params;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class HmWorkCaseParam {

    @NotNull
    private Long stylistId;

    private String title;

    private String introduction;

    private String images;

    private Integer seq;


}
