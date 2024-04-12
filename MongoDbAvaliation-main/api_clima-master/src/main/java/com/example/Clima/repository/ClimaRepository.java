package com.example.Clima.repository;

import com.example.Clima.model.ClimaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClimaRepository extends MongoRepository<ClimaEntity, String> {

}
