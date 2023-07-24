package com.example.todo.action.auth;

import com.example.todo.model.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {
    @Autowired
    private AccountService service;

    /**
     * ログイン画面の表示
     * @param error (@RequestParam)
     * @param model (Model)
     * @return login.html
     */
    @RequestMapping(value = "/login")
    public String showLoginForm(@RequestParam(name = "error",required = false) String error,Model model){
        if(error!=null){
            model.addAttribute("error","ログイン名またはパスワードが間違っています");
        }
        return "auth/login";
    }

    /**
     * 新規登録画面の表示
     * @param dto (AccountDTO)
     * @return register.html
     */
    @RequestMapping(value = "/register")
    public String showRegisterForm(@ModelAttribute("accountForm") AccountDTO dto){
        return "auth/register";
    }

    /**
     * Accountの新規登録
     * @param dto (AccountDTO)
     * @param result (BindingResult)
     * @param model (Model)
     * @return login.html
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(@ModelAttribute("accountForm") @Validated AccountDTO dto, BindingResult result, Model model){
        if(result.hasErrors()){
            return "auth/register";
        }
        if(!service.isUniqueName(dto.getName())){
            model.addAttribute("err","この名前はすでに使用されています");
            return "auth/register";
        }
        service.saveAccount(dto);
        model.addAttribute("message","アカウントが登録されました");
        return "auth/login";
    }
}
