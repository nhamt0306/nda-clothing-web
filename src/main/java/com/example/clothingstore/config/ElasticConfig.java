package com.example.clothingstore.config;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticConfig {

    @Value("${spring.elasticsearch.uris}")
    String elasticHost;

    @Bean
    public RestHighLevelClient client() {
        return new RestHighLevelClient(RestClient.builder(HttpHost.create(elasticHost)));
    }
}