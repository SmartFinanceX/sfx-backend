package org.sfx.search.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.sfx.api.config.SfxResponseCode;
import org.sfx.api.domain.IncBasicInfo;
import org.sfx.api.domain.ResponseResult;
import org.sfx.search.domain.SearchResult;
import org.sfx.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {
    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Override
    public ResponseResult searchByTicker(String ticker){
        SearchRequest query = new SearchRequest("inc");
        query.source().query(QueryBuilders.termQuery("ticker", ticker));
        try {
            SearchResponse searchResponse = restHighLevelClient.search(query, RequestOptions.DEFAULT);
            SearchResult<IncBasicInfo> handleResponse = handleResponse(searchResponse);
            if(handleResponse.getHit()==1) {
                return new ResponseResult(SfxResponseCode.OK,"Get Ticker",handleResponse.getSearchResults());
            }
            else return new ResponseResult(SfxResponseCode.INDEX_NOT_FIND,"无对应结果");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return new ResponseResult(SfxResponseCode.UNKNOWN_ERROR,"未知错误");
    }
    @Override
    public ResponseResult searchByKeyWord(String keyword){
        return  searchByKeyWord(keyword,1,10);
    }

    @Override
    public ResponseResult searchByKeyWord(String keyword, int pageNum, int pageSize) {
        SearchRequest query = new SearchRequest("inc");
        query.source().query(QueryBuilders.matchQuery("all", keyword)).from((pageNum-1)*pageSize).size(pageSize);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(query, RequestOptions.DEFAULT);
            SearchResult<IncBasicInfo> handleResponse = handleResponse(searchResponse);
            if(handleResponse.getHit()>0) {
                return new ResponseResult(SfxResponseCode.OK,handleResponse.getHit().toString(),handleResponse.getSearchResults());
            }
            else return new ResponseResult(SfxResponseCode.INDEX_NOT_FIND,"无对应结果");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return new ResponseResult(SfxResponseCode.UNKNOWN_ERROR,"未知错误");
    }

    private SearchResult<IncBasicInfo> handleResponse(SearchResponse response) {
        return handleResponse( response, false);
    }
    private SearchResult<IncBasicInfo> handleResponse(SearchResponse response,Boolean highlighted) {
        SearchResult<IncBasicInfo> incBasicInfoSearchResult = new SearchResult<IncBasicInfo>();
        ArrayList<IncBasicInfo> incBasicInfos = new ArrayList<>();
        SearchHits searchHits = response.getHits();
        // 总条数
        long total = searchHits.getTotalHits().value;
        // System.out.println("总条数：" + total);
        // 获取文档数组
        SearchHit[] hits = searchHits.getHits();
        // 4.3.遍历
        for (SearchHit hit : hits) {
            // 获取source
            String json = hit.getSourceAsString();
            // 反序列化，非高亮的
            IncBasicInfo incBasicInfo = JSON.parseObject(json, IncBasicInfo.class);
            // 处理高亮结果
            if(highlighted) {
                // 1)获取高亮map
//            Map<String, HighlightField> map = hit.getHighlightFields();
//            // 2）根据字段名，获取高亮结果
//            HighlightField highlightField = map.get("name");
//            // 3）获取高亮结果字符串数组中的第1个元素
//            String incName = highlightField.getFragments()[0].toString();
//            // 4）把高亮结果放到Inc中
//
            }

            incBasicInfos.add(incBasicInfo);
        }
        incBasicInfoSearchResult.setHit(response.getHits().getTotalHits().value);
        incBasicInfoSearchResult.setSearchResults(incBasicInfos);
        return incBasicInfoSearchResult;
    }
}
