package com.macro.mall.service.impl;

import com.macro.mall.mapper.BalanceMapper;
import com.macro.mall.mapper.LiushuiMapper;
import com.macro.mall.model.Balance;
import com.macro.mall.model.LiushuiMoney;
import com.macro.mall.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service("BalanceService")
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    private BalanceMapper balanceMapper;
    @Autowired
  private LiushuiMapper liushuiMapper;

    //用userid查询余额表的余额
    @Override
    public BigDecimal selcetyuemoney(Integer userid) {
        BigDecimal yuemoney = balanceMapper.selectyue(userid);
        return yuemoney;
    }

   //用userid查询余额表
    @Override
    public List<Balance> selectallbalance(Integer userid) {
     Balance balance = new Balance();


    return balanceMapper.selectByuserid(userid);
    }
}
