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