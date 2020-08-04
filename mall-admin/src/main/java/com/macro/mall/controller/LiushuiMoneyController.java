package com.macro.mall.controller;


import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.PmsProductParam;
import com.macro.mall.model.CmsPrefrenceArea;
import com.macro.mall.model.LiushuiMoney;
import com.macro.mall.service.LiushuiMoneyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api(tags = "LiushuiMoneyController", description = "流水记录")
@RequestMapping("/Lliushui")
public class LiushuiMoneyController {

    @Autowired
    private LiushuiMoneyService liushuiMoneyService;


    @ApiOperation("根据团长id查询流水信息")
    @RequestMapping(value = "/Lli", method = RequestMethod.GET)
    @ResponseBody
    public List<LiushuiMoney> findByUserId( Integer userid,
                                            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        System.out.println("userid = " + userid);
        List<LiushuiMoney> liushuiMonies = liushuiMoneyService.selectLiushui(userid,pageSize,pageNum);
        return liushuiMonies;
    }

    @ApiOperation("添加充值流水信息")
    @RequestMapping(value = "/insertliushuiCZ", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addCZliushui(LiushuiMoney liushuiMoney) {
        int count = liushuiMoneyService.insertLiushuiCZ(liushuiMoney);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("添加提现流水信息")
    @RequestMapping(value = "/insertliushuiTX", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addTXliushui(LiushuiMoney liushuiMoney) {
        int count = liushuiMoneyService.insertLiushuiTX(liushuiMoney);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("修改充值流水信息")
    @RequestMapping(value = "/updateliushuiCZ",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateCZLiushui(Integer id){
        //进行修改
        int count = liushuiMoneyService.updateLiushuiCZ(id);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("修改提现流水信息")
    @RequestMapping(value = "/updateliushuiTX",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateTXLiushui(Integer id){
        //进行修改
        int count = liushuiMoneyService.updateLiushuiTX(id);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }
}
