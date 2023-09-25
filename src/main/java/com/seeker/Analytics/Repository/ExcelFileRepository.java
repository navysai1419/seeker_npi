package com.seeker.Analytics.Repository;

import com.seeker.Analytics.POJO.ExcelFile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ExcelFileRepository extends MongoRepository<ExcelFile, String> {

    // Custom query method to find Excel files by sheetType
    List<ExcelFile> findBySheetType(String sheetType);


}
