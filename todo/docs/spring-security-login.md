# Spring Securityでログイン機能を作成する

# 準備
使用するEntity
```Account.java
@Entity
@Table(name = "accounts")
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
}
```

pom.xmlに次の依存関係が追加されている
```pom.xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

# 認証の設定
認証の適用する範囲やどのように認証を行うか設定する
```WebSecurityConfig.java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private AccountDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated() // 全てのリクエストに認証設定
                .and()
                .formLogin() // フォームログインで認証する
                .loginPage("/login") // ログインページのURL
                .loginProcessingUrl("/login") // ログイン処理を行うURL
                .usernameParameter("name") // 認証にnameとpasswordを使用
                .passwordParameter("password")
                .defaultSuccessUrl("/index") // ログイン後に飛ぶURL
                .failureUrl("/login?error") // ログイン失敗時に飛ぶURL
                .permitAll() // /loginページにアクセス許可
                .and()
                .logout()
                .logoutUrl("/logout") // ログアウト処理を行うURL
                .logoutSuccessUrl("/login") // ログアウト後に飛ぶURL
                .permitAll();
    }
}
```
* `@EnableWebSecurity`：Spring Securityを有効化
* `WebSecurityConfigurerAdapter`を継承

# 認証サービスの実装
### ユーザー情報クラス
ユーザー情報を保持するクラス
```AccountDetail.java
public class AccountDetail extends User{
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
    }
}
```
* `User`クラスを継承
* `User`クラスは`UserDetails`インターフェイスを実装済み
* Account エンティティーのname,passwordをユーザー情報として保持

### ユーザー情報サービスクラス
ログインフォームで入力されたnameからユーザーが存在するか検索
```AccountDetailsService.java
@Service
public class AccountDetailsService implements UserDetailsService {
    @Autowired
    private AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Account account = repository.findByName(name);
        if (account == null) {
            throw new UsernameNotFoundException("User " + name + " not found");
        }
        return new AccountDetail(account);
    }
}
```
* `UserDetailsService`を継承
* `loadUserByUsername`の引数に入力されたnameが渡される
* nameに一致する`Account`が存在するかチェック
```AccountRepository.java
@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    @Query("SELECT a FROM Account a WHERE a.name = :name")
    Account findByName(String name);
}
```
* 存在した場合はユーザー情報のオブジェクトを生成

ここではnameだけで認証を行なっているので、`WebSecurityConfig`で`AuthenticationManagerBuilder`を使用。
```WebSecurityConfig.java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private AccountDetailsService userDetailsService;

    // 略

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
```
* `passwordEncoder`：入力されたパスワードをハッシュ化
* DBのパスワードと一致するか確認
  これによってnameとpasswordで認証

# ログインフォームの作成

自作ログインフォームの作成

```login.html
<label th:if="${error}" th:text="${error}" class="alert alert-danger text-center"></label>
<form class="mt-sm-4" method="post" th:action="@{/login}" aria-label="Login">
  <div class="mb-3 input-group-lg">
    <label>Name</label>
    <input class="form-control" type="text" name="name" required>
  </div>
  <div class="mb-3 input-group-lg">
    <label>Password</label>
    <input class="form-control" type="password" name="password" required>
  </div>
  <div class="d-grid">
    <button type="submit" class="btn btn-lg btn-primary">Login</button>
  </div>
</form>
```
フォームの表示
```AccountController.java
@RequestMapping(value = "/login")
public String showLoginForm(@RequestParam(name = "error",required = false) String error,Model model){
    if(error!=null){
        model.addAttribute("error","ログイン名またはパスワードが間違っています");
    }
    return "login";
}
```
* `/login?error`：ログイン失敗時にエラーメッセージを表示

# ログアウト
```html
<form class="d-flex" method="post" th:action="@{/logout}">
  <button class="btn btn-outline-secondary" type="submit">Logout</button>
</form>
```
* `POST`メソッドで`/logout`へ
* Controllerの設定は不要