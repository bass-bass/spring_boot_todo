# Spring Securityでログインユーザーを取得する

## ユーザー情報クラス
```AccountDetail.java
public class AccountDetail extends User{
    // 追記
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
        // 追記
        this.account = account;
    }
}
```
* `Account`フィールドの追加
* コンストラクタで`Account`情報をセット
* [参考](https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/mvc.html#mvc-authentication-principal)

## ログインユーザー取得
* 取得したいメソッドの引数に`AuthenticationPrincipal`を指定
* `@AuthenticationPrincipal(expression = "account") Account account`
* ユーザー情報クラスの`account`フィールドを参照して取得

# Thymeleafでの取得

## 依存関係の追加
```pom.xml
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-springsecurity5</artifactId>
</dependency>
```

## Thymeleaf

* htmlタグに`xmlns:sec="http://www.thymeleaf.org/extras/spring-security"`を追加
* 表示したい箇所で`sec:authentication`を使用
* ex) `<p sec:authentication="name"></p>`