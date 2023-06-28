package org.sfx.analyze.controller;

import org.sfx.analyze.service.WatchListService;
import org.sfx.api.config.SfxResponseCode;
import org.sfx.api.domain.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/watchlist")
public class WatchListController {

    @Autowired
    WatchListService watchListService;

    @GetMapping("/{userid}")
    ResponseResult getUserWatchList(@PathVariable Long userid) {
        List<String> watchList = watchListService.getWatchList(userid);
        return new ResponseResult(SfxResponseCode.OK,"Find WatchList",watchList);
    }

    @PostMapping("/{userid}/{ticker}")
    ResponseResult addUserWatchList(@PathVariable Long userid,@PathVariable String ticker) {
        boolean result = watchListService.addOneTicker(userid, ticker);
        return new ResponseResult(SfxResponseCode.OK,String.valueOf(result));
    }

    @DeleteMapping("/{userid}/{ticker}")
    ResponseResult delUserWatchList(@PathVariable Long userid,@PathVariable String ticker) {
        boolean result = watchListService.deleteWatchTicker(userid, ticker);
        return new ResponseResult(SfxResponseCode.OK,String.valueOf(result));
    }
}
