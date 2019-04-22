package com.qxf.fileserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class FileServerApplicationTests {

    @Test
    public void contextLoads() {
    }

//    newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
//    newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
//    newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
//    newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。

    @Test
    public void test1(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        Long start = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    // while (true)
                    System.out.println("......YJ......" + Thread.currentThread().getId());
                }
            });
        }
        Long end = System.currentTimeMillis();
        System.out.println("time:" + (end - start));
    }

    @Test
    public void test2(){
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 1000; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    // while (true)

                    System.out.println("......YJ......"  + Thread.currentThread().getId());
                }
            });
        }
    }

    @Test
    public void test3(){
        ExecutorService executorService = Executors.newScheduledThreadPool(2);

        for (int i = 0; i < 1000; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    // while (true)

                    System.out.println("......YJ......"  + Thread.currentThread().getId());
                }
            });
        }
    }

    @Test
    public void test4(){

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 1000; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    // while (true)

                    System.out.println("......YJ......"  + Thread.currentThread().getId());
                }
            });
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 4; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                   // while (true)
                        System.out.println("......YJ......" + Thread.currentThread().getId());
                }
            });

        }
    }

}
