package com.example.todo.action;

import com.example.todo.entity.Account;
import com.example.todo.model.TodoDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import com.example.todo.model.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private TodoService todoService;

    /**
     * コメント新規投稿・更新
     * 投稿完了後TodoDTOをviewへ返却
     * @param account (Account)
     * @param comment (CommentDTO)
     * @param result (BindingResult)
     * @param model (Model)
     * @return show.html
     */
    @RequestMapping(value = "/show/{id}",method = RequestMethod.POST)
    public String comment(
            @AuthenticationPrincipal(expression = "account") Account account,
            @ModelAttribute("commentModel") @Validated CommentDTO comment,
            BindingResult result,
            Model model) {
        if(result.hasErrors()){
            TodoDTO todo = todoService.getTodo(comment.getTodoId(), account.getId());
            model.addAttribute("todo",todo);
            return "todo/show";
        }
        commentService.saveComment(comment);
        TodoDTO todo = todoService.getTodo(comment.getTodoId(), account.getId());
        model.addAttribute("todo",todo);
        return "todo/show";
    }

    /**
     * コメントの削除
     * @param commentId ajaxで取得されたコメントID
     * @return commentId json形式で返却
     */
    @RequestMapping(value = "/comment/delete",method = RequestMethod.POST)
    @ResponseBody
    public Long delete(@RequestParam("commentId") Long commentId){
        commentService.deleteComment(commentId);
        return commentId;
    }

}