package com.macro.mall.controller;

import cn.hutool.json.JSONObject;
import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.PmsProductParam;
import com.macro.mall.dto.PmsProductQueryParam;
import com.macro.mall.dto.PmsProductResult;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.pddsdk.PopClientDemo;
import com.macro.mall.service.PmsProductService;
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkGoodsDetailRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkGoodsPromotionUrlGenerateRequest;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsDetailResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsPromotionUrlGenerateResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

import static com.macro.mall.config.Pddconfig.clientId;
import static com.macro.mall.config.Pddconfig.clientSecret;

/**
 * 商品管理Controller
 * Created by macro on 2018/4/26.
 */
@Controller
@Api(tags = "PmsProductController", description = "商品管理")
@RequestMapping("/product")
public class PmsProductController {
    @Autowired
    private PmsProductService productService;


    @ApiOperation("创建商品")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody PmsProductParam productParam, BindingResult bindingResult) {
        int count = productService.create(productParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("根据商品id获取商品编辑信息")
    @RequestMapping(value = "/updateInfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<PmsProductResult> getUpdateInfo(@PathVariable Long id) {
        PmsProductResult productResult = productService.getUpdateInfo(id);
        return CommonResult.success(productResult);
    }

    @ApiOperation("获取分享链接")
    @RequestMapping(value = "/getSmallUrl", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getSmallUrl(String shopId, String pid) throws Exception {
        String clientId = "16f0f35165da41ed90040eac240fb8b4";
        String clientSecret = "e6b597eb90ad538b1bf96fee3294fa23c3be45d1";
        PopClient client = new PopHttpClient(clientId, clientSecret);

        PddDdkGoodsPromotionUrlGenerateRequest request = new PddDdkGoodsPromotionUrlGenerateRequest();
        request.setGenerateMallCollectCoupon(false);
        request.setGenerateQqApp(false);
        request.setGenerateSchemaUrl(false);
        request.setGenerateShortUrl(true);
        request.setGenerateWeappWebview(false);
        request.setGenerateWeiboappWebview(false);
        request.setGenerateWeApp(false);
        List<Long> goodsIdList = new ArrayList<>();
        goodsIdList.add(Long.parseLong(shopId));
        request.setGoodsIdList(goodsIdList);
        request.setMultiGroup(false);
        request.setPId(pid);
        PddDdkGoodsPromotionUrlGenerateResponse response = client.syncInvoke(request);
        System.out.println(JsonUtil.transferToJson(response));
        return CommonResult.success(response.getGoodsPromotionUrlGenerateResponse().getGoodsPromotionUrlList().get(0).getShortUrl());
    }

    @ApiOperation("更新商品")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody PmsProductParam productParam, BindingResult bindingResult) {
        int count = productService.update(id, productParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("查询商品")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PmsProduct>> getList(PmsProductQueryParam productQueryParam,
                                                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<PmsProduct> productList = productService.list(productQueryParam, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(productList));
    }

    @ApiOperation("根据商品名称或货号模糊查询")
    @RequestMapping(value = "/simpleList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<PmsProduct>> getList(String keyword, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<PmsProduct> productList = productService.list(keyword, pageSize, pageNum);
        return CommonResult.success(productList);
    }

    @ApiOperation("批量修改审核状态")
    @RequestMapping(value = "/update/verifyStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateVerifyStatus(@RequestParam("ids") List<Long> ids,
                                           @RequestParam("verifyStatus") Integer verifyStatus,
                                           @RequestParam("detail") String detail) {
        int count = productService.updateVerifyStatus(ids, verifyStatus, detail);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("批量上下架")
    @RequestMapping(value = "/update/publishStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updatePublishStatus(@RequestParam("ids") List<Long> ids,
                                            @RequestParam("publishStatus") Integer publishStatus) {
        int count = productService.updatePublishStatus(ids, publishStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("批量推荐商品")
    @RequestMapping(value = "/update/recommendStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateRecommendStatus(@RequestParam("ids") List<Long> ids,
                                              @RequestParam("recommendStatus") Integer recommendStatus) {
        int count = productService.updateRecommendStatus(ids, recommendStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("批量设为新品")
    @RequestMapping(value = "/update/newStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateNewStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("newStatus") Integer newStatus) {
        int count = productService.updateNewStatus(ids, newStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("批量修改删除状态")
    @RequestMapping(value = "/update/deleteStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateDeleteStatus(@RequestParam("ids") List<Long> ids,
                                           @RequestParam("deleteStatus") Integer deleteStatus) {
        int count = productService.updateDeleteStatus(ids, deleteStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("根据id查询商品详情")
    @RequestMapping(value = "/xiangqing", method = RequestMethod.GET)
    @ResponseBody
    public PmsProduct yueByUserId(Long id) {
        //System.out.println("userid = " + userid);
        PmsProduct duct = productService.selcetgoodByid(id);
        // PmsProduct selcetallyuey = .selectallbalance(userid);
        return duct;
    }

    @ApiOperation("pdd根据id查询商品详情")
    @RequestMapping(value = "/jiansuo", method = RequestMethod.POST)
    @ResponseBody
    public PddDdkGoodsDetailResponse yueByUserId2(Long goodsId) throws Exception {
        //  JSONObject jsonResult = null;
        //System.out.println(clientId);
        String clientIds = clientId;
        //System.out.println(clientIds);
        String clientSecrets = clientSecret;
        //System.out.println(clientSecrets);
        PopClient client = new PopHttpClient(clientIds, clientSecrets);
        PddDdkGoodsDetailRequest request = new PddDdkGoodsDetailRequest();
        //request.setCustomParameters("str");
        List<Long> goodsIdList = new ArrayList<>();
        //goodsId = 30124365;
        //Integer a = goodsId;
        //Long longvue = a.longValue();
        goodsIdList.add(goodsId);

        request.setGoodsIdList(goodsIdList);
        //request.setPid("str");
        //request.setPlanType(0);
        //request.setSearchId("str");
        //request.setZsDuoId(0L);
        PddDdkGoodsDetailResponse response = client.syncInvoke(request);
        //   System.out.println(JsonUtil.transferToJson(response));
        //    String tt= JsonUtil.transferToJson(response);
        return response;
    }

}
