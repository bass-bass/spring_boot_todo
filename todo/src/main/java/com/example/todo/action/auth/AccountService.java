package com.example.todo.action.auth;

import com.example.todo.entity.Account;
import com.example.todo.model.AccountDTO;
import com.example.todo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    /**
     * アカウントの登録
     * passwordをハッシュ化して保存
     * @param dto (AccountDTO)
     */
    public void saveAccount(AccountDTO dto){
        Account entity = new Account();
        entity.setName(dto.getName());
        entity.setPassword(encoder.encode(dto.getPassword()));
        repository.saveAndFlush(entity);
    }

    /**
     * 入力されたnameを持つAccountが存在するかチェック
     * @param name (Account.name)
     * @return boolean
     */
    public boolean isUniqueName(String name){
        Account account = repository.findByName(name);
        if(account==null){
            return true;
        }
        return false;
    }
}
