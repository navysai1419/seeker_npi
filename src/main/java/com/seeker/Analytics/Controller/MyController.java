package com.seeker.Analytics.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MyController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/upload-csv")
    public String uploadCSV(@RequestParam("file") MultipartFile file, @RequestParam("collectionName") String collectionName) throws IOException {
        try (CSVParser csvParser = new CSVParser(new InputStreamReader(file.getInputStream()), CSVFormat.DEFAULT.withHeader())) {
            List<Map<String, String>> csvData = new ArrayList<>();

            for (CSVRecord record : csvParser) {
                csvData.add(record.toMap());
            }

            // Create the collection dynamically based on the selected name
            mongoTemplate.createCollection(collectionName);

            // Insert data into the dynamically created collection
            for (Map<String, String> recordMap : csvData) {
                mongoTemplate.insert(recordMap, collectionName);
            }
        }

        return "CSV data uploaded and inserted into MongoDB collection: " + collectionName;
    }
    @DeleteMapping("/delete-collection/{collectionName}")
    public String deleteCollection(
            @PathVariable String collectionName
    ) {
        try {
            // Drop the specified collection
            mongoTemplate.getDb().getCollection(collectionName).drop();

            // Optionally, you can return a success message
            return "Collection " + collectionName + " deleted successfully!";
        } catch (Exception e) {
            // Handle errors, e.g., collection not found
            return "Error deleting collection: " + e.getMessage();
        }
    }
//    class DocumentClassCreator {
//        public static void createDynamicDocument(Map<String, String> headers) {
//            StringBuilder classDefinition = new StringBuilder();
//            classDefinition.append("@Document(collection = \"mycollection\")\n");
//            classDefinition.append("public class DynamicDocument {\n");
//
//            for (Map.Entry<String, String> header : headers.entrySet()) {
//                String fieldName = header.getKey();
//                String fieldType = "String"; // You can modify this based on your needs
//
//                classDefinition.append("    @Field(\"").append(fieldName).append("\")\n");
//                classDefinition.append("    private ").append(fieldType).append(" ").append(fieldName).append(";\n");
//                classDefinition.append("\n");
//            }
//
//            classDefinition.append("    // Getters and setters\n");
//            classDefinition.append("}");
//
//            // Compile and load the dynamic class here (e.g., using a dynamic class loader)
//
//            // You can use reflection to create instances of the dynamic class and insert data
//        }
//    }
}

