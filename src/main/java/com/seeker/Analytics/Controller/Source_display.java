package com.seeker.Analytics.Controller;

import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.data.mongodb.core.query.Query;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

    @RestController
    @RequestMapping("/api")
    public class Source_display {



        private final MongoTemplate mongoTemplate;

        @Autowired
        public Source_display(MongoTemplate mongoTemplate) {
            this.mongoTemplate = mongoTemplate;
        }

        @GetMapping("/retrieve-csv")
        public ResponseEntity<List<DBObject>> retrieveCSV(@RequestParam("collectionName") String collectionName) {
            List<DBObject> csvData = mongoTemplate.findAll(DBObject.class, collectionName);
            return ResponseEntity.ok(csvData);
        }
}
