package com.jack.financialweb.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;

public class FinancialUserDto {
    @NotBlank(message = "不得為空")
    private String userName;
    @Email(message = "格式錯誤")
    private String email;
    @NotBlank(message = "不得為空")
    private String account;
    @Length(min = 6, message = "至少需6位")
    private String password;
    private String confirmPassword;

    /**
     * 驗證密碼
     * @return
     */
    public boolean ValidPassword(){
        return this.password.equals(confirmPassword);
    }

    /**
     * 轉換成 FinancialUser
     * @return
     */
    public FinancialUser Convert(){
        FinancialUser user = new FinancialUser();
        BeanUtils.copyProperties(this, user);

        return  user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}

