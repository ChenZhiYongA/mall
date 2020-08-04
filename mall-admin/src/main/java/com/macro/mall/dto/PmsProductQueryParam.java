package com.macro.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 产品查询参数
 * Created by macro on 2018/4/27.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PmsProductQueryParam {
    @ApiModelProperty("上架状态")
    private Integer publishStatus;
    @ApiModelProperty("审核状态")
    private Integer verifyStatus;
    @ApiModelProperty("商品名称模糊关键字")
    private String keyword;
    @ApiModelProperty("商品货号")
    private String productSn;
    @ApiModelProperty("商品分类编号")
    private Long productCategoryId;
    @ApiModelProperty("商品品牌编号")
    private Long brandId;

    private Integer id;

    private Integer userid;

    private String  goodsId;

    private String goodsName;

    private String goodsVideo;

    private String goodsImageUrl;

    private Timestamp addtime;

    private BigDecimal juanhou;

    private  BigDecimal zhuan;

    private  BigDecimal tuiguangfei;

    private String mallId;

    private String mallName;

    private String cltCpnQuantity;

    private String cltCpnRemainQuantity;

    private String cltCpnDiscount;

    private String categoryName;

    private String catIds;

    private Integer  state;

    private String salesTip;

    private  String goodsTitle;

    private  String goodsDesc;

    private BigDecimal commissionrate;

    private String goodsGalleryUrls;


}
