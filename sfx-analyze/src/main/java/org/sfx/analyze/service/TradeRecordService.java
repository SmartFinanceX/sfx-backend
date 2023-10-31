package org.sfx.analyze.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.val;
import org.sfx.analyze.dao.TradeRecordMapper;
import org.sfx.analyze.domain.TradeList;
import org.sfx.analyze.domain.TradeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeRecordService {
    @Autowired
    private TradeRecordMapper recordMapper;

    public boolean addOneRecord(TradeRecord r) {
        r.setTotalPrice(r.getNum() * r.getPrice());
        if (recordMapper.insert(r) == 1) {
            return true;
        }
        return false;
    }

    /**
     * 删除一条指定的记录
     *
     * @param id
     * @return
     */
    public boolean deleteOneRecord(long id) {
        if (recordMapper.deleteById(id) == 1) {
            return true;
        }
        return false;
    }

    /**
     * 查询指定用户的交易记录
     *
     * @param userId 用户的ID
     * @return 消费列表
     */
    public TradeList queryByUserId(long userId) {
        TradeList result = new TradeList();
        val wrapper = new LambdaQueryWrapper<TradeRecord>();
        wrapper.eq(TradeRecord::getUserId, userId);
        val tradeRecords = recordMapper.selectList(wrapper);
        for (TradeRecord tradeRecord : tradeRecords) {
            result.AddTradeRecord(tradeRecord);
        }
        return result;
    }

    public TradeList queryByUserIdAndTicker(Long userid, String ticker) {
        TradeList result = new TradeList();
        val wrapper = new LambdaQueryWrapper<TradeRecord>();
        wrapper.eq(TradeRecord::getUserId, userid).eq(TradeRecord::getTicker, ticker);
        val tradeRecords = recordMapper.selectList(wrapper);
        for (TradeRecord tradeRecord : tradeRecords) {
            result.AddTradeRecord(tradeRecord);
        }
        return result;
    }
}
