package org.sfx.analyze.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.val;
import org.sfx.analyze.dao.WatchListMapper;
import org.sfx.analyze.domain.WatchList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class WatchListService {

    @Autowired
    WatchListMapper watchListMapper;

    List<String> getWatchList(Long userId) {
        LambdaQueryWrapper<WatchList> watchListMapperLambdaQueryWrapper = new LambdaQueryWrapper<>();
        watchListMapperLambdaQueryWrapper.eq(WatchList::getUserId,userId);
        List<WatchList> watchLists = watchListMapper.selectList(watchListMapperLambdaQueryWrapper);
        val watchTickers = new ArrayList<String>();
        for (WatchList watchList : watchLists) {
            watchTickers.add(watchList.getWatchTicker());
        }
        return watchTickers;
    }

    /**
     * @param userId
     * @param ticker
     * @return
     */
    boolean addOneTicker(Long userId,String ticker) {
        LambdaQueryWrapper<WatchList> watchListMapperLambdaQueryWrapper = new LambdaQueryWrapper<>();
        watchListMapperLambdaQueryWrapper.eq(WatchList::getUserId,userId).eq(WatchList::getWatchTicker,ticker);
        // TODO(AntiO2) rpc调用，检查是否存在用户和股票代码
        val watchList = watchListMapper.selectOne(watchListMapperLambdaQueryWrapper);
        if(Objects.isNull(watchList)) {
            watchListMapper.insert(new WatchList(ticker,userId));
            return true;
        }
        return false; // 添加失败
    }
    boolean deleteWatchTicker(Long userId,String ticker) {
        LambdaQueryWrapper<WatchList> watchListMapperLambdaQueryWrapper = new LambdaQueryWrapper<>();
        watchListMapperLambdaQueryWrapper.eq(WatchList::getUserId,userId).eq(WatchList::getWatchTicker,ticker);
        int delete = watchListMapper.delete(watchListMapperLambdaQueryWrapper);
        return delete>0;
    }
}
