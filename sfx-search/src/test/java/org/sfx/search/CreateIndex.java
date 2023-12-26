package org.sfx.search;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class CreateIndex {
    @Autowired
    RestHighLevelClient client;

    @Test
    public void createIndex() {
        String indexName = "inc";
        try {
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            request.settings(Settings.builder()
                    .put("index.number_of_shards", 1)
                    .put("index.number_of_replicas", 1)
            );

            XContentBuilder mappings = XContentFactory.jsonBuilder()
                    .startObject()
                    .startObject("properties")
                    .startObject("ticker")
                    .field("type", "keyword")
                    .field("copy_to", "all")
                    .endObject()
                    .startObject("stockName")
                    .field("type", "text")
                    .field("analyzer", "ik_max_word")
                    .field("copy_to", "all")
                    .endObject()
                    .startObject("fullName")
                    .field("type", "text")
                    .field("analyzer", "ik_max_word")
                    .field("copy_to", "all")
                    .endObject()
                    .startObject("description")
                    .field("type", "text")
                    .field("analyzer", "ik_max_word")
                    .field("copy_to", "all")
                    .endObject()
                    .startObject("listDate")
                    .field("type", "date")
                    .endObject()
                    .startObject("industryClass")
                    .field("type", "keyword")
                    .endObject()
                    .startObject("all")
                    .field("type", "text")
                    .field("analyzer", "ik_max_word")
                    .endObject()
                    .endObject()
                    .endObject();

            request.mapping("_doc", mappings);

            CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
            boolean acknowledged = response.isAcknowledged();
            boolean shardsAcknowledged = response.isShardsAcknowledged();
            System.out.println("Index creation acknowledged: " + acknowledged);
            System.out.println("Shards acknowledged: " + shardsAcknowledged);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


