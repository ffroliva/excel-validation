package io.ffroliva.validation.excel.util;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.IOException;

public class TestUtils {

    public static Sheet getSheet(String workbookClassPath, String sheetName) throws IOException {
        Resource resource = new ClassPathResource(workbookClassPath);
        FileInputStream fis = new FileInputStream(resource.getFile());
        return new XSSFWorkbook(fis).getSheet(sheetName);
    }
}
