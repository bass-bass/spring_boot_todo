package com.example.todo.action.auth;

import com.example.todo.entity.Account;
import org.springframework.security.core.userdetails.User;
import java.util.ArrayList;
import lombok.Getter;

public class AccountDetail extends User{
    @Getter
    private Account account;
    public AccountDetail(Account account){
        super(
                account.getName(),
                account.getPassword(),
                true,
                true,
                true,
                true,
                new ArrayList<>()
        );
        this.account = account;
    }
}
