package com.cg.test.am.utils;

import com.github.andyczy.java.excel.ExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * excel工具类
 * 
 * @author 小威老师 xiaoweijiagou@163.com
 *
 */
public class ExcelUtil {

	public static void excelLocal(String path, String fileName, String[] headers, List<Object[]> datas) {
		Workbook workbook = getWorkbook(headers, datas);
		if (workbook != null) {
			ByteArrayOutputStream byteArrayOutputStream = null;
			FileOutputStream fileOutputStream = null;
			try {
				byteArrayOutputStream = new ByteArrayOutputStream();
				workbook.write(byteArrayOutputStream);

				String suffix = ".xls";
				File file = new File(path + File.separator + fileName + suffix);
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}

				fileOutputStream = new FileOutputStream(file);
				fileOutputStream.write(byteArrayOutputStream.toByteArray());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					if (byteArrayOutputStream != null) {
						byteArrayOutputStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 导出excel
	 * 
	 * @param fileName
	 * @param headers
	 * @param datas
	 * @param response
	 */
	public static void excelExport(String fileName, String[] headers, List<Object[]> datas,
			HttpServletResponse response) {
		Workbook workbook = getWorkbook(headers, datas);
		if (workbook != null) {
			ByteArrayOutputStream byteArrayOutputStream = null;
			try {
				byteArrayOutputStream = new ByteArrayOutputStream();
				workbook.write(byteArrayOutputStream);

				String suffix = ".xls";
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
				response.setHeader("Content-Disposition",
						"attachment;filename=" + new String((fileName + suffix).getBytes(), "iso-8859-1"));

				OutputStream outputStream = response.getOutputStream();
				outputStream.write(byteArrayOutputStream.toByteArray());
				outputStream.flush();
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (byteArrayOutputStream != null) {
						byteArrayOutputStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @param headers
	 *            列头
	 * @param datas
	 *            数据
	 * @return
	 */
	public static Workbook getWorkbook(String[] headers, List<Object[]> datas) {
		Workbook workbook = new HSSFWorkbook();

		Sheet sheet = workbook.createSheet();
		Row row = null;
		Cell cell = null;
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER_SELECTION);

		Font font = workbook.createFont();

		int line = 0, maxColumn = 0;
		if (headers != null && headers.length > 0) {// 设置列头
			row = sheet.createRow(line++);
			row.setHeightInPoints(23);
			font.setBold(true);
			font.setFontHeightInPoints((short) 13);
			style.setFont(font);

			maxColumn = headers.length;
			for (int i = 0; i < maxColumn; i++) {
				cell = row.createCell(i);
				cell.setCellValue(headers[i]);
				cell.setCellStyle(style);
			}
		}

		if (datas != null && datas.size() > 0) {// 渲染数据
			for (int index = 0, size = datas.size(); index < size; index++) {
				Object[] data = datas.get(index);
				if (data != null && data.length > 0) {
					row = sheet.createRow(line++);
					row.setHeightInPoints(20);

					int length = data.length;
					if (length > maxColumn) {
						maxColumn = length;
					}

					for (int i = 0; i < length; i++) {
						cell = row.createCell(i);
						cell.setCellValue(data[i] == null ? null : data[i].toString());
					}
				}
			}
		}

		for (int i = 0; i < maxColumn; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;
	}
	
	/**
	 * @description 导入数据（导出也可以使用ExcelUtils）
	 * @param file
	 * @param sheetName
	 * @param indexMap
	 * @param continueRowMap
	 * @return
	 * @throws IOException
	 * @author wuyao
	 * @date 2020年2月18日下午5:34:45
	 */
	public static List<List<LinkedHashMap<String, String>>> importExcel(MultipartFile file,String[] sheetName, HashMap indexMap, HashMap continueRowMap) throws IOException{
		Workbook workBook;
		//InputStream fis=file.getInputStream();
		try{
			workBook = new HSSFWorkbook(file.getInputStream());
			}catch(Exception e){
			workBook = new XSSFWorkbook(file.getInputStream());
			}
		ExcelUtils e=ExcelUtils.initialization();
		return e.importForExcelData(workBook,sheetName, indexMap, continueRowMap);		
	}
}
