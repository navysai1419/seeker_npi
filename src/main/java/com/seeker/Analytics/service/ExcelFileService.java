package com.seeker.Analytics.service;

import com.seeker.Analytics.POJO.ExcelFile;
import com.seeker.Analytics.POJO.ExcelFileBackup;
import com.seeker.Analytics.Repository.ExcelFileBackupRepository;
import com.seeker.Analytics.Repository.ExcelFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExcelFileService {

    @Autowired
    private ExcelFileRepository excelFileRepository;

    @Autowired
    private ExcelFileBackupRepository excelFileBackupRepository;

    public ExcelFile save(ExcelFile excelFile) {
        return excelFileRepository.save(excelFile);
    }

    public List<ExcelFile> findAll() {
        return excelFileRepository.findAll();

    }
    public List<ExcelFile> findBySheetType(String sheetType) {
        // Implement logic to find Excel sheets by sheetType
        // Assuming you have an appropriate method in the repository, for example:
        return excelFileRepository.findBySheetType(sheetType);
    }



    public void deleteBySheetType(String sheetType) {
        // Find the Excel sheets by sheetType
        List<ExcelFile> excelSheets = excelFileRepository.findBySheetType(sheetType);

        // Check if any sheets were found
        if (excelSheets.isEmpty()) {
            throw new ResourceNotFoundException("No Excel sheets found with sheetType: " + sheetType);
        }

        // Delete each Excel sheet
        for (ExcelFile excelSheet : excelSheets) {
            excelFileRepository.delete(excelSheet);
        }
    }
    // Other service methods...

    public void backupExcelFile(String fileId) {
        // Retrieve the Excel file from the source collection
        ExcelFile excelFile = excelFileRepository.findById(fileId)
                .orElseThrow(() -> new EntityNotFoundException("Excel file not found with ID: " + fileId));

        // Create a copy of the Excel file
        ExcelFileBackup backup = new ExcelFileBackup();
        backup.setFileName(excelFile.getFileName());
        backup.setSheetType(excelFile.getSheetType());
        backup.setData(excelFile.getData()); // Assuming you have a byte array field for data

        // Optionally, add metadata to the backup document (e.g., timestamp)
        backup.setBackupTimestamp(new Date());

        // Save the backup document to a backup collection or database
        excelFileBackupRepository.save(backup);
    }

}

