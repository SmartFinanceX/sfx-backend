package org.sfx.analyze.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeRequest {
    /**
     * 手续费
     */
    float fee;
    /**
     * 股票代码
     */
    String ticker;
    /**
     * 成交单价
     */
    float price;
    /**
     * 交易类型
     * Sell 或 Buy
     */
    @TableField("trade_type")
    int tp;
    /**
     * 成交时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date time;
    /**
     * 交易单位量
     */
    int num;
    /**
     * 用户编号
     */
    Long userId;

    TradeRequest(TradeRequest r) {
        this.fee = r.getFee();
        this.ticker = r.getTicker();
        this.price = r.getPrice();
        this.tp = r.getTp();
        this.time = r.getTime();
        this.num = r.getNum();
        this.userId = r.getUserId();
    }

}
