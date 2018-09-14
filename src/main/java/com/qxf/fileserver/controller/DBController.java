package com.qxf.fileserver.controller;


import com.qxf.fileserver.annotation.LOG;
import com.qxf.fileserver.dao.AccountDao;
import com.qxf.fileserver.dao.domain.Account;
import com.qxf.fileserver.dto.AddDTO;
import com.qxf.fileserver.event.SpringUtil;
import com.qxf.fileserver.event.UpdateEvent;
import com.qxf.fileserver.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;


/**
 * @author QIUXUEFU376
 */

@Api(description = "DB接口", tags = "DB")
@RestController
public class DBController {

    @Autowired
    private AccountDao AccountDao;
    @Autowired
    private com.qxf.fileserver.service.AsyncService asyncService;


    @RequestMapping(value = "/db", method = RequestMethod.GET)
    @ApiOperation("db")
    @ResponseBody
    public ResponseVO<Account> db() {


        ApplicationContext ioc = SpringUtil.getApplicationContext();
        ioc.publishEvent(new UpdateEvent(new Object()));
        System.out.println("db Thread: " +Thread.currentThread().getId());

        return ResponseVO.successResponse(AccountDao.findOne(1L));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation("add")
    @ResponseBody
    @LOG
    public ResponseVO<Boolean> add(AddDTO dto) {
        return ResponseVO.successResponse(true);
    }

    @RequestMapping(value = "/AsyncDb", method = RequestMethod.GET)
    @ApiOperation("AsyncDb")
    @ResponseBody
    public ResponseVO<Account> asyncDb() throws Exception {

        System.out.println("asyncDb Thread: " +Thread.currentThread().getId());
        Future<Boolean> task= asyncService.asyncDb();


        /*if(task.isDone()){
            System.out.println("wancheng");
        };*/
        //boolean result = task.get(1000L, TimeUnit.MILLISECONDS);


        return ResponseVO.successResponse(AccountDao.findOne(1L));
    }
}
