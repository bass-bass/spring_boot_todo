package com.example.todo.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {
    private Long id;
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9_\\-.]+", message = "半角英数字とハイフン,ピリオド,アンダースコアのみ使用可能です")
    private String name;
    @NotBlank
    private String password;
}
