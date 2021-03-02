package excel;

import java.io.File;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.Statement;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelRead {

    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/test";

    static final String USER = "root";
    static final String PASS = "root";

    public static void main(String[] args)throws Exception {
        String path = "d:\\内推需求-.xlsx";
        excelToDb(path);
    }

    public static void excelToDb(String filePath) throws Exception{
        //1.构造方法
        Workbook wb = new XSSFWorkbook(new File(filePath));
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

    private static Connection getConnection(){
        Connection connection = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    private static void executeSql(Connection connection ,String sql){
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            // 展开结果集数据库
//            while(rs.next()){
//                 //通过字段检索
//                int id  = rs.getInt("id");
//                System.out.println("id=" + id);
//                String name = rs.getString("name");
//            }
            rs.close();
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void closeConnection(Connection connection){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
