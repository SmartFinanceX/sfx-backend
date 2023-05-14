package org.sfx.search.sfxbackendsearch.org.sfx.search.service;

import org.elasticsearch.client.RestClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SearchTest {
    @Autowired
    RestClient restClient;
    @Test
    public void TestConnect() {
        System.out.println(restClient.toString());
    }
}
