package com.macro.mall.controller;

import com.alibaba.druid.support.json.JSONParser;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.config.WxConfig;
import com.macro.mall.model.Balance;
import com.macro.mall.model.Users;
import com.macro.mall.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@Api(tags = "UserController", description = "团长表")
@RequestMapping("/tuanzhang")
public class UserController {

    @Resource
    private WxConfig wxConfig;

    @Autowired
    private UserService userService;

    @ApiOperation("判断用户是否注册")
    @RequestMapping(value = "/isRegister", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult selcetByOpenId(String openId) {
        Users usersByPid = userService.selcetByOpenId(openId);
        if (usersByPid != null) {

            Map<String, Object> map = new HashMap();
            map.put("pid", usersByPid.getPId());
            map.put("openId", usersByPid.getOpenid());
            return CommonResult.success(map);
        }

        return CommonResult.failed();
    }

    @ApiOperation("转换微信OpenID")
    @RequestMapping(value = "/getOpenId", method = RequestMethod.GET)
    @ResponseBody
    public String getOpenId(String code) {
        //获取用户的openId和用于的session_key
        Map<String, Object> map = wxConfig.getSessionByCode(code);
        System.out.println("map:-------->"+map);
        //判断置换的openid是否正确
        if (map.isEmpty()) {
            return null;
        } else {
            String openId = map.get("openid").toString();
            return openId;
        }
    }



    @ApiOperation("新用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult register(Users users) throws Exception {
        Integer result = userService.userRegister(users);
        if (result > 0) {
            return CommonResult.success("注册成功！");
        } else {
            return CommonResult.failed("注册失败！");
        }
    }


    @ApiOperation("查询Pid")
    @RequestMapping(value = "/pid", method = RequestMethod.GET)
    @ResponseBody
    public Users yueByUserId(String pid) {
        //System.out.println("userid = " + userid);
        Users usersByPid = userService.findUsersByPid(pid);
        return usersByPid;
    }

    @ApiOperation("Pid修改佣金")
    @RequestMapping(value = "/yongjin", method = RequestMethod.POST)
    @ResponseBody
    public int xiugaiyongjin(String pid, BigDecimal totalAmount) {
        //System.out.println("userid = " + userid);
        int yongjin = userService.updateyongjin(pid, totalAmount);
        return yongjin;
    }

    @ApiOperation("Id查询")
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    @ResponseBody
    public Users ByUserId(Integer id) {
        //System.out.println("userid = " + userid);
        Users ByPid = userService.findUsersByIid(id);
        return ByPid;
    }
}
