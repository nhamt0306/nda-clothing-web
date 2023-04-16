package com.example.clothingstore.repository;

import com.example.clothingstore.model.ElasticProduct;
import com.example.clothingstore.model.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductElasticRepository extends ElasticsearchRepository<ElasticProduct, Long> {

    @Query("{\n" +
            "        \"bool\": {\n" +
            "            \"must\": [\n" +
            "                {\n" +
            "                    \"bool\": {\n" +
            "                        \"should\": [\n" +
            "                            {\n" +
            "                                \"match\": {\n" +
            "                                    \"name\": {\n" +
            "                                        \"query\": \"?0\",\n" +
            "                                        \"operator\": \"or\",\n" +
            "                                        \"zero_terms_query\": \"all\"\n" +
            "                                    }\n" +
            "                                }\n" +
            "                            }\n" +
            "                        ]\n" +
            "                    }\n" +
            "                },\n" +
            "                {\n" +
            "                    \"bool\": {\n" +
            "                        \"should\": [\n" +
            "                            {\n" +
            "                                \"bool\": {\n" +
            "                                    \"must_not\": {\n" +
            "                                        \"exists\": {\n" +
            "                                            \"field\": \"categoryId\"\n" +
            "                                        }\n" +
            "                                    }\n" +
            "                                }\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"match\": {\n" +
            "                                    \"categoryId\": {\n" +
            "                                        \"query\": \"?1\",\n" +
            "                                        \"operator\": \"or\",\n" +
            "                                        \"zero_terms_query\": \"all\"\n" +
            "                                    }\n" +
            "                                }\n" +
            "                            }\n" +
            "                        ]\n" +
            "                    }\n" +
            "                },\n" +
            "                {\n" +
            "                    \"bool\": {\n" +
            "                        \"should\": [\n" +
            "                            {\n" +
            "                                \"bool\": {\n" +
            "                                    \"must_not\": {\n" +
            "                                        \"exists\": {\n" +
            "                                            \"field\": \"avgRating\"\n" +
            "                                        }\n" +
            "                                    }\n" +
            "                                }\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"range\": {\n" +
            "                                    \"avgRating\": {\n" +
            "                                        \"gte\": \"?2\"\n" +
            "                                    }\n" +
            "                                }\n" +
            "                            }\n" +
            "                        ],\n" +
            "                        \"minimum_should_match\": 1\n" +
            "                    }\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    }")
    Page<ElasticProduct> searchElasticProductsByFiltering(String keyword, String catId, String rating, Pageable pageable);
}
