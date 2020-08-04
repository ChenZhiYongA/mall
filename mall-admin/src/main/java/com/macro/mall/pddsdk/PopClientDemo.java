package com.macro.mall.pddsdk;

import com.macro.mall.config.Pddconfig;
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkGoodsDetailRequest;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsDetailResponse;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

public class PopClientDemo extends Pddconfig {


    public static void main(String[] args) throws Exception {
        //System.out.println(clientId);
        String clientIds = clientId;
        //System.out.println(clientIds);
        String clientSecrets = clientSecret;
        //System.out.println(clientSecrets);
        PopClient client = new PopHttpClient(clientIds, clientSecrets);

        PddDdkGoodsDetailRequest request = new PddDdkGoodsDetailRequest();
        request.setCustomParameters("str");
        List<Long> goodsIdList = new ArrayList<>();


        //转型
        Integer a = 30124365;
        Long longvue = a.longValue();
        goodsIdList.add(longvue);



        request.setGoodsIdList(goodsIdList);
        request.setPid("str");
        request.setPlanType(0);
        request.setSearchId("str");
        request.setZsDuoId(0L);
        PddDdkGoodsDetailResponse response = client.syncInvoke(request);
        System.out.println(JsonUtil.transferToJson(response));
    }

}
