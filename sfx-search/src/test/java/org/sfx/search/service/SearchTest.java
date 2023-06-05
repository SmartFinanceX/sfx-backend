package org.sfx.search.service;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sfx.search.service.IndexService;
import org.sfx.search.service.impl.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

@SpringBootTest
public class SearchTest {
    RestHighLevelClient restClient;
    @Autowired
    SearchServiceImpl searchService;

    @Test
    void SearchByTicker() {
        searchService.searchByTicker("000001");
    }
    @Test
    void SearchByKeyWord() {
        System.out.println(searchService.searchByKeyWord("农业银行"));
    }
    @BeforeEach
    void setUp() {
        this.restClient = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://192.168.150.101:9200")));
    }
    @Test
    public void TestConnect() {}

}
