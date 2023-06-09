package com.jack.financialweb.service;

import com.jack.financialweb.exception.NotFoundException;
import com.jack.financialweb.model.FinancialUser;
import com.jack.financialweb.model.FinancialUserDto;
import com.jack.financialweb.model.FinancialUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinancialUserService {
    private final FinancialUserRepository _repo;

    public List<FinancialUser> GetAllUser(){
        return _repo.findAll();
    }

    public FinancialUser GetUser(String account, String password){
        FinancialUser user = _repo.findByAccountAndPassword(account, password);
        if(user == null){
            throw new NotFoundException("Account or Password Error!");
        }
        return user;
    }

    public FinancialUser Insert(FinancialUserDto user){
        return _repo.save(user.Convert());
    }
}
