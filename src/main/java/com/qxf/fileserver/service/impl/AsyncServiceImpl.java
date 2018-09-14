package com.qxf.fileserver.service.impl;

import com.qxf.fileserver.service.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class AsyncServiceImpl implements AsyncService {

    @Async
    @Override
    public Future<Boolean> asyncDb() throws Exception{

        System.out.println("AsyncServiceImpl.asyncDb Thread: " +Thread.currentThread().getId());

        System.out.println("开始做任务一");
        long start = System.currentTimeMillis();
        Thread.sleep(10000);
        long end = System.currentTimeMillis();
        System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");

        return new AsyncResult<>(true);

    }
}

