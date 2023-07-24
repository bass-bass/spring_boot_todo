package com.example.todo.action;

import com.example.todo.action.auth.AccountService;
import com.example.todo.entity.Account;
import com.example.todo.model.CommentDTO;
import com.example.todo.model.TodoPageWrapper;
import com.example.todo.model.TodoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.BindingResult;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TodoController {

    private static final int DEFAULT_PAGE_SIZE = 5;

    @Autowired
    private TodoService service;
    @Autowired
    private AccountService accountService;
    /**
     * Todoの一覧を返却
     * @param account (Account)
     * @param pageable (Pageable 1ページ5件)
     * @param model (Model)
     * @return index.html
     */
    @RequestMapping(value="/index")
    public String index(@AuthenticationPrincipal(expression = "account") Account account,
                        @PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable,
                        Model model) {
        Long accountId = account.getId();
        TodoPageWrapper page = service.getList(accountId,pageable);
        model.addAttribute("header","ToDo List");
        model.addAttribute("todos",page.getDtoList());
        model.addAttribute("page",page);
        return "todo/index";
    }
    /**
     * Todo作成ページの表示
     * @param dto (TodoDTO)
     * @return create.html
     */
    @RequestMapping(value="/create")
    public String showCreate(@ModelAttribute("formModel") TodoDTO dto){
        return "todo/create";
    }
    /**
     * Todoの新規作成
     * @param account (Account)
     * @param dto (TodoDTO)
     * @param result (BindingResult)
     * @return /indexへリダイレクト
     */
    @RequestMapping(value="/create/done", method = RequestMethod.POST)
    public String create(@AuthenticationPrincipal(expression = "account") Account account,
                         @ModelAttribute("formModel") @Validated TodoDTO dto,
                         BindingResult result) {
        if(result.hasErrors()){
            return "todo/create";
        }
        service.saveTodo(dto,account);
        return "redirect:/index";
    }
    /**
     * Todo編集ページの表示
     * @param account (Account)
     * @param dto (TodoDTO)
     * @param id (Todoのid)
     * @param attributes (redirectAttributes)
     * @param model (Model)
     * @return edit.html
     */
    @RequestMapping(value="/edit/{id}")
    public String showEdit(@AuthenticationPrincipal(expression = "account") Account account,
                           @ModelAttribute("formModel") TodoDTO dto,
                           @PathVariable Long id,
                           RedirectAttributes attributes,
                           Model model) {
        TodoDTO todo = service.getTodo(id,account.getId());
        if(todo == null){
            attributes.addFlashAttribute("error","ToDoが見つかりませんでした");
            return "redirect:/index";
        }
        model.addAttribute("formModel",todo);
        return "todo/edit";
    }
    /**
     * Todoの編集
     * @param account (Account)
     * @param dto (TodoDTO)
     * @param result (BindingResult)
     * @return /indexへリダイレクト
     */
    @RequestMapping(value="/edit/done", method = RequestMethod.POST)
    public String edit(@AuthenticationPrincipal(expression = "account") Account account,
                       @ModelAttribute("formModel") @Validated TodoDTO dto,
                       BindingResult result) {
        if(result.hasErrors()) {
            return "todo/edit";
        }
        service.saveTodo(dto,account);
        return "redirect:/index";
    }
    /**
     * Todoの削除
     * @param account (Account)
     * @param id (Todo.id)
     * @param model (Model)
     * @param attributes (RedirectAttributes)
     * @return /indexへリダイレクト
     */
    @RequestMapping(value="/delete", method = RequestMethod.POST)
    public String delete(@AuthenticationPrincipal(expression = "account") Account account,
                         @RequestParam Long id,
                         RedirectAttributes attributes,
                         Model model) {
        if(!service.deleteTodo(id, account.getId())){
            attributes.addFlashAttribute("error","削除に失敗しました");
            return "redirect:/index";
        }
        return "redirect:/index";
    }
    /**
     * Todoの検索
     * @param account (Account)
     * @param pageable (Pageable 1ページ5件)
     * @param keyword (@RequestParam 検索キーワード)
     * @param searchType (@RequestParam 検索タイプ)
     * @param model (Model)
     * @return index.html
     */
    @RequestMapping(value="/search")
    public String search(
            @AuthenticationPrincipal(expression = "account") Account account,
            @PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable,
            @RequestParam String keyword,
            @RequestParam int searchType,
            Model model) {
        Long accountId = account.getId();
        TodoPageWrapper page = service.getSearchList(accountId,keyword,pageable,searchType);
        model.addAttribute("header","Search Results by \""+keyword+"\"");
        model.addAttribute("keyword",keyword);
        model.addAttribute("searchType",searchType);
        model.addAttribute("todos",page.getDtoList());
        model.addAttribute("page",page);
        return "todo/index";
    }

    /**
     * Todo詳細画面の表示
     * @param account (Account)
     * @param comment (CommentDTO)
     * @param id (Todoのid)
     * @param attributes (RedirectAttributes)
     * @param model (Model)
     * @return show.html
     */
    @RequestMapping(value = "/show/{id}")
    public String showDetail(
            @AuthenticationPrincipal(expression = "account") Account account,
            @ModelAttribute("commentModel") CommentDTO comment,
            @PathVariable Long id,
            RedirectAttributes attributes,
            Model model) {
        TodoDTO todo = service.getTodo(id, account.getId());
        if(todo == null){
            attributes.addFlashAttribute("error","ToDoが見つかりませんでした");
            return "redirect:/index";
        }
        model.addAttribute("todo",todo);
        return "todo/show";
    }
}