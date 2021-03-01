package excel;//ExcelRead

import java.io.File;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelRead {


    public static void main(String[] args)throws Exception {
        //1.构造方法
        Workbook wb = new XSSFWorkbook(new File("d:\\内推需求-.xlsx"));
        //2.获得sheet，  sheet脚标从0开始
        Sheet sheet = wb.getSheetAt(5);
        //3.获得范围
        int firstRow = sheet.getFirstRowNum();//默认RowNum从0开始
        int lastRow = sheet.getLastRowNum();
        System.out.println("行数范围取值=" + firstRow + "," + lastRow);
        for(int x = firstRow; x<=lastRow; x++){
            //4.获得某一行
            Row row = sheet.getRow(x);
            if(row == null){
                continue;
            }
            int firstCell = row.getFirstCellNum();//默认CellNum从0开始
            int lastCell = row.getLastCellNum();
            for(int y=firstCell; y<lastCell; y++){
                //6.得到单元格
                Cell cell = row.getCell(y);
                String preInfo = "x=" + x + ",y=" + y + ", CellType=" + cell.getCellType() + ",body=";
                if(cell.getCellType()== CellType.NUMERIC){
                    System.out.print(preInfo + cell.getNumericCellValue());
                    System.out.println();
                }else if(cell.getCellType()== CellType.BLANK){
                    System.out.println(preInfo);
                }else if(cell.getCellType()== CellType._NONE){
                    System.out.println(preInfo);
                }else {
                    System.out.println(preInfo + cell.getStringCellValue());
                }
            }
        }
        wb.close();
    }

    public void excelToDb(String filePath,String dbIp,String dbName){

    }
}
