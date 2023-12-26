package org.sfx.analyze.dao;

import org.junit.jupiter.api.Test;
import org.sfx.analyze.domain.TradeRecord;
import org.sfx.analyze.service.TradeRecordService;
import org.sfx.api.domain.TradeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


@SpringBootTest
public class TradeRecordTest {
    @Autowired
    TradeRecordMapper mapper;
    @Autowired
    TradeRecordService service;

    @Test
    public void selectAll() {
        System.out.println(mapper.selectList(null));
    }

    @Test
    public void insertRecord() {
        TradeRecord tr = new TradeRecord();
        Date t = new Date();

        tr.setTicker("000001");

        // 将时间格式转换成符合 Timestamp要求的格式
        tr.setTp(TradeType.buy);
        tr.setNum(1);
        tr.setFee((float) 2.23);
        tr.setTime(t);
        tr.setPrice(5);
        tr.setTotalPrice(1 * 5f);
        tr.setUserId(11L);
        mapper.insert(tr);
    }

    @Test
    public void queryUser() {
        System.out.println(service.queryByUserId(1L));
    }
}
