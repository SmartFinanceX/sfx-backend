package org.sfx.analyze.domain;

import lombok.Data;
import org.sfx.api.domain.TradeType;

import java.util.ArrayList;
import java.util.List;

@Data
public class TradeList {
    List<TradeRecord> records;
    /**
     * 当前拥有单位数
     */
    int ownNum;
    /**
     * 总成本
     */
    float totalCost;
    /**
     * 成本价
     */
    float priceCost;

    public TradeList() {
        records = new ArrayList<>();
        ownNum = 0;
        totalCost = 0;
        priceCost = 0;
    }

    public void AddTradeRecord(TradeRecord tr) {
        records.add(tr);
        if (tr.getTp() == TradeType.sell) {
            // 出售股票
            ownNum -= tr.num;
            totalCost -= tr.getTotalPrice();
        } else {
            // 购买股票
            ownNum += tr.num;
            totalCost += tr.getTotalPrice();
        }
        totalCost += tr.fee;
        if (ownNum != 0) {
            priceCost = totalCost / ownNum;
        }
    }
}
