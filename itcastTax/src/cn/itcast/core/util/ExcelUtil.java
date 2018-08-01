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
 * �����û��б�excel
 * @author ����������
 * userlist �û��б�
 * outputStream �����
 */
public class ExcelUtil {
	public static  void exportExcel(List<User> userList,ServletOutputStream outputStream) {
		try {
			// 1������������
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 1.1�������ϲ���Ԫ�����
			CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 4);
			// 1.2������ͷ��������ʽ���������� ����
			HSSFCellStyle style1 = createCellStyle(workbook, (short) 16);
			// 1.3�����б�����ʽ
			HSSFCellStyle style2 = createCellStyle(workbook, (short) 13);
			// 2������������
			HSSFSheet sheet = workbook.createSheet();
			// 2.1�����غϲ���Ԫ�����
			sheet.addMergedRegion(cellRangeAddress);
			// 2.2����Ĭ���п�
			sheet.setDefaultColumnWidth(25);
			// 3��������
			// 3.1������ͷ�����в�д��ͷ����
			HSSFRow row1 = sheet.createRow(0);
			HSSFCell cell1 = row1.createCell(0);
			cell1.setCellStyle(style1);
			cell1.setCellValue("�û��б�");
			// 3.2�������б��Ⲣд���б���
			HSSFRow row2 = sheet.createRow(1);
			String[] titles = { "�û���", "�˺�", "��������", "�Ա�", "����" };
			for (int i = 0; i < titles.length; i++) {
				HSSFCell cell2 = row2.createCell(i);
				cell2.setCellStyle(style2);
				cell2.setCellValue(titles[i]);
			}
			// 4��������Ԫ��д���û����ݵ�excel
			if (userList != null && userList.size() > 0) {
				for (int j = 0; j < userList.size(); j++) {
					HSSFRow row = sheet.createRow(j + 2);
					row.createCell(0).setCellValue(userList.get(j).getName());
					row.createCell(1).setCellValue(userList.get(j).getAccount());
					row.createCell(2).setCellValue(userList.get(j).getDept());
					row.createCell(3).setCellValue(userList.get(j).isGender() ? "��" : "Ů");
					row.createCell(4).setCellValue(userList.get(j).getEmail());
				}
			}
			// 5�����
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��ȡ���õĴ�����Ԫ��ʽ
	public static HSSFCellStyle createCellStyle(HSSFWorkbook workbook,short fontsize) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// ��������
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// �Ӵ�
		font.setFontHeightInPoints(fontsize);
		// ��ʽ�м�������
		style.setFont(font);
		return style;
	}

}
