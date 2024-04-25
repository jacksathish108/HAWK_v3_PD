package hawk.bizservices;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import hawk.configrator.dtos.QuestionDTO;

public class ExcelUtility {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "ID", "questionInfodent Name", "Email", "Mobile No." };
	static String SHEET = "Questions.V1";

	public static boolean hasExcelFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}

	public static List<QuestionDTO> excelToquestionInfoList(InputStream is) {
		try {
			Workbook workbook = new XSSFWorkbook(is);
			Sheet sheet = workbook.getSheet(SHEET);

			List<QuestionDTO> questionInfoList = new ArrayList<QuestionDTO>();

			for (int i = 1; i < sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				QuestionDTO questionInfo = new QuestionDTO();

				for (int j = 0; j < row.getLastCellNum(); j++) {
					Cell cell = row.getCell(j);
					if (cell != null) {
						int cellIndex = cell.getColumnIndex();

						// Print the cell index
						//System.out.println("Cell index: " + cellIndex + "::" + cell.toString());

						switch (cellIndex) {
						case 0:
							questionInfo.setIndex((int) (cell.getNumericCellValue()));
							break;

						case 1:
							questionInfo.setElementType(cell.toString());
							break;
						case 2:
							questionInfo.setUnique((int) (cell.getNumericCellValue()));
							break;
						case 3:
							questionInfo.setDataType(cell.toString());
							break;
						case 4:
							System.out.println(cell.toString());
							break;
						case 5:
							questionInfo.setName(cell.toString());
							break;
						case 6:
							questionInfo.setDescription(cell.toString());
							break;
						case 7:
							questionInfo.setStatus((int) (cell.getNumericCellValue()));
							break;
						case 8:
							questionInfo.setRequired((int) (cell.getNumericCellValue()));
							break;
						case 9:
							questionInfo.setStyle(cell.toString());
							break;
						case 10:
							questionInfo.setCssClass(cell.toString());
							break;
						case 11:
							questionInfo.setOptions(cell.toString());
							break;
						case 12:
							questionInfo.setOnClick(cell.toString());
							break;
						case 13:
							questionInfo.setOnChange(cell.toString());
							break;
						case 14:
							questionInfo.setAttributes(cell.toString());
							break;
						default:
							break;
						}

					}

				}
				questionInfoList.add(questionInfo);
			}

			return questionInfoList;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}
}