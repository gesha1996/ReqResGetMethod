package Utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class ExelUtils {
    private static Workbook excelBook;
    private static Sheet excelSheet;
    private static Row row;
    private static Cell cell;
    private static String filePath;
    /*
    Create the method will take two params
    @Param1 --> FileName
    @Param2 --> SheetName
    This method will open the excel file
     */
    public static void openExcelFile(String fileName, String sheetName){
        filePath="src/test/resources/com.cucumber.features.ExcelData/"+fileName+".xlsx";
        try{
            File file=new File(filePath);
            FileInputStream fileInputStream=new FileInputStream(file);
            excelBook=new XSSFWorkbook(fileInputStream);
            excelSheet=excelBook.getSheet(sheetName);
        }catch (Exception e){
            System.out.println("The file is not exist");
        }
    }
    /*
    Create the method will take two param
    @param1 --> rowNumber
    @param2 --> cellNumber
    this method will return cell value as String
     */
    public static String getCellValue(int rowNumber, int cellNumber){
        rowNumber=rowNumber-1;
        cellNumber=cellNumber-1;
        row=excelSheet.getRow(rowNumber);
        cell=row.getCell(cellNumber);
        return cell.toString();
    }
    /*
    Create the method will take three param
    @param1 --> value
    @param2 --> rowNum
    @param3 --> cellNum
    This method will set new value to your excel file
     */
    public static void setCellValue(String value, int rowNum, int celNum){
        rowNum=rowNum-1;
        celNum=celNum-1;
        row=excelSheet.getRow(rowNum);
        cell=row.getCell(celNum);
        if(cell==null){
            row=excelSheet.createRow(rowNum);
            cell=row.createCell(celNum);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(value);
        }else{
            cell.setCellValue(value);
        }
    }
    /*
    Create getAll method
    It will print all the values from excel
     */
    public static void getAllExcelValue(){
        for(int i=excelSheet.getFirstRowNum(); i<=excelSheet.getLastRowNum();i++){
            Row row=excelSheet.getRow(i);
            for(int j=row.getFirstCellNum();j<row.getLastCellNum();j++){
                Cell cell=row.getCell(j);
                System.out.print(cell+"|");
            }
            System.out.println();
        }
    }
    /*
    Create the one method it will close the workbook
     */
    public static void closeWorkbook()  {
        try {
            excelBook.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /*

    HomeWork:
    Create one method it will get all the value from excel
    Return type of this method will be List<List<String>>
     */

}
