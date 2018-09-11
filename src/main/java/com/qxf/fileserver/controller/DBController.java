package com.qxf.fileserver.controller;


import com.qxf.fileserver.annotation.LOG;
import com.qxf.fileserver.dao.AccountDao;
import com.qxf.fileserver.dao.domain.Account;
import com.qxf.fileserver.dto.AddDTO;
import com.qxf.fileserver.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author QIUXUEFU376
 */

@Api(description = "DB接口", tags = "DB")
@RestController
public class DBController {

    @Autowired
    private AccountDao AccountDao;

    @RequestMapping(value = "/db", method = RequestMethod.GET)
    @ApiOperation("db")
    @ResponseBody
    @LOG()
    public ResponseVO<Account> db() {
        return ResponseVO.successResponse(AccountDao.findOne(1L));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation("add")
    @ResponseBody
    @LOG()
    public ResponseVO<Boolean> add(AddDTO dto) {
        return ResponseVO.successResponse(true);
    }
}
