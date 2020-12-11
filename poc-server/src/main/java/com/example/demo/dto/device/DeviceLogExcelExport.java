package com.example.demo.dto.device;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.demo.model.device.DeviceLog;

public class DeviceLogExcelExport {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	private List<DeviceLog> listDeviceLog;

	private void writeHeaderRow() {
		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		Cell cell = row.createCell(0);
		cell.setCellValue("User Id");

		cell = row.createCell(1);
		cell.setCellValue("Name");

		cell = row.createCell(2);
		cell.setCellValue("Temperature");

		cell = row.createCell(3);
		cell.setCellValue("Type");

		cell = row.createCell(4);
		cell.setCellValue("Date");

		cell = row.createCell(5);
		cell.setCellValue("TTCode");

		cell = row.createCell(6);
		cell.setCellValue("DeviceId");

		cell = row.createCell(7);
		cell.setCellValue("Location");

	}

	private void writeDataRows() {

		int rowCount = 1;
		for (DeviceLog log : listDeviceLog) {
			Row row = sheet.createRow(rowCount++);

			Cell cell = row.createCell(0);
			cell.setCellValue(log.getUser().getId());
			sheet.autoSizeColumn(0);

			cell = row.createCell(1);
			cell.setCellValue(log.getName());
			sheet.autoSizeColumn(1);

			cell = row.createCell(2);
			cell.setCellValue(log.getTemperature());
			sheet.autoSizeColumn(2);

			cell = row.createCell(3);
			cell.setCellValue(log.getCardType());
			sheet.autoSizeColumn(3);

			cell = row.createCell(4);
			cell.setCellValue(new Date(log.getTimestamp()).toString());
			System.out.println("Time" + new Date(log.getTimestamp()));
			sheet.autoSizeColumn(4);

			cell = row.createCell(5);
			cell.setCellValue(log.getTtCode());
			sheet.autoSizeColumn(5);

			cell = row.createCell(6);
			cell.setCellValue(log.getDevice().getId());
			sheet.autoSizeColumn(6);

			cell = row.createCell(7);
			cell.setCellValue(log.getDevice().getLocation());
			sheet.autoSizeColumn(7);
		}
	}

	public void export(HttpServletResponse response) throws IOException {
		writeHeaderRow();
		writeDataRows();
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}

	public DeviceLogExcelExport(List<DeviceLog> listDeviceLog) {
		this.listDeviceLog = listDeviceLog;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("USERLOG");

	}
}
