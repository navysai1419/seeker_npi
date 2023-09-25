package com.seeker.Analytics.Controller;

import com.seeker.Analytics.POJO.ExcelFile;
import com.seeker.Analytics.Repository.ExcelFileRepository;
import com.seeker.Analytics.service.ExcelFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ExcelController {

    @Autowired


    private ExcelFileService excelFileService; // Inject the service

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcelFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("sheetType") String sheetType) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file.");
        }

        ExcelFile excelFile = new ExcelFile();
        excelFile.setFileName(file.getOriginalFilename());
        excelFile.setSheetType(sheetType);
        excelFile.setData(file.getBytes());

        excelFileService.save(excelFile);

        return ResponseEntity.ok("File uploaded successfully.");
    }

    @PostMapping ("/all")
    public ResponseEntity<List<ExcelFile>> getAllExcelSheets() {
        List<ExcelFile> excelSheets = excelFileService.findAll();

        if (excelSheets.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(excelSheets);
    }

    @DeleteMapping("/delete/{sheetType}")
    public ResponseEntity<String> deleteExcelSheetBySheetType(@PathVariable String sheetType) {
        try {
            // Find the Excel sheet(s) by sheetType
            List<ExcelFile> excelSheets = excelFileService.findBySheetType(sheetType);

            // Check if any sheets were found
            if (excelSheets.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Delete each Excel sheet
            for (ExcelFile excelSheet : excelSheets) {
                excelFileService.deleteBySheetType(excelSheet.getId());
            }

            return ResponseEntity.ok("Excel sheet(s) with sheetType " + sheetType + " deleted successfully.");
        } catch (Exception e) {
            // Handle any exceptions that may occur during deletion
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting Excel sheet(s).");
        }
    }

    @PostMapping("/backup/{fileId}")
    public ResponseEntity<String> backupExcelFile(@PathVariable String fileId) {
        // Call the service to perform the backup
        excelFileService.backupExcelFile(fileId);
        return ResponseEntity.ok("Excel file backed up successfully.");
    }




    // Add other controller methods for downloading, deleting, or managing Excel files
}

