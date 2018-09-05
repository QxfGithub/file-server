package com.qxf.fileserver.controller;


import com.qxf.fileserver.Util.DateUtils;
import com.qxf.fileserver.Util.POIExport;
import com.qxf.fileserver.dto.ExcelDTO;
import com.qxf.fileserver.service.FileUploadService;
import com.qxf.fileserver.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;


/**
 * @author QIUXUEFU376
 */

@Api(description = "文件接口", tags = "File")
@RestController
public class FileController {

    @Autowired
    private FileUploadService fileUploadService;

    @ApiOperation(value = "文件读取")
    @GetMapping(value = "/file/{date}/{key}/{name}.{type}")
    public ResponseEntity<byte[]> read(@PathVariable String date, @PathVariable String key,
                                       @PathVariable String name,@PathVariable String type) throws IOException {

        byte[] content = fileUploadService.read( date, key, name +"."+ type);
        HttpHeaders headers = new HttpHeaders();
        if ("PDF".equalsIgnoreCase(type)) {
            headers.setContentType(MediaType.APPLICATION_PDF);
        } else {
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        }
        headers.set("Content-Disposition", "");
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

    @PostMapping(value = "/attachment")
    @ApiOperation("附件上传")
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name="file", paramType = "form", dataType="file", value = "附件")})
    public ResponseVO<String> upload(MultipartFile file) {
        return ResponseVO.successResponse( fileUploadService.upload(file));
    }

    @ApiOperation("excel导出")
    @GetMapping(value = "/export")
    @ResponseBody
    public  void exportExcel(HttpServletResponse response, @ModelAttribute  ExcelDTO dto) throws IllegalAccessException, IOException, InvocationTargetException {
        // 创建csv文件
        POIExport<ExcelDTO>  exportCsv = new POIExport<>(POIExport.getCsvHeader(0));
        POIExport.getExcelData(dto,exportCsv);
        String suffix = DateUtils.formatDate(new Date(), "yyyy-MM-dd HH_mm_ss");
        exportCsv.exportExcel(response, "excel_" + suffix);
    }

}
