package com.qxf.fileserver.controller;


import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
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

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ApiOperation("test")
    @ResponseBody
    public ResponseVO<Boolean> test() throws Exception {
        System.out.println("----------------");

        /////////////   一 、接入 transaction 监控接口调用次数 ; 一段程序执行的时间
        String testUrl = "testUrl"; //可以随便定义
        String testEvent = "testEvent";

        //test1
        Transaction t = Cat.newTransaction("URL", testUrl);
        try{
            //业务代码
            t.setStatus(Transaction.SUCCESS);
        }catch (Exception e) {

            e.printStackTrace();
            t.setStatus(e);

        } finally {
            t.complete();
        }

        //test2
        Transaction t1 = Cat.newTransaction("EVENT", testEvent);
        Transaction t2 = Cat.newTransaction("file-server", "qxf");
        try {
            //业务代码1
            for (int i=0 ;i<300;i++){
                System.out.println(i);
            }
            t1.setStatus(Transaction.SUCCESS);

            System.out.println("----------------");

            //业务代码2
            for (int i=400 ;i<700;i++){
                System.out.println(i);
            }
            t2.setStatus(Transaction.SUCCESS);

        } catch (Exception e) {

            e.printStackTrace();
            t1.setStatus(e);
            t2.setStatus(e);
        } finally {

            t1.complete();
            t2.complete();
        }


        ///////// 二、Event用来记录一行code的执行次数
        String serverIp = "127.0.0.1";
        String serverIp1 = "127.0.0.0";
        Cat.logEvent("URL.Server", serverIp, Event.SUCCESS, "ip="+ serverIp + "&...");
        Cat.logEvent("CODE.COUNT", serverIp, Event.SUCCESS, "ip="+ serverIp1 + "&&&&&&&&");

        /////////  三、表示程序内定期产生的统计信息, 如CPU%, MEM%, 连接池状态, 系统负载等。
        Cat.logHeartbeat("file-server","test",Event.SUCCESS,"heartbeat");

        // Cat.logMetricForCount("PayCount");
        //Cat.logMetricForSum("PayAmont", 5);
        return ResponseVO.successResponse(true);
    }
}
