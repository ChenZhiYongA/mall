package com.macro.mall.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.*;
import com.macro.mall.mapper.BalanceMapper;
import com.macro.mall.model.OmsOrder;
import com.macro.mall.model.Users;
import com.macro.mall.service.OmsOrderService;
import com.macro.mall.service.UserService;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkGoodsDetailRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkOrderListRangeGetRequest;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsDetailResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkOrderListRangeGetResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.macro.mall.config.Pddconfig.clientId;
import static com.macro.mall.config.Pddconfig.clientSecret;
import static com.pdd.pop.ext.glassfish.grizzly.http.util.Header.Date;

/**
 * 订单管理Controller
 * Created by macro on 2018/10/11.
 */
@Controller
@Api(tags = "OmsOrderController", description = "订单管理")
@RequestMapping("/order")
public class OmsOrderController {
    @Autowired
    private OmsOrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private BalanceMapper balanceMapper;

    @ApiOperation("查询订单")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<OmsOrder>> list(OmsOrderQueryParam queryParam,
                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<OmsOrder> orderList = orderService.list(queryParam, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(orderList));
    }

    @ApiOperation("批量发货")
    @RequestMapping(value = "/update/delivery", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delivery(@RequestBody List<OmsOrderDeliveryParam> deliveryParamList) {
        int count = orderService.delivery(deliveryParamList);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("批量关闭订单")
    @RequestMapping(value = "/update/close", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult close(@RequestParam("ids") List<Long> ids, @RequestParam String note) {
        int count = orderService.close(ids, note);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("批量删除订单")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        int count = orderService.delete(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取订单详情:订单信息、商品信息、操作记录")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<OmsOrderDetail> detail(@PathVariable Long id) {
        OmsOrderDetail orderDetailResult = orderService.detail(id);
        return CommonResult.success(orderDetailResult);
    }

    @ApiOperation("修改收货人信息")
    @RequestMapping(value = "/update/receiverInfo", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateReceiverInfo(@RequestBody OmsReceiverInfoParam receiverInfoParam) {
        int count = orderService.updateReceiverInfo(receiverInfoParam);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改订单费用信息")
    @RequestMapping(value = "/update/moneyInfo", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateReceiverInfo(@RequestBody OmsMoneyInfoParam moneyInfoParam) {
        int count = orderService.updateMoneyInfo(moneyInfoParam);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("备注订单")
    @RequestMapping(value = "/update/note", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateNote(@RequestParam("id") Long id,
                                   @RequestParam("note") String note,
                                   @RequestParam("status") Integer status) {
        int count = orderService.updateNote(id, note, status);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }


    @ApiOperation("新增订单")
    @RequestMapping(value = "/insert/order", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult insertOmsOrder( OmsOrderDetail omsOrderDetail){
        boolean count = orderService.insertDingDan(omsOrderDetail);
        if (count == true) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("对家拼拼多多订单")
    @RequestMapping(value = "/chaxundingdan", method = RequestMethod.GET)
    @ResponseBody
    public PddDdkOrderListRangeGetResponse insertchaxundingdan()throws Exception{

        String clientIds = clientId;
        //System.out.println(clientIds);
        String clientSecrets = clientSecret;
        //System.out.println(clientSecrets);
        PopClient client = new PopHttpClient(clientIds, clientSecrets);
        PddDdkOrderListRangeGetRequest request = new PddDdkOrderListRangeGetRequest();
        //转型
        Date date = new Date();
        //设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取String类型的时间
        String createdate = sdf.format(date);
        request.setEndTime(createdate);
        //request.setLastOrderId('str');
        //request.setPageSize(0);b
        request.setStartTime("2020-01-25 23:06:22");
        PddDdkOrderListRangeGetResponse response = client.syncInvoke(request);
        //获取拼多多方面的订单信息
        List<PddDdkOrderListRangeGetResponse.OrderListGetResponseOrderListItem> orderList = response.getOrderListGetResponse().getOrderList();
        for (PddDdkOrderListRangeGetResponse.OrderListGetResponseOrderListItem orderListGetResponseOrderListItem : orderList) {
            //获取实际金额
            Long orderAmount = orderListGetResponseOrderListItem.getOrderAmount();
            //获取pid
            String pId = orderListGetResponseOrderListItem.getPId();
            //查询pid的用户表的信息
            Users usersByPid = userService.findUsersByPid(pId);
            //上级
            Integer parent = usersByPid.getParent();
            //根据上级id查询出pid
            String parentPid  = userService.findUsersByIid(parent).getPId();
            //上上级
            Integer parentp = usersByPid.getParentp();
            //根据上上级id查询出pid
            String parentpPid = userService.findUsersByIid(parentp).getPId();
            //分公司
            Integer fengongsi = usersByPid.getFengongsi();
            //根据分公司查询出pid
            String fengongsiPid = userService.findUsersByIid(fengongsi).getPId();

            Double money=null;
            //判断金额是几档
            if(orderAmount<5){
                money=0.3;
            }else if (orderAmount>=5&&orderAmount<=10){
                money=0.4;
            }else{
                money=orderAmount*0.4;
            }
            //判断该pid是什么类型
            String role = usersByPid.getRole();
            if("filiale".equals(role)){
                //给分公司分95%
                BigDecimal addMoney1  = new BigDecimal(money*0.95);
                //balanceMapper.updateyue(Integer.parseInt(pId), addMoney1, 2);
                //将佣金累加到用户表中
                userService.updateyongjin(fengongsiPid,addMoney1);
                //给自己公司分5%
                BigDecimal addMoney2  = new BigDecimal(money*0.05);
                userService.updateyongjin("001",addMoney2);
               // balanceMapper.updateyue(1, addMoney2, 2);
            }else if("individuals".equals(role)){
                //普通合伙人
                //自己60%
                BigDecimal addMoney1  = new BigDecimal(money*0.6);
                userService.updateyongjin(pId,addMoney1);
                //balanceMapper.updateyue(Integer.parseInt(pId), addMoney1, 2);
                //判断是否有上级和上上级
                if(parentp==null){
                    //判断上级是普通还是高级还是合伙人
                    Users usersByPid1 = userService.findUsersByPid(parent.toString());
                    String role1 = usersByPid1.getRole();
                    if("filiale".equals(role1)){
                        //如果直接是分公司
                        BigDecimal addMoney2  = new BigDecimal(money*0.3);
                        //分30%
                        userService.updateyongjin( parentPid,addMoney2);
                        //balanceMapper.updateyue(parent, addMoney2, 2);
                        //给自己公司分10%
                        BigDecimal addMoney3  = new BigDecimal(money*0.1);
                        userService.updateyongjin( "001",addMoney3);
                        //balanceMapper.updateyue(parent, addMoney3, 2);
                    }
                    if("individuals".equals(role1)){
                        //如果直接是普通合伙人
                        BigDecimal addMoney2  = new BigDecimal(money*0.15);
                        //分15%
                        userService.updateyongjin( parentPid,addMoney2);
                        //balanceMapper.updateyue(parent, addMoney2, 2);
                        //给自己公司分25%
                        BigDecimal addMoney3  = new BigDecimal(money*0.25);
                        userService.updateyongjin( "001",addMoney3);
                        //balanceMapper.updateyue(1, addMoney3, 2);
                    }
                    if("teams".equals(role1)){
                        //如果直接是高级合伙人
                        BigDecimal addMoney2  = new BigDecimal(money*0.20);
                        //分20%
                        userService.updateyongjin( parentPid,addMoney2);
                        //balanceMapper.updateyue(parent, addMoney2, 2);
                        //给自己公司分20%
                        BigDecimal addMoney3  = new BigDecimal(money*0.20);
                        userService.updateyongjin( "001",addMoney3);
                        //balanceMapper.updateyue(1, addMoney3, 2);
                    }
                }else{
                    //有上上级的情况
                    //获取上级和上上级信息
                    Users usersByPid1 = userService.findUsersByPid(parent.toString());
                    if("individuals".equals(usersByPid1.getRole())){
                        //上级为普通合伙人
                        BigDecimal addMoney2  = new BigDecimal(money*0.15);
                        //分15%
                        userService.updateyongjin( parentPid,addMoney2);
                        //balanceMapper.updateyue(parent, addMoney2, 2);
                        //上上级15%
                        BigDecimal addMoney3  = new BigDecimal(money*0.15);
                        userService.updateyongjin( parentpPid,addMoney3);
                        //balanceMapper.updateyue(parentp, addMoney3, 2);
                        //给分公司5%
                        BigDecimal addMoney4  = new BigDecimal(money*0.05);
                        userService.updateyongjin( fengongsiPid,addMoney4);
                        //balanceMapper.updateyue(2, addMoney4, 2);
                        //给分自己公司5%
                        BigDecimal addMoney5  = new BigDecimal(money*0.05);
                        userService.updateyongjin( "001",addMoney5);
                        //balanceMapper.updateyue(1, addMoney5, 2);
                    }
                    if("teams".equals(usersByPid1.getRole())){
                        //上级为高级合伙人
                        BigDecimal addMoney2  = new BigDecimal(money*0.20);
                        //分20%
                        userService.updateyongjin( parentPid,addMoney2);
                        //balanceMapper.updateyue(parent, addMoney2, 2);
                        //上上级10%
                        BigDecimal addMoney3  = new BigDecimal(money*0.10);
                        userService.updateyongjin( parentpPid,addMoney3);
                        //balanceMapper.updateyue(parentp, addMoney3, 2);
                        //给分公司5%
                        BigDecimal addMoney4  = new BigDecimal(money*0.05);
                        userService.updateyongjin( fengongsiPid,addMoney4);
                        //balanceMapper.updateyue(2, addMoney4, 2);
                        //给分自己公司5%
                        BigDecimal addMoney5  = new BigDecimal(money*0.05);
                        userService.updateyongjin( "001",addMoney5);
                        //balanceMapper.updateyue(1, addMoney5, 2);
                    }
                }
            }else if("teams".equals(role)){
                //如果为高级合伙人
                //自己70%
                BigDecimal addMoney1  = new BigDecimal(money*0.7);
                userService.updateyongjin( pId,addMoney1);
                //balanceMapper.updateyue(Integer.parseInt(pId), addMoney1, 2);
                //判断是否有上级和上上级
                if(parentp==null){
                    //判断上级是普通还是高级还是合伙人
                    Users usersByPid1 = userService.findUsersByPid(parent.toString());
                    String role1 = usersByPid1.getRole();
                    if("filiale".equals(role1)){
                        //如果直接是分公司
                        BigDecimal addMoney2  = new BigDecimal(money*0.2);
                        //分30%
                        userService.updateyongjin( parentPid,addMoney2);
                        //balanceMapper.updateyue(parent, addMoney2, 2);
                        //给自己公司分10%
                        BigDecimal addMoney3  = new BigDecimal(money*0.1);
                        userService.updateyongjin( "001",addMoney3);
                        //balanceMapper.updateyue(1, addMoney3, 2);
                    }
                    if("individuals".equals(role1)){
                        //如果直接是普通合伙人
                        BigDecimal addMoney2  = new BigDecimal(money*0.05);
                        //分5%
                        userService.updateyongjin(parentPid,addMoney2);
                        //balanceMapper.updateyue(parent, addMoney2, 2);
                        //给自己公司分25%
                        BigDecimal addMoney3  = new BigDecimal(money*0.25);
                        userService.updateyongjin("001",addMoney3);
                        //balanceMapper.updateyue(1, addMoney3, 2);
                    }
                    if("teams".equals(role1)){
                        //如果直接是高级合伙人
                        BigDecimal addMoney2  = new BigDecimal(money*0.10);
                        //分10%
                        userService.updateyongjin( parentPid,addMoney2);
                        //balanceMapper.updateyue(parent, addMoney2, 2);
                        //给自己公司分20%
                        BigDecimal addMoney3  = new BigDecimal(money*0.20);
                        userService.updateyongjin( "001",addMoney3);
                        //balanceMapper.updateyue(1, addMoney3, 2);
                    }
                }else{
                    //有上上级的情况
                    //获取上级和上上级信息
                    Users usersByPid1 = userService.findUsersByPid(parent.toString());
                    if("individuals".equals(usersByPid1.getRole())){
                        //上级为普通合伙人
                        BigDecimal addMoney2  = new BigDecimal(money*0.05);
                        //分5%
                        userService.updateyongjin( parentPid,addMoney2);
                        //balanceMapper.updateyue(parent, addMoney2, 2);
                        //上上级15%
                        BigDecimal addMoney3  = new BigDecimal(money*0.15);
                        userService.updateyongjin( parentpPid,addMoney3);
                        //balanceMapper.updateyue(parentp, addMoney3, 2);
                        //给分公司5%
                        BigDecimal addMoney4  = new BigDecimal(money*0.05);
                        userService.updateyongjin(fengongsiPid,addMoney4);
                        //balanceMapper.updateyue(2, addMoney4, 2);
                        //给分自己公司5%
                        BigDecimal addMoney5  = new BigDecimal(money*0.05);
                        userService.updateyongjin( "001",addMoney5);
                        //balanceMapper.updateyue(1, addMoney5, 2);
                    }
                    if("teams".equals(usersByPid1.getRole())){
                        //上级为高级合伙人
                        BigDecimal addMoney2  = new BigDecimal(money*0.10);
                        //分15%
                        userService.updateyongjin( parentPid,addMoney2);
                        //balanceMapper.updateyue(parent, addMoney2, 2);
                        //上上级10%
                        BigDecimal addMoney3  = new BigDecimal(money*0.10);
                        userService.updateyongjin( parentpPid,addMoney3);
                        //balanceMapper.updateyue(parentp, addMoney3, 2);
                        //给分公司5%
                        BigDecimal addMoney4  = new BigDecimal(money*0.05);
                        userService.updateyongjin( fengongsiPid,addMoney4);
                        //balanceMapper.updateyue(2, addMoney4, 2);
                        //给分自己公司5%
                        BigDecimal addMoney5  = new BigDecimal(money*0.05);
                        userService.updateyongjin( "001",addMoney5);
                        //balanceMapper.updateyue(1, addMoney5, 2);
                    }
                }
            }
        }
        return response;
    }
    @ApiOperation("查询卷后价")
    @RequestMapping(value = "/juanhou", method = RequestMethod.GET)
    @ResponseBody
      public int chaxunjuanhou(Integer id){
        int shiji = orderService.selcetshiji(id);

               return shiji;
    }
}
