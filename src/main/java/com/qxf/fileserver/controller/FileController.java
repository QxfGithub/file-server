package com.qxf.fileserver.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author QIUXUEFU376
 */

@Api(description = "文件接口", tags = "File")
@RestController
public class FileController {


    @GetMapping(value = "/attachment")
    @ApiOperation("查看附件")
    public void detail() {

    }

}
