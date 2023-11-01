package org.sfx.analyze.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author AntiO2
 * @description 一条交易记录。
 */
@Data
@AllArgsConstructor
@TableName(value = "counter")
public class TradeRecord extends TradeRequest {
    /**
     * 交易编号，自增
     */
    @TableId
    long id;

    /**
     * 该笔交易的成交金额
     */
    float totalPrice;

    public TradeRecord(TradeRequest request) {
        super(request);
        totalPrice = request.getPrice() * request.num;
    }

    public TradeRecord() {
        super();

    }
}
