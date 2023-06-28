package org.sfx.analyze.controller;

import org.sfx.analyze.service.WatchListService;
import org.sfx.api.config.SfxResponseCode;
import org.sfx.api.domain.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/watchlist")
public class WatchListController {

    @Autowired
    WatchListService watchListService;

    @GetMapping("/{userid}")
    ResponseResult getUserWatchList(Long userid) {
        List<String> watchList = watchListService.getWatchList(userid);
        return new ResponseResult(SfxResponseCode.OK,"Find WatchList",watchList);
    }

    @PostMapping("/{userid}/{tickerid}")
    ResponseResult addUserWatchList(Long userid,String ticker) {
        boolean result = watchListService.addOneTicker(userid, ticker);
        return new ResponseResult(SfxResponseCode.OK,String.valueOf(result));
    }

    @PostMapping("/{userid}/{tickerid}")
    ResponseResult delUserWatchList(Long userid,String ticker) {
        boolean result = watchListService.deleteWatchTicker(userid, ticker);
        return new ResponseResult(SfxResponseCode.OK,String.valueOf(result));
    }
}
