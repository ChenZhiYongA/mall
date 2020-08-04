package com.macro.mall.controller;


import com.macro.mall.common.api.CommonResult;
import com.macro.mall.model.Balance;
import com.macro.mall.model.LiushuiMoney;
import com.macro.mall.service.BalanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;

@Controller
@Api(tags = "BalanceController", description = "余额")
@RequestMapping("/yue")
public class BalanceController {


    @Autowired
    private BalanceService balanceService;


    @ApiOperation("根据团长id查询余额")
    @RequestMapping(value = "/qian", method = RequestMethod.GET)
    @ResponseBody
    public BigDecimal moneyByUserId(Integer userid) {
        System.out.println("userid = " + userid);
        BigDecimal selcetyuey = balanceService.selcetyuemoney(userid);
        return selcetyuey;
    }
    @ApiOperation("根据团长id查询余额表")
    @RequestMapping(value = "/allqian", method = RequestMethod.GET)
    @ResponseBody
    public List<Balance> yueByUserId(Integer userid) {
        System.out.println("userid = " + userid);
        List<Balance> selcetallyuey = balanceService.selectallbalance(userid);
        return selcetallyuey;
    }



}
