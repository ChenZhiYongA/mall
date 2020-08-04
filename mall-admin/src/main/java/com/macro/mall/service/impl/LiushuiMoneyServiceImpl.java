package com.macro.mall.service.impl;

import com.macro.mall.dto.OmsMoneyInfoParam;
import com.macro.mall.mapper.BalanceMapper;
import com.macro.mall.mapper.LiushuiMapper;
import com.macro.mall.model.Balance;
import com.macro.mall.model.LiushuiMoney;
import com.macro.mall.service.LiushuiMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service("liushuiMoneyService")
public class LiushuiMoneyServiceImpl implements LiushuiMoneyService {

    @Autowired
    private LiushuiMapper liushuiMapper;

    @Autowired
    private BalanceMapper balanceMapper;

    /**
     * 根据userid查询流水账号
     * @param userid  用户id
     * @return
     */
    @Override
    public List<LiushuiMoney> selectLiushui(Integer userid,Integer pageSize, Integer pageNum) {

        List<LiushuiMoney> liushuiMoney = liushuiMapper.selectByUserId(userid);
        return liushuiMoney;
    }

    /**
     * 生成新的充值流水单号
     * @param liushuiMoney 流水单号的信息
     * @return
     */
    @Override
    public int insertLiushuiCZ(LiushuiMoney liushuiMoney) {
        //根据充值方式生成请求单号
        liushuiMoney.setOutOrderNo("CZ"+System.currentTimeMillis());
        //设置创建时间
        liushuiMoney.setAddtime(new Timestamp(new Date().getTime()));
        //设置审核状态
        liushuiMoney.setNeedaudit(1);
        //设置状态
        liushuiMoney.setStatus(0);
        //设置流水类型
        liushuiMoney.setType(2);
        //调用方法进行新增
        int i = liushuiMapper.insertLiushui(liushuiMoney);
        return i;
    }

    /**
     * 生成新的提现流水单号
     * @param liushuiMoney 流水单号的信息
     * @return
     */
    @Override
    public int insertLiushuiTX(LiushuiMoney liushuiMoney) {
        //根据充值方式生成请求单号
        liushuiMoney.setOutOrderNo("TX"+new Date().getTime());
        //设置创建时间
        liushuiMoney.setAddtime(new Timestamp(new Date().getTime()));
        //设置审核状态
        liushuiMoney.setNeedaudit(1);
        //设置状态
        liushuiMoney.setStatus(0);
        //设置流水类型
        liushuiMoney.setType(1);
        //调用方法进行新增
        int i = liushuiMapper.insertLiushui(liushuiMoney);
        return i;
    }

    /**
     * 修改类型为充值的单号
     * @param id  流水单号id
     * @return
     */
    @Override
    @Transactional
    public int updateLiushuiCZ(Integer id) {
        //用来判断是否修改成功,默认为失败
        int i = 0;
        //根据id查询流水单号
        LiushuiMoney liushuiMoney = liushuiMapper.selectLiushuiMoneyById(id);
        //将状态变为成功
        liushuiMoney.setStatus(1);
        //修改审核的状态
        liushuiMoney.setNeedaudit(2);
        //设置说明
        liushuiMoney.setExplain("微信在线充值!");
        //进行修改
        int successLS = liushuiMapper.updateLiushui(liushuiMoney);
        //更改余额表所对应的金额
        int successYE = balanceMapper.updateyue(liushuiMoney.getUserid(), liushuiMoney.getMoney(),liushuiMoney.getType());
        if(successYE==1&&successLS==1){
            i = 1;
        }
        return i;
    }

    /**
     * 修改类型为提现的单号
     * @param id  流水单号id
     * @return
     */
    @Override
    @Transactional
    public int updateLiushuiTX(Integer id) {
        //用来判断是否修改成功,默认为失败
        int i = 0;
        //根据id查询流水单号
        LiushuiMoney liushuiMoney = liushuiMapper.selectLiushuiMoneyById(id);
        //将状态变为成功
        liushuiMoney.setStatus(1);
        //修改审核的状态
        liushuiMoney.setNeedaudit(2);
        //设置说明
        liushuiMoney.setExplain("放款成功!");
        //进行修改
        int successLS = liushuiMapper.updateLiushui(liushuiMoney);
        //更改余额表所对应的金额
        int successYE = balanceMapper.updateyue(liushuiMoney.getUserid(), liushuiMoney.getMoney(),liushuiMoney.getType());
        if(successLS==1&&successYE==1){
            i = 1;
        }
        return i;
    }
}
