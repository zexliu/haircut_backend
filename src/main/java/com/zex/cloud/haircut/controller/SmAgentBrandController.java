package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.SmAgent;
import com.zex.cloud.haircut.entity.SmAgentBrand;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.ISmAgentBrandService;
import com.zex.cloud.haircut.service.ISmAgentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-03-18
 */
@RestController
@RequestMapping("/api/v1/agent/bank")
@Api(tags = "代理商银行卡")
public class SmAgentBrandController {

    @Autowired
    private ISmAgentBrandService iSmAgentBrandService;

    @Autowired
    private ISmAgentService iSmAgentService;


    @GetMapping("/current")
    public List<SmAgentBrand> list(){
        SmAgent agent = iSmAgentService.getByUserId(RequestHolder.user().getId());
        return iSmAgentBrandService.list(new LambdaQueryWrapper<SmAgentBrand>()
        .eq(SmAgentBrand::getAgentId,agent.getId()));
    }

    @PostMapping("/current")
    public SmAgentBrand create(@RequestBody  SmAgentBrand smAgentBrand){
        SmAgent agent = iSmAgentService.getByUserId(RequestHolder.user().getId());
        smAgentBrand.setAgentId(agent.getId());
        smAgentBrand.setId(null);
      return    iSmAgentBrandService.create(smAgentBrand);
    }

    @PutMapping("/current/{id}")
    public SmAgentBrand update(@RequestBody SmAgentBrand smAgentBrand,@PathVariable Long id){
        SmAgent agent = iSmAgentService.getByUserId(RequestHolder.user().getId());
        smAgentBrand.setAgentId(agent.getId());
        smAgentBrand.setId(id);
        return  iSmAgentBrandService.update(smAgentBrand);
    }

    @DeleteMapping("/current/{id}")
    public String delete(@PathVariable Long id){
        SmAgent agent = iSmAgentService.getByUserId(RequestHolder.user().getId());
        iSmAgentBrandService.remove(new LambdaQueryWrapper<SmAgentBrand>()
        .eq(SmAgentBrand::getId,id)
        .eq(SmAgentBrand::getAgentId,agent.getId()));
        return SimpleResp.SUCCESS;

    }

    @GetMapping("/{id}")
    public SmAgentBrand update(@PathVariable Long id){
        return  iSmAgentBrandService.getById(id);
    }

}
