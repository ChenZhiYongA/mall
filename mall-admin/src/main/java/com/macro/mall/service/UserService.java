package com.macro.mall.service;

import com.macro.mall.model.Users;

import java.math.BigDecimal;

public interface UserService {

    public Users findUsersByPid(String pid);


    public int updateyongjin(String pid, BigDecimal totalAmount);


    public Users findUsersByIid(Integer id);

    public Users selcetByOpenId(String openId);


    /**
     * 用户注册
     *
     * @param users
     * @return
     */
    Integer userRegister(Users users) throws Exception;
}
