package com.qxf.fileserver.service;

import java.util.Map;

public interface PdfService {

    byte[] generatePdfByTemplate(byte[] pdfTemplate, Map<String, String> pdfParamMapping);
}
