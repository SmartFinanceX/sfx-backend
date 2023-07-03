package org.sfx.search.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.sfx.api.clients.IncInfoClient;
import org.sfx.api.config.SfxResponseCode;
import org.sfx.api.domain.ResponseResult;
import org.sfx.search.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@Service
@Slf4j
public class IndexServiceImpl implements IndexService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    IncInfoClient incInfoClient;
    @Override
    public Integer flushAllBasicInfo() {
        ResponseResult allInc = incInfoClient.getAllInc();

        if(allInc.getCode()== SfxResponseCode.OK) {
            Integer cnt = Integer.valueOf( allInc.getMsg());
            BulkRequest bulkRequest = new BulkRequest();
            // TODO(AntiO2) 检查此处警告
            for(LinkedHashMap<String,Object> incBasicInfo:(ArrayList<LinkedHashMap<String,Object>>)allInc.getData()) {
                String sourceJson = JSON.toJSONString(incBasicInfo);// TODO(AntiO2) 加快此处
                try {
                    restHighLevelClient.index(new IndexRequest("inc").id((String) incBasicInfo.get("ticker")).source(sourceJson, XContentType.JSON),
                            RequestOptions.DEFAULT);
                    log.debug("add " + incBasicInfo.get("ticker"));
                } catch (IOException e) {
                    log.error(e.getMessage());
                    return -1;
                }
            }
            return cnt;
        }
        else {
            return -2;
        }
    }
}
