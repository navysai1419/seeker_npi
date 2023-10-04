package com.seeker.Analytics.Controller;
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
public class Admin_display {

    @Autowired
    private MongoTemplate mongoTemplate;

    // You should also inject the CSVDataService if you have not done so

    @PostMapping("/upload-csv")
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file, @RequestParam("collectionName") String collectionName) {
        try (CSVParser csvParser = new CSVParser(new InputStreamReader(file.getInputStream()), CSVFormat.DEFAULT.withHeader())) {
            List<Map<String, String>> csvData = new ArrayList<>();

            for (var record : csvParser) {
                csvData.add(record.toMap());
            }

            // Create the collection dynamically based on the selected name
            mongoTemplate.createCollection(collectionName);

            // Insert data into the dynamically created collection
            mongoTemplate.insert(csvData, collectionName);

            return ResponseEntity.ok("CSV data uploaded and inserted into MongoDB collection: " + collectionName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload and insert CSV data: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete-collection/{collectionName}")
    public ResponseEntity<String> deleteCollection(@PathVariable String collectionName) {
        try {
            // Drop the specified collection
            mongoTemplate.dropCollection(collectionName);
            return ResponseEntity.ok("Collection " + collectionName + " deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting collection: " + e.getMessage());
        }
    }

    @GetMapping("/metadata")
    public ResponseEntity<?> getCSVMetadata(@RequestParam("collectionName") String collectionName) {
        try {
            List<Map> csvData = mongoTemplate.findAll(Map.class, collectionName);
            if (csvData.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Collection not found or empty.");
            }

            // Get headers from the first record
            Map<String, String> sampleData = csvData.get(0);
            List<String> headers = new ArrayList<>(sampleData.keySet());

            return ResponseEntity.ok(headers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving CSV metadata: " + e.getMessage());
        }
    }
    @PutMapping("/edit-header/{collectionName}")
    public ResponseEntity<String> changeColumnHeaders(
            @PathVariable String collectionName,
            @RequestParam("oldHeader") String oldHeader,
            @RequestParam("newHeader") String newHeader
    ) {
        try {
            // Define the update operation to change the column header
            Update update = new Update().rename(oldHeader, newHeader);

            // Perform the update for all documents in the collection
            mongoTemplate.updateMulti(new Query(), update, collectionName);

            return ResponseEntity.ok("Column header changed successfully for collection: " + collectionName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error changing column header: " + e.getMessage());
        }
    }

    @PutMapping("/add-header/{collectionName}")
    public ResponseEntity<String> addHeaderToCSV(
            @PathVariable String collectionName,
            @RequestParam("newHeader") String newHeader
    ) {
        try {
            // Retrieve data from the MongoDB collection
            List<Map> csvData = mongoTemplate.findAll(Map.class, collectionName);

            // Add the new header to each document with a default value of null
            for (Map document : csvData) {
                document.put(newHeader, null);
            }

            // Update the collection with the new header in all documents
            mongoTemplate.remove(new Query(), collectionName); // Clear the collection
            mongoTemplate.insert(csvData, collectionName); // Insert updated data

            return ResponseEntity.ok("New header added successfully to the collection: " + collectionName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding new header: " + e.getMessage());
        }
    }

    @PutMapping("/remove-header/{collectionName}")
    public ResponseEntity<String> removeHeaderFromCSV(
            @PathVariable String collectionName,
            @RequestParam("headerToRemove") String headerToRemove
    ) {
        try {
            // Retrieve data from the MongoDB collection
            List<Map> csvData = mongoTemplate.findAll(Map.class, collectionName);

            // Remove the specified header from each document
            for (Map document : csvData) {
                document.remove(headerToRemove);
            }

            // Update the collection with the modified CSV data
            mongoTemplate.remove(new Query(), collectionName); // Clear the collection
            mongoTemplate.insert(csvData, collectionName); // Insert updated data

            return ResponseEntity.ok("Header removed successfully from the collection: " + collectionName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removing header: " + e.getMessage());
        }
    }
}
