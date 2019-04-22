package com.qxf.fileserver.controller;


import com.qxf.fileserver.Util.DateUtils;
import com.qxf.fileserver.Util.POIExport;
import com.qxf.fileserver.dto.ContractDTO;
import com.qxf.fileserver.dto.ExcelDTO;
import com.qxf.fileserver.dto.UploadMaterialDTO;
import com.qxf.fileserver.form.UploadMaterialForm;
import com.qxf.fileserver.service.FileUploadService;
import com.qxf.fileserver.service.MaterialService;
import com.qxf.fileserver.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Map;
import com.qxf.fileserver.service.Test;



/**
 * @author QIUXUEFU376
 */

@Api(description = "文件接口", tags = "File")
@RestController
public class FileController {

    /**
     * 文件存储路径
     */
    @Value("${file.store.path:D:/Program Files/File}")
    private String fileStorePath;

    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private com.qxf.fileserver.service.ContractPdfService contractPdfService;
    @Autowired
    private MaterialService materialService;

    private static POIExport POIExport = new POIExport<>();

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

    @PostMapping(value = "/file/upload")
    @ApiOperation("附件上传")
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name="file", paramType = "form", dataType="file", value = "附件")})
    public ResponseVO<String> upload(MultipartFile file) {
        return ResponseVO.successResponse( fileUploadService.upload(file));
    }

    @ApiOperation("excel导出")
    @GetMapping(value = "/Excel/export")
    @ResponseBody
    public  void exportExcel(HttpServletResponse response, @ModelAttribute  ExcelDTO dto) throws IllegalAccessException, IOException, InvocationTargetException {
        // 创建csv文件
        POIExport<ExcelDTO>  exportCsv = new POIExport<>(POIExport.getCsvHeader(0));
        POIExport.getExcelData(dto,exportCsv);
        String suffix = DateUtils.formatDate(new Date(), "yyyy-MM-dd HH_mm_ss");
        exportCsv.exportExcel(response, "excel_" + suffix);
    }

    @ApiOperation("excel解析")
    @PostMapping(value = "/Excel/Parse")
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name="file", paramType = "form", dataType="file", value = "附件")})
    public ResponseVO<String> parseExcel(MultipartFile file)throws IOException, InvalidFormatException {
        String key = fileUploadService.upload(file);
        return ResponseVO.successResponse(POIExport.parseExcel(fileStorePath,file.getOriginalFilename(),key));
    }

    @ApiOperation(value = "生成PDF")
    @GetMapping(value = "/pdf")
    @ResponseBody
    public ResponseEntity<byte[]> previewAgreement(ContractDTO dto) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.add("Content-Disposition","1.pdf");
        byte[] pdfBytes = contractPdfService.generatePDF(dto);
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/base64/upload" , method = RequestMethod.PUT)
    @ApiOperation("base64流上传")
    @ResponseBody
    public ResponseVO<Map<String,String> > upload(@RequestBody UploadMaterialForm materialForm) {
        UploadMaterialDTO dto = new UploadMaterialDTO();
        BeanUtils.copyProperties(materialForm, dto);
        return ResponseVO.successResponse(materialService.uploadMaterial(dto));
    }

    @RequestMapping(value = "/base64/{key}/{ext}", method = RequestMethod.GET)
    @ApiOperation("材料查询")
    @ResponseBody
    public ResponseVO<Map<String,String>> queryMaterial(@PathVariable("key") String key,@PathVariable("ext") String ext) {
        return ResponseVO.successResponse(materialService.queryMaterial(key,ext));
    }


    @Autowired
    Test test;

    @RequestMapping(value = "/aaaa", method = RequestMethod.GET)
    @ApiOperation("aaaa")
    @ResponseBody
    public String aaa() {
        return (test.test1());
    }

}
