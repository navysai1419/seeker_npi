//package com.seeker.Analytics.service;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.stereotype.Service;
//
//import javax.swing.text.Document;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//
//
//
//
//@Service
//public class CsvUploadService {
//
//
//    private final MongoTemplate mongoTemplate;
//
//    @Autowired
//    public CsvUploadService(MongoTemplate mongoTemplate) {
//        this.mongoTemplate = mongoTemplate;
//    }
//
//    public void uploadCsvAndGenerateOutput(MultipartFile file) throws IOException {
//        // Process the uploaded CSV file and collect data
//
//       List< List<String>> processedData = processData(file);
//
//        // Generate the output CSV file with dynamic field names
//        // generateOutputCsv(processedData);
//           insertdatatocollection(processedData);
//
//
//    }
//
//    private List<List<String>> processData(MultipartFile file) throws IOException {
//        List<List<String>> data = new ArrayList<>();
//
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
//
//            String headerLine = reader.readLine(); // Read the header row
//            String[] stringArray = headerLine.split(",");
//            List<String> columnNames = Arrays.asList(stringArray);
//
////            data.add(fieldNames);
////
////            // Read and process data rows
////            String line;
////            while ((line = reader.readLine()) != null) {
////                String[] rowData = line.split(",");
////                data.add(rowData);
//
//        // List<String> columnNames = data.get(0); // First row contains column names
//
//            String line;
//            List<Document> documents;
//            while ((line = reader.readLine()) != null) {
//                String[] rowData = line.split(",");
//                data.add(rowData);
//
//
//        List<Document> documents = data.stream()
//                .skip(1) // Skip the header row
//                .map(row -> createDocument(row, columnNames))
//                .collect(Collectors.toList());
//
//        mongoTemplate.insert(documents, "Seeker");
//
//        return data;
//    }
//
//    private void generateOutputCsv(List<String[]> data) throws IOException {
//        // Define the output CSV file path (adjust as needed)
//        String outputPath = "output.csv";
//
//        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
//            // Write the CSV header using the field names from the uploaded file
//            String headerLine = String.join(",", data.get(0));
//            writer.println(headerLine);
//
//            // Write data rows to the output CSV
//            for (int i = 1; i < data.size(); i++) {
//                String[] rowData = data.get(i);
//                String row = String.join(",", rowData);
//                writer.println(row);
//            }
//        }
//    }
//        private Document createDocument(List<String> rowData, List<String> columnNames) {
//            Document document = new Document();
//            for (int i = 0; i < columnNames.size(); i++) {
//                String columnName = columnNames.get(i);
//                String cellData = rowData.get(i);
//                document.put(columnName, cellData);
//            }
//            return document;
//        }
//}
//
