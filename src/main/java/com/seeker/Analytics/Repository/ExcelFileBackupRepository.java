package com.seeker.Analytics.Repository;

import com.seeker.Analytics.POJO.ExcelFileBackup;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExcelFileBackupRepository extends MongoRepository<ExcelFileBackup, String> {
    // Your repository methods
}

