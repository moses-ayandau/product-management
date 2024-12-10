package com.moses.code.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MongoDBImageRepository extends MongoRepository<Image, String> {
}
