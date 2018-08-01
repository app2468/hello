package cn.itcast.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class TestPOI2Excel {
	// --------------poi????excel
	@Test
	public void write03Excel() throws Exception {
		// 1????????????
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 2????????????
		HSSFSheet sheet = workbook.createSheet("hello worlk");
		// 3????????,??3??
		HSSFRow row = sheet.createRow(2);
		// 4???????????,??3???3??
		HSSFCell cell = row.createCell(2);
		cell.setCellValue("Hello World!");

		String fileName = "D:\\itcast\\????.xls";
		FileOutputStream fileOutputStream = new FileOutputStream(fileName);
		workbook.write(fileOutputStream);
		workbook.close();
		fileOutputStream.close();

	}

	@Test
	public void read03Excel() throws Exception {
		String fileName = "D:\\itcast\\????.xls";
		FileInputStream fileInputStream = new FileInputStream(fileName);
		// 1???????????
		HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
		// 2???????????
		HSSFSheet sheet = workbook.getSheetAt(0);
		// 3???????,??3??
		HSSFRow row = sheet.getRow(2);
		// 4??????????,??3???3??
		HSSFCell cell = row.getCell(2);
		System.out.println("??3???3?????" + cell.getStringCellValue());

		workbook.close();
		fileInputStream.close();

	}

	@Test
	public void testExcelStyle() throws Exception {
		// 1????????????
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 1.1?????????????????????3???3?????5??
		CellRangeAddress cellRangeAddress = new CellRangeAddress(2, 2, 2, 4);// ?????????????????????????????
		// 1.2???????????
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// ??
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// ???
		// 1.3????????
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// ??????
		font.setFontHeightInPoints((short) 16);// ??????16

		// ???????????????
		style.setFont(font);
		// ???????
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);// ?????????
		style.setFillBackgroundColor(HSSFColor.YELLOW.index);// ????????????
		style.setFillForegroundColor(HSSFColor.GREEN.index);// ???

		// 2??????????
		HSSFSheet sheet = workbook.createSheet("hello worlk");
		// 2.1??????????????
		sheet.addMergedRegion(cellRangeAddress);
		// 3?????????3??
		HSSFRow row = sheet.createRow(2);
		// 4??????????3???3??
		HSSFCell cell = row.createCell(2);
		// 4.1?????????????
		cell.setCellStyle(style);
		cell.setCellValue("hello world");
		String fileName = "D:\\itcast\\????.xls";
		FileOutputStream fileOutputStream = new FileOutputStream(fileName);
		workbook.write(fileOutputStream);
		workbook.close();
		fileOutputStream.close();

	}

}