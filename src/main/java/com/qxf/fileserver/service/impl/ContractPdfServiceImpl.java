package com.qxf.fileserver.service.impl;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.qxf.fileserver.dto.ContractDTO;
import com.qxf.fileserver.service.ContractPdfService;
import com.qxf.fileserver.service.PdfService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Service
public class ContractPdfServiceImpl implements ContractPdfService {

    @Autowired
    private PdfService PdfService;

    private static final String DEFAULT_STR = "/";
    private static final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat(",##0.00");
    private static final String SIGNATURE_IMG_PATH = "signature/";
    private static final String TRANSFER_LETTER_TEMPLATE_FILE = "transfer_instruction_letter_template.pdf";


    @Override
    public byte[] generatePDF(ContractDTO dto) throws Exception {
        Map<String, String> paramMap = buildMapFromDTO(dto);

        byte[] b = PdfService.generatePdfByTemplate(IOUtils.toByteArray(
                this.getClass().getClassLoader().getResourceAsStream(TRANSFER_LETTER_TEMPLATE_FILE))
                , paramMap);
        return b;
    }

    /**
     * 微软宋体字体
     *
     * @return
     */
    private BaseFont getMsyhBaseFont() throws IOException, DocumentException {
        try {
            return BaseFont.createFont("/SimSun18030.ttc,0", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        } catch (DocumentException | IOException e) {
            //LOGGER.error(e.getMessage(), e);
        }
        return getDefaultBaseFont();
    }

    /**
     * 默认字体
     *
     * @return
     */
    private BaseFont getDefaultBaseFont() throws IOException, DocumentException {
        return BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
    }

    private Map<String, String> buildMapFromDTO(ContractDTO dto) throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("tripartiteAgreementNo", StringUtils.defaultIfEmpty(dto.getTripartiteAgreementNo(), DEFAULT_STR));

        map.put("interest", "¥" + DECIMAL_FORMATTER.format(
                new BigDecimal(String.valueOf(dto.getInterest())))
        );

        Calendar createDate = Calendar.getInstance();
        createDate.setTime(dto.getCreateTime());
        map.put("year", String.valueOf(createDate.get(Calendar.YEAR)));
        map.put("month", String.valueOf(createDate.get(Calendar.MONTH ) + 1));
        map.put("dayOfMonth", String.valueOf(createDate.get(Calendar.DAY_OF_MONTH)));

        return map;

    }
}
