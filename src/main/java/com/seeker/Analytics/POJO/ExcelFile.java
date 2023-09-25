package com.seeker.Analytics.POJO;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Document(collection = "excelFiles")
public class ExcelFile {
    @Id
    private String id;

    private String fileName;

    private String sheetType;

    private byte[] data;


    // Getters and setters

    public void setId(String id) {
        this.id = id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setSheetType(String sheetType) {
        this.sheetType = sheetType;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
