//package com.seeker.Analytics.Controller;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/api/upload")
//public class DataController {
//    @Autowired
//    private CsvUploadService csvUploadService;
//
//    private static final Logger logger = LoggerFactory.getLogger(DataController.class);
//
//    @PostMapping("/process-csv")
//    public ResponseEntity<String> processCsv(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body("Please upload a CSV file.");
//        }
//
//        try {
//            csvUploadService.uploadCsvAndGenerateOutput(file);
//            return ResponseEntity.ok("CSV file uploaded and processed successfully.");
//        } catch (IOException e) {
//            logger.error("Error processing the uploaded CSV file.", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the CSV file.");
//        }
//    }
//    @PostMapping("/generate")
//    public void generateCsv(@RequestParam("file") MultipartFile file) throws IOException {
//        csvUploadService.uploadCsvAndGenerateOutput(file);
//    }
//}
//
