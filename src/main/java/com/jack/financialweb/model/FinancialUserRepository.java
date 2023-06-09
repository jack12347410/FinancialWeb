package com.jack.financialweb.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialUserRepository extends JpaRepository<FinancialUser, Long> {
    public FinancialUser findByAccountAndPassword(String account, String password);
}
