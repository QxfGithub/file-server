/**
 * Copyright (C) 2017 Pingan, Inc. All Rights Reserved.
 */
package com.qxf.fileserver.dao;

import com.qxf.fileserver.dao.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {

}
