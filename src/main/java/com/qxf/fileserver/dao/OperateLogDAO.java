/**
 * Copyright (C) 2017 Pingan, Inc. All Rights Reserved.
 */
package com.qxf.fileserver.dao;

import com.qxf.fileserver.dao.domain.OperateLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 操作日志dao
 * @author QIUXUEFU376
 * 2018-07-06
 */
@Repository
public interface OperateLogDAO extends JpaRepository<OperateLog, Long>, JpaSpecificationExecutor<OperateLog> {


}
