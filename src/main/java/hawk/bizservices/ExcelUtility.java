//package hawk.bizservices;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.web.multipart.MultipartFile;
//
//import hawk.configrator.dtos.QuestionDTO;
//
//public class ExcelUtility {
//    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
//      static String[] HEADERs = { "ID", "Student Name", "Email", "Mobile No." };
//      static String SHEET = "student";
//      public static boolean hasExcelFormat(MultipartFile file) {
//        if (!TYPE.equals(file.getContentType())) {
//          return false;
//        }
//        return true;
//      }
//      public static List<QuestionDTO> excelToStuList(InputStream is) {
//        try {
//          Workbook workbook = new XSSFWorkbook(is);
//          Sheet sheet = workbook.getSheet(SHEET);
//          Iterator<Row> rows = sheet.iterator();
//          List<QuestionDTO> stuList = new ArrayList<QuestionDTO>();
//          int rowNumber = 0;
//          while (rows.hasNext()) {
//            Row currentRow = rows.next();
//            // skip header
//            if (rowNumber == 0) {
//              rowNumber++;
//              continue;
//            }
//            Iterator<Cell> cellsInRow = currentRow.iterator();
//            QuestionDTO stu = new QuestionDTO();
//            int cellIdx = 0;
//            while (cellsInRow.hasNext()) {
//              Cell currentCell = cellsInRow.next();
//              switch (cellIdx) {
//              case 0:
//                  stu.setInde((int) currentCell.getNumericCellValue());
//                break;
//              case 1:
//                  stu.setStudentName(currentCell.getStringCellValue());
//                break;
//              case 2:
//                  stu.setEmail(currentCell.getStringCellValue());
//                break;
//              case 3:
//                stu.setMobileNo(currentCell.getStringCellValue());
//                break;
//              default:
//                break;
//              }
//              cellIdx++;
//            }
//            stuList.add(stu);
//          }
//          workbook.close();
//          return stuList;
//        } catch (IOException e) {
//          throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
//        }
//      }
//}