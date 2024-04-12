package com.example.Fipe.repository;

import com.example.Fipe.model.FipeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FipeRepository extends MongoRepository<FipeEntity, String> {
}
