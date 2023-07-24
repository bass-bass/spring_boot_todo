package com.example.todo.action.auth;

import com.example.todo.entity.Account;
import com.example.todo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsService implements UserDetailsService {
    @Autowired
    private AccountRepository repository;

    /**
     * 認証処理
     * @param name (Account.name)
     * @return AccountDetail
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Account account = repository.findByName(name);
        if (account == null) {
            throw new UsernameNotFoundException("User " + name + " not found");
        }
        return new AccountDetail(account);
    }
}