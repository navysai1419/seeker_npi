package com.seeker.Analytics.POJO;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "excelFileBackups") // Collection for backup Excel files
public class ExcelFileBackup {
    @Id
    private String id;

    private String fileName;

    private String sheetType;

    private byte[] data; // Store file data as a byte array

    private Date backupTimestamp; // Timestamp for the backup

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSheetType() {
        return sheetType;
    }

    public void setSheetType(String sheetType) {
        this.sheetType = sheetType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Date getBackupTimestamp() {
        return backupTimestamp;
    }

    public void setBackupTimestamp(Date backupTimestamp) {
        this.backupTimestamp = backupTimestamp;
    }
    // Constructors
}

