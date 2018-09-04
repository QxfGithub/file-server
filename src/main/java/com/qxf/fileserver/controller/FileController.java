package com.qxf.fileserver.controller;


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

import java.io.IOException;


/**
 * @author QIUXUEFU376
 */

@Api(description = "文件接口", tags = "File")
@RestController
public class FileController {

    @Autowired
    private FileUploadService fileUploadService;

    @ApiOperation(value = "文件读取")
    @GetMapping(value = "/file/{key}/{name}.{type}")
    public ResponseEntity<byte[]> read( @PathVariable String key, @PathVariable String name,@PathVariable String type) throws IOException {

        byte[] content = fileUploadService.read(key, name +"."+ type);
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


}
