package com.qxf.fileserver.Util;

import com.qxf.fileserver.dto.ExcelDTO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class POIExport<T> {

    private List<ColInfo> headers;

    private SXSSFWorkbook sxssfWorkbook;

    private Sheet sheet;

    private Map<String, ColInfo> colInfoMap; // 列信息

    private int rowNum=0;
    private OutputStream out;
    public POIExport(List<ColInfo> headers) {
        this.headers = headers;
        init();
    }
    public List<ColInfo> getHeaders() {
        return headers;
    }

    public void setHeaders(List<ColInfo> headers) {
        this.headers = headers;
    }

    public   void exportExcel(HttpServletResponse response,String fileName) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition",
                "attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx\"");
        response.setHeader("Cache-Control", "max-age=0");
        OutputStream respOut=response.getOutputStream();
        sxssfWorkbook.write(out);
        IOUtils.copy(getInputStream(), respOut);
        sxssfWorkbook.close();
        sxssfWorkbook.dispose();
        out.close();
        respOut.close();
    }


    void init(){
        this.out = new ByteArrayOutputStream();
        // 产生表格标题行
        if (colInfoMap ==null) {
            colInfoMap = new HashMap<>();// 列信息
        }
        sxssfWorkbook = new SXSSFWorkbook(100);
        sheet = sxssfWorkbook.createSheet();
            // 首行
         Row row = sheet.createRow(0);
         for(int j=0;j<headers.size();j++){
             ColInfo colInfo = headers.get(j);
             colInfoMap.put(colInfo.getColName(), colInfo);
             row.createCell(j).setCellValue(headers.get(j).getShowName());
         }
        rowNum++;
    }

    public void   writeDataRows (List<T> list) throws InvocationTargetException, IllegalAccessException {
        int last=rowNum;
        for (int i = rowNum; i < list.size()+last; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < headers.size(); j++) {
                T data=list.get(i-last);
                if (data instanceof Map) {
                    Map<?, ?> map = (Map<?, ?>) data;
                    String colName = headers.get(j).getColName();
                    Object value = map.get(colName);
                    CellUtil.createCell(row, j,  handelCellValue(colName, value));
                }else {
                        String colName = headers.get(j).getColName();
                        Object value = BeanUtils.getPropertyValue(data, colName);
                    CellUtil.createCell(row, j,  handelCellValue(colName, value));
                }

            }
            rowNum++;
        }
    }

    private String handelCellValue(String key, Object value) {
        String result = "";

        ColInfo colInfo = colInfoMap.get(key);
        ColInfo.TYPE type = colInfo.getType();
        String format = colInfo.getFormat();
        Map<String, String> map = colInfo.getMap();
        if (value == null) {
            return result;
        }
        String textValue = value + "";
        if (textValue.trim().equals("")) {
            return result;
        }
        switch (type) {
            case STRING:
                String mapVal = null;
                if (map != null) {// 获取映射配置
                    mapVal = map.get(textValue);
                    if (mapVal == null) {
                        mapVal = map.get("default");
                    }
                }
                if (mapVal == null) {
                    mapVal = textValue;
                }

                if (!StringUtils.isEmpty(format)) {
                    mapVal = String.format(format, mapVal);
                }

                result = mapVal;
                break;
            case NUMBER:
                if (!StringUtils.isEmpty(format)) {
                    DecimalFormat df = new DecimalFormat(format);
                    result = df.format(value);
                } else {
                    result = textValue;
                }
                break;
            case DATE:
                Date date = (Date) value;
                if (StringUtils.isEmpty(format)) {
                    format = "yyyy-MM-dd hh:mm:ss";
                }
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
                break;
            default:
                result = textValue;
                break;
        }
        return result;

    }

    /**
     * 转换文件流
     *
     * @throws IOException
     */
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
    }

    /** 设置csv文件头信息 */
    public static List<ColInfo> getCsvHeader(Integer transactionType) {
        Map<String, String> payTypeMap = new HashMap<>();
        payTypeMap.put("default", "未知");
        payTypeMap.put("0", "POS");
        payTypeMap.put("1", "转账");
        List<ColInfo> headers = new ArrayList<>();
        headers.add(new ColInfo("transactionTime", "日期", ColInfo.TYPE.DATE, "yyyy-MM-dd HH:mm:ss"));
        headers.add(new ColInfo("transactionNo", "流水单号", ColInfo.TYPE.STRING));
        headers.add(new ColInfo("paymentType", "付款方式", ColInfo.TYPE.STRING, payTypeMap));
        headers.add(new ColInfo("paymentBankAccount", "付款卡号", ColInfo.TYPE.STRING));
        headers.add(new ColInfo("paymentBankName", "所属银行", ColInfo.TYPE.STRING));
        if(transactionType.equals(0)){
            headers.add(new ColInfo("incomeAmountYuan", "收入(元)", ColInfo.TYPE.NUMBER, ",###.00"));
        }else if(transactionType.equals(1)){
            headers.add(new ColInfo("expensesAmountYuan", "支出(元)", ColInfo.TYPE.NUMBER, ",###.00"));
            headers.add(new ColInfo("poundageYuan", "手续费(元)", ColInfo.TYPE.NUMBER, ",###0.00"));
        }
        headers.add(new ColInfo("supervisionBankName", "监管银行", ColInfo.TYPE.STRING));
        headers.add(new ColInfo("netSignContractNo", "合同编号", ColInfo.TYPE.STRING ));
        headers.add(new ColInfo("tripleAgreementNo", "协议编号", ColInfo.TYPE.STRING));
        headers.add(new ColInfo("sellerName", "甲方", ColInfo.TYPE.STRING));
        headers.add(new ColInfo("sellerTrusteeName", "甲方（受托人）", ColInfo.TYPE.STRING));
        headers.add(new ColInfo("buyerName", "乙方", ColInfo.TYPE.STRING ));
        headers.add(new ColInfo("buyerTrusteeName", "乙方（受托人）", ColInfo.TYPE.STRING));
        headers.add(new ColInfo("fundType", "资金类型", ColInfo.TYPE.STRING));

        return headers;
    }

    public static void getExcelData(ExcelDTO dto,POIExport<ExcelDTO> exportCsv) throws  IllegalAccessException, InvocationTargetException {
        List<ExcelDTO> resultVOs=new ArrayList<>(10000);
        resultVOs.add(dto);
        exportCsv.writeDataRows(resultVOs);
    }


}
