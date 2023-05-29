package org.sfx.search;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestHighLevelClientBuilder;
import org.sfx.api.clients.IncInfoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients(clients = {IncInfoClient.class})
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class SearchService {

    public static void main(String[] args) {
        SpringApplication.run(SearchService.class, args);
    }
    @Bean
    public RestHighLevelClient restHighLevelClient(){
        return new RestHighLevelClientBuilder(RestClient.builder(
                HttpHost.create("http://150.158.161.165:9200")).build()  // TODO(Antio2) change url
        ).setApiCompatibilityMode(true).build();
    }
}
