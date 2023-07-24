$(function (){
    $('.edit-comment').on('click', function(e){
        const commentId = $(e.currentTarget).data('id');
        const comment = $(e.currentTarget).data('comment');
        const todoId = $(e.currentTarget).data('todo');
        $('#commentEditModal').find('input[name="id"]').val(commentId);
        $('#commentEditModal').find('input[name="comment"]').val(comment);
        $('#commentEditModal').find('input[name="todoId"]').val(todoId);
    });
});