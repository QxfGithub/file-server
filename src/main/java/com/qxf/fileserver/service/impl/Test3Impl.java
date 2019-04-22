package com.qxf.fileserver.service.impl;

import com.qxf.fileserver.service.Test;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@ConditionalOnProperty("test3")
@Service
public class Test3Impl implements  Test{

    @Override
    public String test1(){
        return "test3";
    }
}
