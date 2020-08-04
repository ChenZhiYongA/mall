package com.macro.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.macro.mall.mapper.UserMapper;
import com.macro.mall.model.Users;
import com.macro.mall.service.UserService;
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkGoodsPidGenerateRequest;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsPidGenerateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Users findUsersByPid(String pid) {
        return userMapper.selcetByPid(pid);
    }


    @Override
    public int updateyongjin(String pid, BigDecimal totalAmount) {
        return userMapper.updateByPid(pid, totalAmount);
    }


    @Override
    @Transactional
    public Users findUsersByIid(Integer id) {
        return userMapper.selcetById(id);
    }

    @Override
    public Users selcetByOpenId(String openId) {
        return userMapper.selcetByOpenId(openId);
    }

    @Override
    public Integer userRegister(Users users) throws Exception {

        String clientId = "16f0f35165da41ed90040eac240fb8b4";
        String clientSecret = "e6b597eb90ad538b1bf96fee3294fa23c3be45d1";
        PopClient client = new PopHttpClient(clientId, clientSecret);

        PddDdkGoodsPidGenerateRequest request = new PddDdkGoodsPidGenerateRequest();
        request.setNumber(0L);
        List<String> pIdNameList = new ArrayList<>();
        pIdNameList.add("推手" + UUID.randomUUID().toString().replace("-", ""));
        request.setPIdNameList(pIdNameList);
        request.setNumber(Long.parseLong("1"));
        PddDdkGoodsPidGenerateResponse response = client.syncInvoke(request);
        //第一种方式
        users.setPId(response.getPIdGenerateResponse().getPIdList().get(0).getPId());
        return userMapper.userRegister(users);
    }
}
