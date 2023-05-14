package org.sfx.search.service;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.sfx.api.clients.IncInfoClient;
import org.sfx.api.config.SfxResponseCode;
import org.sfx.api.domain.IncBasicInfo;
import org.sfx.api.domain.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@SpringBootTest
public class InfoTest {
    @Autowired
    IncInfoClient incInfoClient;
    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Test
    public void testClient() {
        ResponseResult allInc = incInfoClient.getAllInc();
        if(allInc.getCode()== SfxResponseCode.OK) {
            BulkRequest bulkRequest = new BulkRequest();
            // TODO(AntiO2) 检查此处警告
            for(LinkedHashMap<String,Object> incBasicInfo:(ArrayList<LinkedHashMap<String,Object>>)allInc.getData()) {

                String sourceJson = JSON.toJSONString(incBasicInfo);// TODO(AntiO2) 加快此处
                try {
                    restHighLevelClient.index( new IndexRequest("inc").id((String) incBasicInfo.get("ticker")).source(sourceJson, XContentType.JSON),
                            RequestOptions.DEFAULT);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(incBasicInfo);
            }

        }
        else return;
    }
}
