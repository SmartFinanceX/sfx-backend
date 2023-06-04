package org.sfx.analyze.service;

import com.mysql.cj.log.Log;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.sfx.analyze.domain.WatchList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class WLTest {
    @Autowired
    WatchListService watchListService;
    @Test
    public void SelectTest() {
        val watchList = watchListService.getWatchList(0L);
        log.info(watchList.toString());
    }
    @Test public void AddTicker() {
        for(int i=1;i<=100;i++) {
            String ticker="0000";
            ticker = ticker.concat(Integer.toString(i));
            watchListService.addOneTicker(0L,ticker);
        }
        val watchList = watchListService.getWatchList(0L);
        log.info(watchList.toString());
    }
    @Test public void DeleteTicker() {
        List<String> watchList = watchListService.getWatchList(0L);
        log.info(watchList.toString());
        log.info(Boolean.toString(watchListService.deleteWatchTicker(0L,"00001")) );
        log.info(Boolean.toString(watchListService.deleteWatchTicker(0L,"00000")) );
        watchList = watchListService.getWatchList(0L);
        log.info(watchList.toString());
    }
}
