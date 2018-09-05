package com.qxf.fileserver.service;

import com.qxf.fileserver.dto.ContractDTO;

public interface ContractPdfService {

    byte[] generatePDF(ContractDTO dto) throws Exception;
}
