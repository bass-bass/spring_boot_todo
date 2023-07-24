# Spring Bootでajaxを使うサンプル

# Thymeleafの記述
`meta`タグにCSRF対策トークン埋め込み<br>
Spring Security使用している場合は設定しないと動かない
```html
<meta name="_csrf" th:content="${_csrf.token}"/>
<meta name="_csrf_header" th:content="${_csrf.headerName}"/>
```
必要なjsファイルとjQuery読み込み
```html
<script th:src="@{/webjars/jquery/3.4.1/jquery.min.js}"></script>
<script th:src="@{/js/sample.js}"></script>
```
# ajax処理記述
resources/static/js/sample.js

```sample.js
$(function (){
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
    $('.delete-comment').on('click', function(e){
        const commentId = $(e.currentTarget).data('id');
        $.ajax({
            url: '/comment/delete',
            method: 'POST',
            data: {
                'commentId' : commentId,
            },
            dataType: 'json',
            success: function(data){
                $('.card[data-id="'+data+'"]').remove();
            },
            error: function(){ alert("コメントの削除に失敗しました"); },
        })
    });
}); 
```
トークンをリクエストヘッダにセット
```javascript
let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");
$(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, token);
});
```
コメントコントローラ側
```CommentController.java
@RequestMapping(value = "/comment/delete",method = RequestMethod.POST)
@ResponseBody
public Long delete(@RequestParam("commentId") Long commentId){
    commentService.deleteComment(commentId);
    return commentId;
}
```
* `POST`で`commentId`受け取り
* `@RequestBody`で`commentId`をjsonで返却