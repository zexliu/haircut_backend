package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.HmStylistResume;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.HmStyListResumeParam;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-03-09
 */
public interface IHmStylistResumeService extends IService<HmStylistResume> {

    void updateResumes(Long id, List<HmStyListResumeParam> resumes);

    void removeByStylist(Long stylistId);

    List<HmStylistResume> getByStylistId(Long stylistId);

}
