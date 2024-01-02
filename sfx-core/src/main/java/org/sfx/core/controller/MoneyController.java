package org.sfx.core.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.val;
import org.sfx.api.config.SfxResponseCode;
import org.sfx.core.domain.Money;
import org.sfx.core.domain.ResponseResult;
import org.sfx.core.mapper.MoneyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/money")
public class MoneyController {

    @Autowired
    MoneyMapper mapper;

    @GetMapping("{ticker}")
    public ResponseResult getByTicker(@PathVariable String ticker) {
        LambdaQueryWrapper<Money> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Money::getTicker, ticker);
        val profit = mapper.selectOne(wrapper);
        if (Objects.isNull(profit)) {
            return new ResponseResult(SfxResponseCode.OK, "未找到对应股票代码", null);
        }
        return new ResponseResult(SfxResponseCode.OK, "找到对应股票", profit);
    }
}
