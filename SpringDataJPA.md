# Spring Data JPAについて

## Spring Data JPAとは
* [公式ドキュメント](https://spring.pleiades.io/projects/spring-data-jpa)

## Spring Data JPAの使い方
* [公式ドキュメント](https://spring.pleiades.io/spring-data/jpa/docs/current/reference/html/#reference)

### 基本的な説明
* 実装の例
    * [Entity](./todo/src/main/java/com/example/todo/entity)  
        ここにテーブル,カラムの詳細を記述します。
    * [Repository](./todo/src/main/java/com/example/todo/repository)  
        ここにテーブルを操作するためのSQLを記述します。
    * [設定ファイル](./todo/src/main/resources/application.yml)  
        ここに接続先のDB名を設定します.

この 2つのクラスと設定ファイルだけで特定DBのテーブル操作の記述が完結します。

### 具体的な実装パターン
* 参考用記事
    * [基本操作 実装方法](https://kanchi0914.hatenablog.com/entry/2020/01/19/225314)

#### テーブルにカラムを追加したい場合
対象のテーブルの Entity のクラスに 追加したいカラムを実装します

* 実装の例
    * [Entity](./todo/src/main/java/com/example/todo/entity)  
        ```
        /*
         * 1. TODO_APP テーブルに add_column という名前の Type(TEXT) のカラムを追加する際の例
         *  Entity の TodoApp クラスに 以下のように新規にカラムを追加します。
         */
        //@Column(name = "add_column")
        //private String addColumn;
        ```
#### 新規にテーブルを追加したい場合
新規テーブルに対応する Entity, Repositoty のクラスを 1つずつ新規に実装します。

* 実装の例
    * [Entity](./todo/src/main/java/com/example/todo/entity)
    * [Repository](./todo/src/main/java/com/example/todo/repository)

#### SQLを追加したいとき
JPQLの記述方法に沿って Repository クラスに実装します.
[公式ドキュメント](https://spring.pleiades.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query)

* 実装の例
    * [Repository](./todo/src/main/java/com/example/todo/repository)  
        ```
        /*
         * 3. id が一致するレコードの title を取得するための JPQL の例
         */
        //@Query("SELECT a.title FROM TodoApp a WHERE a.id = ?1")
        //String findTitleById(String id);
        ```
