package com.qxf.fileserver.service.impl;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.qxf.fileserver.service.PdfService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Service
public class PdfServiceImpl implements PdfService {

    private static final String SIGNATURE_IMG_PATH = "signature/";

    @Override
    public byte[] generatePdfByTemplate(byte[] pdfTemplate, Map<String, String> pdfParamMapping) {
        Assert.notNull(pdfTemplate, "template is null");
        if (pdfParamMapping == null || pdfParamMapping.isEmpty()) {
            throw new IllegalArgumentException("pdfParamMapping can't be empty");
        }

        PdfReader pdfReader = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfStamper stamper = null;
        try {
            // 读取pdf模板
            pdfReader = new PdfReader(pdfTemplate);
            stamper = new PdfStamper(pdfReader, baos);
            AcroFields form = stamper.getAcroFields();
            form.setGenerateAppearances(true);

            // 设置
            ArrayList<BaseFont> fontList = new ArrayList<>();
            fontList.add(getMsyhBaseFont());
            form.setSubstitutionFonts(fontList);

            // 填充form
            for (String formKey : form.getFields().keySet()) {
                form.setField(formKey, pdfParamMapping.getOrDefault(formKey, StringUtils.EMPTY));
            }

            int status = Integer.valueOf(pdfParamMapping.getOrDefault("status", "0"));
            if (status > 5) {
                replaceSignatures(form, stamper, 1);
                replaceSignatures(form, stamper, 2);
                replaceSignatures(form, stamper, 3);
                replaceSignatures(form, stamper, 4);
            }

            // 如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.setFormFlattening(true);
            stamper.close();

            return baos.toByteArray();
        } catch (DocumentException | IOException e) {
            //LOGGER.error(e.getMessage(), e);
        } finally {
            if (stamper != null) {
                try {
                    stamper.close();
                } catch (DocumentException | IOException e) {
                    //LOGGER.error(e.getMessage(), e);
                }
            }
            if (pdfReader != null) {
                pdfReader.close();
            }

        }
        //throw new SystemException("pdf generate failed");


        return baos.toByteArray();
    }

    private void replaceSignatures(AcroFields form, PdfStamper stamper, int seq) throws DocumentException {
        String fieldName = "signature" + seq;
        int pageNo = form.getFieldPositions(fieldName) == null ? 0 : form.getFieldPositions(fieldName).get(0).page;
        Rectangle signRect = form.getFieldPositions(fieldName).get(0).position;
        float x = signRect.getLeft();
        float y = signRect.getBottom();
        String imgPath = SIGNATURE_IMG_PATH + fieldName + ".png";
        Image image = null;
        try {
            image = Image.getInstance(ResourceUtils.CLASSPATH_URL_PREFIX + imgPath);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取操作的页面
        PdfContentByte under = stamper.getOverContent(pageNo);
        // 根据域的大小缩放图片
        image.scaleToFit(signRect.getWidth(), signRect.getHeight());
        // 添加图片
        image.setAbsolutePosition(x, y);
        under.addImage(image);
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
}
