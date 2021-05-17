package com.itsmv.api.util;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExcelUtil {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private String wbNm;

	private XSSFSheet sh;
	private XSSFWorkbook wb;
	private XSSFCellStyle cs;
	private XSSFCellStyle headerCs;

	XSSFRow row = null;
	XSSFCell cell = null;

	public ExcelUtil(String name) {
		this.wb = this.defaultWb();
		this.cs = this.defaultCs();
		this.headerCs = this.headerCs();
		this.wbNm = name;
	}

	/* 워크북 생성 */
	public XSSFWorkbook defaultWb() {
		return new XSSFWorkbook();
	}

	/* 기본 셀 스타일 */
	public XSSFCellStyle defaultCs() {
		XSSFCellStyle csTmp = this.wb.createCellStyle();
		/* 가로 가운데 */
		/* cs.setAlignment(HorizontalAlignment.CENTER); */
		/* 세로 가운데 */
		csTmp.setVerticalAlignment(VerticalAlignment.CENTER);

		XSSFFont fontTmp = this.wb.createFont();
		fontTmp.setBold(false);
		fontTmp.setFontName("맑은 고딕");
		fontTmp.setFontHeightInPoints((short) 10);
		csTmp.setFont(fontTmp);

		return csTmp;
	}

	public XSSFCellStyle headerCs() {
		XSSFCellStyle csTmp = this.wb.createCellStyle();
		/* 세로 가운데 */
		csTmp.setVerticalAlignment(VerticalAlignment.CENTER);
		csTmp.setFillForegroundColor(IndexedColors.DARK_TEAL.getIndex());
		csTmp.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		XSSFFont fontTmp = this.wb.createFont();
		fontTmp.setBold(true);
		fontTmp.setColor(IndexedColors.WHITE.getIndex());
		fontTmp.setFontName("맑은 고딕");
		fontTmp.setFontHeightInPoints((short) 12);

		csTmp.setFont(fontTmp);
		return csTmp;
	}

	/* 시트 생성 */
	public void addSheet(String sheetNm) {
		this.sh = this.wb.createSheet(sheetNm);
	}

	public void addRow() {
		this.row = this.sh.createRow(this.sh.getLastRowNum() + 1);
	}

	public void addCell(int cellNum, String cellValue) {
		this.cell = this.sh.getRow(this.sh.getLastRowNum()).createCell(cellNum);
		this.cell.setCellValue(cellValue);
		this.cell.setCellStyle(this.row.getRowNum() == 0 ? this.headerCs : this.cs);
	}

	public void addCell(int cellNum, int cellValue) {
		this.cell = this.sh.getRow(this.sh.getLastRowNum()).createCell(cellNum);
		this.cell.setCellValue(cellValue);
		this.cell.setCellStyle(this.row.getRowNum() == 0 ? this.headerCs : this.cs);
	}

	public void addCell(int cellNum, double cellValue) {
		this.cell = this.sh.getRow(this.sh.getLastRowNum()).createCell(cellNum);
		this.cell.setCellValue(cellValue);
		this.cell.setCellStyle(this.row.getRowNum() == 0 ? this.headerCs : this.cs);
	}

	public void setFilter() {
		this.sh.setAutoFilter(new CellRangeAddress(0, 0, this.row.getFirstCellNum(), (this.row.getLastCellNum() - 1)));
	}

	/* 너비 자동 정렬 */
	public void setWidth(XSSFRow row, int shNo) {
		IntStream.range(0, row.getLastCellNum()).forEach(i -> this.wb.getSheetAt(shNo).autoSizeColumn(i));
	}

	/* 완성된 파일 다운로드 */
	public void download(HttpServletRequest req, HttpServletResponse res) {

		// 겹치는 파일 이름 중복을 피하기 위해 시간을 이용해서 파일 이름에 추
		Date date = new Date();
		SimpleDateFormat dayformat = new SimpleDateFormat("yyyyMMdd");
		String day = dayformat.format(date);
		String fileName = wbNm + "_" + day + ".xlsx";

		// 파일이름 인코딩작업
		try {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < fileName.length(); i++) {
				char c = fileName.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, StandardCharsets.UTF_8));
				} else {
					sb.append(c);
				}
			}
			fileName = sb.toString();
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		res.setContentType("application/download;charset=utf-8");
		res.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		res.setHeader("Content-Transfer-Encoding", "binary");

		OutputStream os = null;

		try {
			os = res.getOutputStream();

			// 파일생성
			this.wb.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (this.wb != null) {
				try {
					this.wb.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (os != null) {
				try {
					os.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
