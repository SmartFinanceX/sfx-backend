package org.sfx.search.sfxbackendsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SfxBackendSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SfxBackendSearchApplication.class, args);
    }
    @Bean
    public RestHighLevelClient restHighLevelClient(){
        return new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://192.168.150.101:9200") // TODO(Antio2) change url
        ));
    }
}
