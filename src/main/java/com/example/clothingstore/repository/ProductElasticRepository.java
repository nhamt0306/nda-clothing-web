package com.example.clothingstore.repository;

import com.example.clothingstore.model.ElasticProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductElasticRepository extends ElasticsearchRepository<ElasticProduct, Long> {
    
}
