package com.macro.mall.service;


import com.macro.mall.model.Balance;

import java.math.BigDecimal;
import java.util.List;

public interface BalanceService {

    BigDecimal selcetyuemoney(Integer userid);

    List<Balance> selectallbalance(Integer userid);


}
