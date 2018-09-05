//package com.qxf.fileserver.Util;
//
//import com.pingan.haofang.gov.common.constants.Constants;
//import com.pingan.haofang.gov.common.dto.PaginatedQueryDTO;
//import com.pingan.haofang.gov.common.dto.PaginatedReturnDTO;
//import com.pingan.haofang.gov.common.dto.WrongExcelDataDTO;
//import com.pingan.haofang.gov.common.dto.WrongExcelIdDTO;
//import com.pingan.haofang.gov.order.dto.RecordQueryDTO;
//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.*;
//
//import java.io.*;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//public class ExcelUtil {
//	public static XSSFWorkbook createExcel(List<String> titles, List<List<String>> dataList) {
//		XSSFWorkbook wb = new XSSFWorkbook();
//		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
//		XSSFSheet sheet = wb.createSheet("sheet");
//		// 设置表格默认列宽度为18个字节
//		sheet.setDefaultColumnWidth(18);
//		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
//		XSSFRow row = sheet.createRow(0);
//		// 第四步，创建单元格，并设置值表头 设置表头居中
//		XSSFCellStyle style = wb.createCellStyle();// 标题样式
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
//		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		XSSFFont headerFont = wb.createFont(); // 标题字体
//		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		headerFont.setFontHeightInPoints((short) 11);
//		style.setFont(headerFont);
//		// 创建HSSFCell对象
//		XSSFCell cell;
//		XSSFCellStyle contentStyle = wb.createCellStyle();
//		// 设置单元格的表头标题值
//		for (int i = 0; i < titles.size(); i++) {
//			cell = row.createCell(i);
//			cell.setCellStyle(style);
//			cell.setCellValue(titles.get(i));
//		}
//
//		// 写入实体数据
//		for (int i = 0; i < dataList.size(); i++) {
//			row = sheet.createRow(i + 1);
//			for (int j = 0; j < dataList.get(i).size(); j++) {
//				cell = row.createCell(j);
//				contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
//				contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//				cell.setCellStyle(contentStyle);
//				cell.setCellValue(dataList.get(i).get(j));
//			}
//		}
//		return wb;
//	}
//
//	/**
//	 * 统一EXCEL导出
//	 *
//	 * @param excelResolver
//	 * @param queryDto
//	 * @return
//	 */
//	public static HSSFWorkbook createExcelWithPaginate(ExcelResolver excelResolver, PaginatedQueryDTO queryDto) {
//		RecordQueryDTO dto = (RecordQueryDTO) queryDto.getData();
//		HSSFWorkbook wb = new HSSFWorkbook();
//		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
//		HSSFSheet sheet = wb.createSheet("sheet");
//		// 设置表格默认列宽度为18个字节
//		sheet.setDefaultColumnWidth(18);
//		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
//		HSSFRow row = sheet.createRow(0);
//		// 第四步，创建单元格，并设置值表头 设置表头居中
//		HSSFCellStyle style = wb.createCellStyle();// 标题样式
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
//		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		HSSFFont headerFont = wb.createFont(); // 标题字体
//		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);BOLDWEIGHT_BOLD
//		headerFont.setFontHeightInPoints((short) 11);
//		style.setFont(headerFont);
//		// 创建HSSFCell对象
//		HSSFCell cell;
//		HSSFCellStyle contentStyle = wb.createCellStyle();
//		contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
//		contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		List<String> titles = excelResolver.getTitles(dto);// 获取标题
//		// 设置单元格的表头标题值
//		for (int i = 0; i < titles.size(); i++) {
//			cell = row.createCell(i);i
//			cell.setCellType(HSSFCell.ENCODING_UTF_16);
//			cell.setCellStyle(style);
//			cell.setCellValue(titles.get(i));
//		}
//
//		int start = 0;
//		// 每次查询10000条数据
//		queryDto.setLimit(Constants.UPLOAD_MAX_PAGE_SIZE);
//
//		PaginatedReturnDTO page = excelResolver.queryRecords(dto); // 获取数据
//		int total = page.getTotalCount();
//		for (int k = start; k < total; k += Constants.UPLOAD_MAX_PAGE_SIZE) {
//			@SuppressWarnings("unchecked")
//			List<List<String>> dataList = (List<List<String>>) page.getData();
//			// 写入实体数据
//			for (int i = 0; i < dataList.size(); i++) {
//				row = sheet.createRow(i + 1 + start);
//				for (int j = 0; j < dataList.get(i).size(); j++) {
//					cell = row.createCell(j);
//                    cell.setCellType(HSSFCell.ENCODING_UTF_16);
//					String value = dataList.get(i).get(j);
//					cell.setCellStyle(contentStyle);
//					value = null == value ? "" : value;
//					cell.setCellValue(value);
//				}
//			}
//
//			start += Constants.UPLOAD_MAX_PAGE_SIZE;
//			if (start >= total)
//				break;
//			page = excelResolver.queryRecords(dto); // 获取数据
//		}
//
//		return wb;
//	}
//
//	public static XSSFWorkbook createErrorExcel(Sheet sheetdata, List<Integer> idList, List<String> titles, List<Boolean> flagList, List<String> errorValueList) {
//		XSSFWorkbook wb = new XSSFWorkbook();
//		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
//		XSSFSheet sheet = wb.createSheet("sheet");
//		// 设置表格默认列宽度为18个字节
//		sheet.setDefaultColumnWidth(18);
//		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
//		XSSFRow row = sheet.createRow(0);
//		// 第四步，创建单元格，并设置值表头 设置表头居中
//		XSSFCellStyle style = wb.createCellStyle();// 标题样式
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
//		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		XSSFFont headerFont = wb.createFont(); // 标题字体
//		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		headerFont.setFontHeightInPoints((short) 11);
//		style.setFont(headerFont);
//		// 创建HSSFCell对象
//		XSSFCell cell;
//		XSSFCellStyle contentStyle = wb.createCellStyle();
//		// 设置单元格的表头标题值
//		for (int i = 0; i < titles.size(); i++) {
//			cell = row.createCell(i);
//			cell.setCellStyle(style);
//			cell.setCellValue(titles.get(i));
//		}
//		// 标黄的样式
//		XSSFCellStyle contentYellowStyle = wb.createCellStyle();
//		Row rowData = null;
//		Cell cellData = null;
//		// 写入实体数据
//		for (int i = 0; i < idList.size(); i++) {
//			row = sheet.createRow(i + 1);
//			rowData = sheetdata.getRow(idList.get(i));
//			for (int j = 0; j < titles.size(); j++) {
//				cellData = rowData.getCell(j);
//				String value = getCellFormatValue(cellData);
//				// contentStyle = wb.createCellStyle();
//				cell = row.createCell(j);
//				contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
//				contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//				// 错误的设置标黄
//				if (!flagList.get(i).booleanValue() && j == 1) {
//					contentYellowStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
//					contentYellowStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
//					Sheet errorSheet = wb.getSheetAt(0);
//					// 创建备注
//					Drawing p = errorSheet.createDrawingPatriarch();
//					Comment comment = p.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, 3, 3, 5, 6));
//					comment.setString(new XSSFRichTextString(errorValueList.get(i)));
//					cell.setCellComment(comment);
//
//					DataFormat format = wb.createDataFormat();
//					contentYellowStyle.setDataFormat(format.getFormat("@"));
//					cell.setCellStyle(contentYellowStyle);
//					cell.setCellValue(value);
//					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//				} else {
//					DataFormat format = wb.createDataFormat();
//					contentStyle.setDataFormat(format.getFormat("@"));
//					cell.setCellStyle(contentStyle);
//					cell.setCellValue(value);
//					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//				}
//			}
//		}
//		return wb;
//	}
//
//	/**
//	 * 根据Cell类型设置数据
//	 *
//	 * @param cell
//	 * @return String
//	 */
//	public static String getCellFormatValue(Cell cell) {
//		String cellvalue = "";
//		if ((null != cell) && (!cell.toString().equals(""))) {
//			// 判断当前Cell的Type
//			switch (cell.getCellType()) {
//			case Cell.CELL_TYPE_FORMULA: {
//				cellvalue = cell.getCellFormula().trim();
//				break;
//			}
//			case Cell.CELL_TYPE_NUMERIC: {
//				// 判断当前的cell是否为Date
//				// try
//				{
//					if (HSSFDateUtil.isCellDateFormatted(cell)) {
//						// 如果是Date类型则，转化为Data格式
//						Date date = cell.getDateCellValue();
//						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//						cellvalue = sdf.format(date).trim();
//					} else
//					// 如果是纯数字
//					{
//						// 取得当前Cell的数值
//						DecimalFormat df = new DecimalFormat("0");
//						cellvalue = df.format(cell.getNumericCellValue()).trim();
//					}
//					break;
//				}
//			}
//			// 如果当前Cell的Type为STRIN
//			case Cell.CELL_TYPE_STRING:
//				// 取得当前的Cell字符串
//				cellvalue = cell.getRichStringCellValue().getString().trim();
//				break;
//			// 默认的Cell值
//			default:
//				cellvalue = " ";
//			}
//		} else {
//			cellvalue = "";
//		}
//		return cellvalue;
//
//	}
//
//	/**
//	 *
//	 * Description: 处理excel的名字
//	 *
//	 * @param orgFileName
//	 * @return
//	 */
//	public static String getFileNameShort(String orgFileName) {
//		if (null != orgFileName && orgFileName.length() > 20) {
//			return orgFileName.substring(orgFileName.length() - 20);
//		} else {
//			return orgFileName;
//		}
//	}
//
//	/**
//	 *
//	 * Description:解析Excel数据;
//	 *
//	 * @param wb
//	 * @return
//	 *
//	 *         public static List<List<Cell>> readExcel(Workbook wb) { Row row;
//	 *         // 遍历sheet页 Sheet sheet = wb.getSheetAt(0); int lastRowNum =
//	 *         sheet.getLastRowNum(); Row inputTitle = sheet.getRow(0); int
//	 *         cellNum = inputTitle.getLastCellNum(); List<List<Cell>> list =
//	 *         new ArrayList<List<Cell>>(); boolean effectiveData = false; for
//	 *         (int i = 1; i <= lastRowNum; i++) { row = sheet.getRow(i); if
//	 *         (null != row) { List<Cell> data = new ArrayList<Cell>(); for (int
//	 *         j = inputTitle.getFirstCellNum(); j < cellNum; j++) { if ((null
//	 *         != row.getCell(j)) && (!row.getCell(j).toString().equals("")))
//	 *         effectiveData = true; data.add(row.getCell(j)); } /* 是有效数据 if
//	 *         (effectiveData) { list.add(data); effectiveData = false; } } }
//	 *         return list; }
//	 */
//	/**
//	 *
//	 * Description: 获取excel表头
//	 *
//	 * @param sheet
//	 * @return
//	 */
//	public static List<String> getTitles(Sheet sheet, int num) {
//		List<String> titles = new ArrayList<String>();
//		Row row = sheet.getRow(num);
//		int cellSize = row.getLastCellNum();
//		for (int k = 0; k < cellSize; k++) {
//			Cell cell = row.getCell(k);
//			titles.add(cell.toString());
//		}
//		return titles;
//	}
//
//	public static List<Map<String, String>> getExcelData(Sheet sheet, List<String> titles, int rowSize, int start) {
//		List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
//		for (int i = start; i < rowSize; i++) {
//			Map<String, String> rowMap = new HashMap<String, String>(); // 对应一个数据行
//			Row row = sheet.getRow(i);
//			for (int j = 0; j < titles.size(); j++) {
//				Cell cell = row.getCell(j);
//				String key = titles.get(j);
//				String value = getCellFormatValue(cell);
//				rowMap.put(key, value);
//			}
//			listMap.add(rowMap);
//		}
//		return listMap;
//	}
//
//	// 忽略空白行
//	public static boolean isRowEmpty(Row row) {
//		if (!ValidationUtil.isEmpty(row)) {
//			for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
//				Cell cell = row.getCell(c);
//				if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
//
//					return false;
//			}
//		}
//		return true;
//	}
//
//	/**
//	 * 与模板进行比对，判断上传的文件是否符合模板
//	 *
//	 * @param importWb
//	 * @param moduleWb
//	 * @return
//	 * @throws Exception
//	 */
//	public static HashMap<String, Object> validateModule(Workbook importWb, String moduleType) throws Exception {
//		File date = new File(moduleType);
//		FileInputStream fop = new FileInputStream(date);
//		XSSFWorkbook moduleWb = new XSSFWorkbook(fop);
//		HashMap<String, Object> result = new HashMap<String, Object>();
//		Sheet sheet;
//		Sheet moduleSheet;
//		// 读取出上传文件的标题栏
//		sheet = importWb.getSheetAt(1);
//		Row importTitle = sheet.getRow(2);
//
//		// 读取出对应模板的标题栏
//		moduleSheet = moduleWb.getSheetAt(1);
//		Row moduleTitle = moduleSheet.getRow(2);
//		// 与模板进行比对校验
//		if ((null == importTitle) || (importTitle.getLastCellNum() != moduleTitle.getLastCellNum())) {
//			result.put("error", "导入模板错误，请确认");
//			return result;
//		}
//		if (!importTitle.getCell(0).toString().equals(moduleTitle.getCell(0).toString())) {
//			result.put("error", "导入模板错误，请确认");
//			return result;
//		} else {
//			for (int i = 0; i < importTitle.getLastCellNum(); i++)
//				if (!importTitle.getCell(i).toString().equals(moduleTitle.getCell(i).toString())) {
//					result.put("error", "导入模板错误，请确认");
//					return result;
//				}
//		}
//
//		result.put("error", "");
//		result.put("sheet", sheet);
//		result.put("title", importTitle);
//		return result;
//	}
//
//	/**
//	 * 区分excel有效/无效数据
//	 *
//	 * @param file
//	 * @param modulePath
//	 * @return
//	 *
//	 */
//	public static HashMap<String, Object> readEffecticveExcel(HashMap<String, Object> result) {
//		// 读取出对应模板的标题栏
//		if (result.get("error").equals("")) {
//			boolean effectiveData = false;
//			int effectiveRowNum = 0;
//			Sheet sheet = (Sheet) result.get("sheet");
//			Row row;
//			Row importTitle = (Row) result.get("title");
//			// 读取导入excel的数据行数
//			int importLastRowNum = sheet.getLastRowNum();
//			List<List<Cell>> list = new ArrayList<List<Cell>>();
//			for (int i = 3; i <= importLastRowNum; i++) {
//				row = sheet.getRow(i);
//				if (null != row) {
//					List<Cell> data = new ArrayList<Cell>();
//					data.add(row.getCell(0));
//					for (int j = 1; j < importTitle.getLastCellNum(); j++) {
//						if (row.getCell(j) != null && row.getCell(j).getCellType() != Cell.CELL_TYPE_BLANK)
//							effectiveData = true;
//						data.add(row.getCell(j));
//					}
//					// 是有效数据
//					if (effectiveData) {
//						effectiveRowNum++;
//						list.add(data);
//						effectiveData = false;
//					}
//				}
//			}
//			if (effectiveRowNum > 1000) {
//				result.put("error", "数据条数不可超过1000条;");
//			} else {
//				result.put("out", "excel读取成功");
//				result.put("data", list);
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * 映射excel
//	 *
//	 * @throws IOException
//	 */
//	public static OutputStream outWrongExcel(List<WrongExcelDataDTO> wrongSheetData, String moduleType) throws IOException {
//		// 打开模板
//		File date = new File(moduleType);
//		FileInputStream fop = new FileInputStream(date);
//		XSSFWorkbook wb1 = new XSSFWorkbook(fop);
//		Sheet errorSheet = wb1.getSheetAt(1);
//		OutputStream errorStream = new ByteArrayOutputStream();
//
//		try {
//			int wrongRowNum = 3;
//			Row errorTitle = errorSheet.getRow(2);
//			// 创建备注
//			Drawing p = errorSheet.createDrawingPatriarch();
//			List<Cell> rowCells = null;
//			List<WrongExcelIdDTO> errorIdList = null;
//			String titleName = null;
//			CellStyle style = null;
//			DecimalFormat df = new DecimalFormat("0.00");
//
//			for (int j = 0; j < wrongSheetData.size(); j++) {
//				// 读取一行错误的excel数据以及错误原因
//				Row errorRow = errorSheet.getRow(wrongRowNum);
//				rowCells = wrongSheetData.get(j).getData();
//				errorIdList = wrongSheetData.get(j).getWrongIdDTO();
//				for (int j1 = 0; j1 < rowCells.size(); j1++) {
//					Cell cell = errorRow.getCell(j1);
//					titleName = getCellFormatValue(errorTitle.getCell(j1));
//					// 建筑面积、房屋评估价、被担保主债权金额、最高债权数额、合同总价要设置为数值
//					if (titleName.matches(".*建筑面积.*") || titleName.matches(".*房屋评估价.*") || titleName.matches(".*被担保主债权数额.*") || titleName.matches(".*最高债权数额.*")
//							|| titleName.matches(".*合同总价.*")) {
//						if (!getCellFormatValue(rowCells.get(j1)).toString().equals("")) {
//							if (rowCells.get(j1).getCellType() == Cell.CELL_TYPE_NUMERIC) {
//								String cellvalue = df.format(rowCells.get(j1).getNumericCellValue()).trim();
//								cell.setCellValue(Double.parseDouble(cellvalue));
//							} else {
//								cell.setCellValue(getCellFormatValue(rowCells.get(j1)).toString());
//							}
//						} else
//							cell.setCellValue("");
//					} else if (titleName.matches(".*手机号.*")) {
//						if (!getCellFormatValue(rowCells.get(j1)).toString().equals(""))
//							cell.setCellValue(getCellFormatValue(rowCells.get(j1)).toString());
//							//cell.setCellValue(Long.parseLong(getCellFormatValue(rowCells.get(j1)).toString()));
//						else
//							cell.setCellValue("");
//
//					} else {
//						cell.setCellValue(getCellFormatValue(rowCells.get(j1)).toString());
//					}
//
//				}
//				// 为错误的cell标记颜色和设置comment
//				for (int j2 = 0; j2 < errorIdList.size(); j2++) {
//					WrongExcelIdDTO dto = errorIdList.get(j2);
//					int wrongId = dto.getId();
//					Cell cell = errorRow.createCell(wrongId);
//					titleName = getCellFormatValue(errorTitle.getCell(wrongId));
//					style = wb1.createCellStyle();
//					style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
//					style.setFillPattern(CellStyle.SOLID_FOREGROUND);
//					if (titleName.matches(".*证件号.*")) {
//						DataFormat format = wb1.createDataFormat();
//						style.setDataFormat(format.getFormat("@"));
//						cell.setCellValue(getCellFormatValue(rowCells.get(wrongId)).toString());
//						cell.setCellStyle(style);
//					}
//					// 建筑面积、房屋评估价、被担保主债权金额、最高债权数额、合同总价要设置为数值
//					else if (titleName.matches(".*建筑面积.*") || titleName.matches(".*房屋评估价.*") || titleName.matches(".*被担保主债权金额.*")
//							|| titleName.matches(".*最高债权数额.*") || titleName.matches(".*合同总价.*")) {
//						style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
//						if (!getCellFormatValue(rowCells.get(wrongId)).toString().equals("")) {
//							cell.setCellStyle(style);
//							if (rowCells.get(wrongId).getCellType() == Cell.CELL_TYPE_NUMERIC) {
//								String cellvalue = df.format(rowCells.get(wrongId).getNumericCellValue()).trim();
//								cell.setCellValue(Double.parseDouble(cellvalue));
//							} else {
//								cell.setCellValue(getCellFormatValue(rowCells.get(wrongId)).toString());
//							}
//						} else
//							cell.setCellValue("");
//						cell.setCellStyle(style);
//					} else if (titleName.matches(".*日期.*")) {
//						//DataFormat format = wb1.createDataFormat();
//						//style.setDataFormat(format.getFormat("yyyy-mm-dd"));
//						cell.setCellValue(getCellFormatValue(rowCells.get(wrongId)).toString());
//						cell.setCellStyle(style);
//					} else {
//						cell.setCellValue(getCellFormatValue(rowCells.get(wrongId)).toString());
//					}
//					Comment comment = p.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, 3, 3, 5, 6));
//					comment.setString(new XSSFRichTextString(dto.getReason()));
//					errorRow.getCell(dto.getId()).setCellComment(comment);
//					errorRow.getCell(dto.getId()).setCellStyle(style);
//				}
//				wrongRowNum++;
//			}
//			wb1.write(errorStream);
//
//			return errorStream;
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			return null;
//		} finally {
//			errorStream.flush();
//			errorStream.close();
//			wb1 = null;
//			System.gc();
//		}
//	}
//
//	/**
//	 * 映射excel
//	 *
//	 * @throws IOException
//	 */
//	public static Workbook excelbusinessStatistics(Map<String, Object> map, Workbook wb1) throws IOException {
//		Sheet sheet = wb1.getSheetAt(0);
//		List<List<Integer>> list = (List<List<Integer>>) map.get("varList");
//		List<String> titles = (List<String>) map.get("titels");
//		CellStyle normal = wb1.getSheetAt(0).getRow(0).getCell(0).getCellStyle();
//		Row row1 = sheet.getRow(0);
//		for (int t = 0; t < titles.size(); t++) {
//			Cell cell = row1.createCell(t + 2);
//			cell.setCellValue(titles.get(t));
//			cell.setCellStyle(normal);
//		}
//		for (int i = 0; i < list.size(); i++) {
//			List<Integer> count = list.get(i);
//			Row row = sheet.getRow(i + 1);
//			for (int t = 0; t < count.size(); t++) {
//				Cell cell = row.createCell(t + 2);
//				cell.setCellValue(count.get(t));
//				cell.setCellStyle(normal);
//			}
//
//		}
//		return wb1;
//	}
//
//}
