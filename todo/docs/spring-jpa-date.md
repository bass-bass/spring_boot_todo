# Spring Data JPAでレコードの作成、更新日時を設定

# MySQLの設定確認
* todos table
```
mysql> desc todos;
+--------------+--------------+------+-----+---------+----------------+
| Field        | Type         | Null | Key | Default | Extra          |
+--------------+--------------+------+-----+---------+----------------+
| id           | int          | NO   | PRI | NULL    | auto_increment |
| title        | varchar(100) | NO   |     | NULL    |                |
| content      | text         | NO   |     | NULL    |                |
| created_date | datetime     | NO   |     | NULL    |                |
| updated_date | datetime     | NO   |     | NULL    |                |
+--------------+--------------+------+-----+---------+----------------+
```
* time_zone
```
mysql> show variables like '%time_zone%';

+------------------+------------+
| Variable_name    | Value      |
+------------------+------------+
| system_time_zone | JST        |
| time_zone        | SYSTEM     |
+------------------+------------+
```

# Entityクラスの設定

```Todo.java
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name="todos")
public class Todo {

    //一部省略
    
    @Column(name = "created_date",updatable = false)
    @CreatedDate
    private Date createdDate;

    @Column(name = "updated_date")
    @LastModifiedDate
    private Date updatedDate;
}
```
* `@EntityListeners(AuditingEntityListener.class)`：レコードの作成・更新時の情報設定を有効にする
* `@CreatedDate`：レコードの作成日時がフィールドに自動的に設定される
* `updatable = false`：こちら指定することで更新時にnullが入ってしまうのを防ぐ
* `@LastModifiedDate`：レコードの更新日時がフィールドに自動的に設定される

# Spring Bootの設定

  ```TodoApplication.java
  @SpringBootApplication
  @EnableJpaAuditing
  public class TodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}

	@PostConstruct
	public void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("JST"));
	}
  }
  ```
* `@EnableJpaAuditing`：レコードの作成、更新時の情報設定を有効にする
* `@PostConstruct`：Bean生成後の初期化
* `TimeZone.setDefault(TimeZone.getTimeZone("JST"));`spring bootのタイムゾーンをJSTに設定（デフォルトはUCT）
* アプリケーション全体としてのタイムゾーンを指定しているため、タイムゾーンの異なる複数のデータベースを扱う場合などは注意が必要

# application.yml
```application.yml
spring:
  datasource:
    url: jdbc:mysql://[ホスト名]:[ポート]/[データベース名]?connectionTimeZone=SERVER
    username:
    password: 
    driverClassName: com.mysql.cj.jdbc.Driver
```
* `connectionTimeZone=SERVER`：日付登録時に、mysql側で設定されたタイムゾーンを指定
* MySQL Connector/Jのバージョンに注意
* 8.0.22以前だと`serverTimeZone=JST`で指定可能（[参考](https://dev.mysql.com/doc/relnotes/connector-j/8.0/en/news-8-0-23.html)）

# 画面側で表示
* thymeleaf
    * そのまま表示
        * `th:text="todo.createdDate"`
        * ex) 2022-07-04 19:33:12
    * フォーマット指定して表示
        * `th:text="${#dates.format(todo.updatedDate,'yyyy/MM/dd HH:mm')}"`
        * ex) 2022/07/04 19:33