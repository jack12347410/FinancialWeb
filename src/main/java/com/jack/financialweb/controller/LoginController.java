package com.jack.financialweb.controller;

import com.jack.financialweb.exception.InvalidRequestException;
import com.jack.financialweb.model.FinancialUser;
import com.jack.financialweb.model.FinancialUserDto;
import com.jack.financialweb.service.FinancialUserService;
import com.jack.financialweb.util.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {
    private final FinancialUserService _service;
    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody FinancialUserDto userDto){
        FinancialUser user = _service.GetUser(userDto.getAccount(), userDto.getPassword());
        String token = JwtUtil.GetToken(user.getEmail());
        return new ResponseEntity<String>(token, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> Register(@Valid @RequestBody FinancialUserDto userDto,
                                      BindingResult result){
        if(!userDto.ValidPassword()){
            result.rejectValue("confirmPassword", "validPswError", "與密碼不一致");
        }

        if(result.hasErrors()){
            throw new InvalidRequestException("Invalid parameter", result);
        }

        FinancialUser user = _service.Insert(userDto);
        return new ResponseEntity<FinancialUser>(user, HttpStatus.CREATED);
    }
}
