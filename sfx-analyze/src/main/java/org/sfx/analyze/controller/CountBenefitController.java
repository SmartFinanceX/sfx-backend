package org.sfx.analyze.controller;

import org.sfx.analyze.domain.TradeList;
import org.sfx.analyze.domain.TradeRecord;
import org.sfx.analyze.domain.TradeRequest;
import org.sfx.analyze.service.TradeRecordService;
import org.sfx.api.config.SfxResponseCode;
import org.sfx.api.domain.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benefit")
public class CountBenefitController {
    @Autowired
    TradeRecordService tradeRecordService;

    @PostMapping("/add_record")
    public ResponseResult AddOneRecord(@RequestBody TradeRequest request) {
        TradeRecord record = new TradeRecord(request);
        if (record.getNum() <= 0) {
            return new ResponseResult<>(SfxResponseCode.ILLEGAL_INPUT, "illegal input");
        }
        record.setTotalPrice(request.getPrice() * request.getNum());
        try {
            tradeRecordService.addOneRecord(record);
        } catch (Exception e) {
            return new ResponseResult<>(SfxResponseCode.MYSQL_INSERT_ERROR, "MYSQL_INSERT_ERROR");
        }
        return new ResponseResult<>(SfxResponseCode.OK, "Add OK");
    }

    @GetMapping("/query_by_user/{userid}")
    public ResponseResult<TradeList> QueryByUser(@PathVariable Long userid) {
        TradeList l = tradeRecordService.queryByUserId(userid);
        return new ResponseResult<>(SfxResponseCode.OK, "Query OK", l);
    }

    @GetMapping("/query_by_user/{userid}/{ticker}")
    public ResponseResult<TradeList> QueryByUserAndTicker(@PathVariable Long userid, @PathVariable String ticker) {
        TradeList l = tradeRecordService.queryByUserIdAndTicker(userid, ticker);
        return new ResponseResult<>(SfxResponseCode.OK, "Query OK", l);
    }
}
