package org.gallup.access.utils;

import com.codeborne.selenide.SelenideElement;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.*;
import java.util.stream.IntStream;

public class AbstractComponent {

    /**
     * Waits explicitly for a SelenideElement to become visible within the given timeout.
     * Polls every 5 seconds until the timeout is reached.
     *
     * @param element      the SelenideElement to wait for
     * @param timeoutInSec total time to wait in seconds
     */
    public static void waitForVisibility(SelenideElement element, int timeoutInSec) {
        new FluentWait<>(element)
                .withTimeout(Duration.ofSeconds(timeoutInSec))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(Exception.class)
                .until((Function<SelenideElement, Boolean>) el -> el.isDisplayed());
    }

    /**
     * Reads an Excel sheet and returns a list of maps where each map represents a row.
     * Keys are column headers, values are cell contents.
     *
     * @param filePath  path to the Excel file
     * @param sheetName name of the sheet to read
     * @return list of row maps
     */
    public static List<Map<String, String>> readSheet(String filePath, String sheetName) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new IllegalArgumentException("Sheet not found: " + sheetName);

            Row headerRow = sheet.getRow(0);
            List<String> headers = new ArrayList<>();
            headerRow.forEach(cell -> headers.add(cell.getStringCellValue()));

            List<Map<String, String>> data = new ArrayList<>();

            IntStream.range(1, sheet.getPhysicalNumberOfRows())
                    .mapToObj(sheet::getRow)
                    .filter(Objects::nonNull)
                    .forEach(row -> {
                        Map<String, String> rowData = new LinkedHashMap<>();
                        IntStream.range(0, headers.size()).forEach(i -> {
                            Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            rowData.put(headers.get(i), getCellValue(cell));
                        });
                        data.add(rowData);
                    });

            return data;

        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + filePath, e);
        }
    }

    public static void readexcelfile(String filePath){
        try {
            FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet excelSheet = workbook.getSheetAt(0);
            if (excelSheet == null) throw new IllegalArgumentException("Sheet not found: " + excelSheet);
            for(Row row:excelSheet){
                for(Cell cell:row){
                    System.out.println(getCellValue(cell) + "\t");
                }
                System.out.println();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getCellValue(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }

    public static void writeExcelfile(){
        Workbook createWorkBook = new XSSFWorkbook();
        Sheet sheet = createWorkBook.createSheet("sheet1");
        Row rowHeader = sheet.createRow(0);
        rowHeader.createCell(0).setCellValue("Name");
        Row rowValue = sheet.createRow(1);
        rowValue.createCell(1).setCellValue("Dhrithi");
        try (FileOutputStream fos = new FileOutputStream("output.xlsx")) {
            createWorkBook.write(fos);
            createWorkBook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static String readPdf(File file) {
        try (PDDocument document = PDDocument.load(file)) {
            return new PDFTextStripper().getText(document);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read PDF: " + file.getName(), e);
        }
    }

    // Returns current date in format: yyyy-MM-dd
    public static String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    // Returns current time in format: HH:mm:ss
    public static String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    // Returns timestamp in format: yyyyMMdd_HHmmss (great for filenames)
    public static String getTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    }




}
