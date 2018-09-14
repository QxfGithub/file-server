package com.qxf.fileserver.service;

import java.util.concurrent.Future;

public interface AsyncService {

    Future<Boolean> asyncDb() throws Exception;
}
