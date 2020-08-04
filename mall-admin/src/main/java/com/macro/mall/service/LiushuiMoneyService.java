package com.macro.mall.service;

import com.macro.mall.dto.OmsMoneyInfoParam;
import com.macro.mall.model.LiushuiMoney;

import java.util.List;

public interface LiushuiMoneyService {

    List<LiushuiMoney> selectLiushui(Integer userid,Integer pageSize, Integer pageNum);

    //新增充值流水的单号
    int insertLiushuiCZ(LiushuiMoney liushuiMoney);

    //新增提现流水的单号
    int insertLiushuiTX(LiushuiMoney liushuiMoney);

    //修改充值流水的单号
    int updateLiushuiCZ(Integer id);

    //修改提现流水的单号
    int updateLiushuiTX(Integer id);

}
