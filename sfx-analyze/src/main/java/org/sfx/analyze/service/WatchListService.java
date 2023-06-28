package org.sfx.analyze.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.sfx.analyze.dao.WatchListMapper;
import org.sfx.analyze.domain.WatchList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class WatchListService {

    @Autowired
    WatchListMapper watchListMapper;

    public List<String> getWatchList(Long userId) {
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
    public boolean addOneTicker(Long userId,String ticker) {

        log.info("try add watchlist user:"+String.valueOf(userId)+" ticker:"+ticker);
        LambdaQueryWrapper<WatchList> watchListMapperLambdaQueryWrapper = new LambdaQueryWrapper<>();

        watchListMapperLambdaQueryWrapper.eq(WatchList::getUserId,userId).eq(WatchList::getWatchTicker,ticker);
        // TODO(AntiO2) rpc调用，检查是否存在用户和股票代码
        val watchList = watchListMapper.selectOne(watchListMapperLambdaQueryWrapper);
        if(Objects.isNull(watchList)) {
            try {
                watchListMapper.insert(new WatchList(ticker,userId));
            }
            catch(Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false; // 添加失败
    }
    public boolean deleteWatchTicker(Long userId,String ticker) {
        LambdaQueryWrapper<WatchList> watchListMapperLambdaQueryWrapper = new LambdaQueryWrapper<>();
        watchListMapperLambdaQueryWrapper.eq(WatchList::getUserId,userId).eq(WatchList::getWatchTicker,ticker);
        int delete = watchListMapper.delete(watchListMapperLambdaQueryWrapper);
        return delete>0;
    }
}
