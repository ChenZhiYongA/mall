package com.macro.mall.controller;



import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkGoodsPidGenerateRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkGoodsPidQueryRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkGoodsPromotionUrlGenerateRequest;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsPidGenerateResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsPidQueryResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsPromotionUrlGenerateResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static com.macro.mall.config.Pddconfig.clientId;
import static com.macro.mall.config.Pddconfig.clientSecret;


@Controller
@Api(tags = "TuiguangweiController", description = "推广")
@RequestMapping("/extension")
public class TuiguangweiController {

    @ApiOperation("创建推广位")
    @RequestMapping(value = "/xinjiantuiguang", method = RequestMethod.POST)
    @ResponseBody
    public PddDdkGoodsPidGenerateResponse installtuiguang(long number)throws Exception {
            String clientIds = clientId;
            String clientSecrets = clientSecret;
            PopClient client = new PopHttpClient(clientIds, clientSecrets);

            PddDdkGoodsPidGenerateRequest request = new PddDdkGoodsPidGenerateRequest();

              //number = 10;

            request.setNumber(number);
            List<String> pIdNameList = new ArrayList<>();
            pIdNameList.add("duoping");
            request.setPIdNameList(pIdNameList);
            PddDdkGoodsPidGenerateResponse response = client.syncInvoke(request);
            //System.out.println(JsonUtil.transferToJson(response));
        return response;
    }

    @ApiOperation("查询推广位")
    @RequestMapping(value = "/chaxuntuiguang", method = RequestMethod.GET)
    @ResponseBody
    public PddDdkGoodsPidQueryResponse selcettuiguang()throws Exception {
        String clientIds = clientId;
        String clientSecrets = clientSecret;
        PopClient client = new PopHttpClient(clientIds, clientSecrets);

        PddDdkGoodsPidQueryRequest request = new PddDdkGoodsPidQueryRequest();
        //request.setPage(0);
       // request.setPageSize(0);
        List<String> pidList = new ArrayList<>();
        pidList.add("duoping");
        //request.setPidList(pidList);
       // request.setStatus(0);
        PddDdkGoodsPidQueryResponse response = client.syncInvoke(request);
        //System.out.println(JsonUtil.transferToJson(response));
        return response;
    }

    @ApiOperation("转连接")
    @RequestMapping(value = "/zhuanlianjie", method = RequestMethod.GET)
    @ResponseBody
    public PddDdkGoodsPromotionUrlGenerateResponse lianjie(Long goodsId,String pid)throws Exception {
        String clientIds = clientId;
        String clientSecrets = clientSecret;
        PopClient client = new PopHttpClient(clientIds, clientSecrets);

        PddDdkGoodsPromotionUrlGenerateRequest request = new PddDdkGoodsPromotionUrlGenerateRequest();
        //request.setCustomParameters('str');
        //request.setGenerateMallCollectCoupon(false);
        //request.setGenerateQqApp(false);
       // request.setGenerateSchemaUrl(false);
        //request.setGenerateShortUrl(false);
       // request.setGenerateWeappWebview(false);
       // request.setGenerateWeiboappWebview(false);
       // request.setGenerateWeApp(false);
        List<Long> goodsIdList = new ArrayList<>();
        goodsIdList.add(goodsId);
        request.setGoodsIdList(goodsIdList);
        //request.setMultiGroup(false);
        request.setPId(pid);
        //request.setSearchId('str');
       // request.setZsDuoId(0L);
        //List<String> roomIdList = new ArrayList<>();
        //roomIdList.add('str');
       // request.setRoomIdList(roomIdList);
       // List<String> targetIdList = new ArrayList<>();
        //targetIdList.add('str');
        //request.setTargetIdList(targetIdList);
        PddDdkGoodsPromotionUrlGenerateResponse response = client.syncInvoke(request);
        System.out.println(JsonUtil.transferToJson(response));
        return response;
    }
}
