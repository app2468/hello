package cn.itcast.core.util;

import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import cn.itcast.nsfw.user.entity.User;
/**
 * 导出用户列表到excel
 * @author 隔壁老刘、
 * userlist 用户列表
 * outputStream 输出流
 */
public class ExcelUtil {
	public static  void exportExcel(List<User> userList,ServletOutputStream outputStream) {
		try {
			// 1、创建工作簿
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 1.1、创建合并单元格对象
			CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 4);
			// 1.2、创建头标题行样式并创建字体 居中
			HSSFCellStyle style1 = createCellStyle(workbook, (short) 16);
			// 1.3、创列标题样式
			HSSFCellStyle style2 = createCellStyle(workbook, (short) 13);
			// 2、创建工作表
			HSSFSheet sheet = workbook.createSheet();
			// 2.1、加载合并单元格对象
			sheet.addMergedRegion(cellRangeAddress);
			// 2.2设置默认列宽
			sheet.setDefaultColumnWidth(25);
			// 3、创建行
			// 3.1、创建头标题行并写入头标题
			HSSFRow row1 = sheet.createRow(0);
			HSSFCell cell1 = row1.createCell(0);
			cell1.setCellStyle(style1);
			cell1.setCellValue("用户列表");
			// 3.2、创建列标题并写入列标题
			HSSFRow row2 = sheet.createRow(1);
			String[] titles = { "用户名", "账号", "所属部门", "性别", "邮箱" };
			for (int i = 0; i < titles.length; i++) {
				HSSFCell cell2 = row2.createCell(i);
				cell2.setCellStyle(style2);
				cell2.setCellValue(titles[i]);
			}
			// 4、创建单元格，写入用户数据到excel
			if (userList != null && userList.size() > 0) {
				for (int j = 0; j < userList.size(); j++) {
					HSSFRow row = sheet.createRow(j + 2);
					row.createCell(0).setCellValue(userList.get(j).getName());
					row.createCell(1).setCellValue(userList.get(j).getAccount());
					row.createCell(2).setCellValue(userList.get(j).getDept());
					row.createCell(3).setCellValue(userList.get(j).isGender() ? "男" : "女");
					row.createCell(4).setCellValue(userList.get(j).getEmail());
				}
			}
			// 5、输出
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 抽取公用的创建单元格式
	public static HSSFCellStyle createCellStyle(HSSFWorkbook workbook,short fontsize) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 创建字体
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		font.setFontHeightInPoints(fontsize);
		// 样式中加载字体
		style.setFont(font);
		return style;
	}

}
