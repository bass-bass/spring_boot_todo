# 概要
Spring BootによるTODOアプリ

[TODOアプリ ローカル環境構築手順](todo/README_build.md)

[TODOアプリ 機能一覧](todo/README.md)

# 開発環境
- IDE IntelliJ
- java 11
- maven 3.6.3
- mysql  Ver 8.0.23 for osx10.14 on x86_64 (Homebrew)

# flyway を使用したDBマイグレーション管理

## flyway とは
* データベースマイグレーションツール  
  データベースの状態をバージョン管理します

## 説明
1. 設定
    1. 場所
        1. [pom.xml](./todo/pom.xml)
            ```
            <configuration>
                <url>jdbc:mysql://localhost/...</url>
                <user>root</user>
                <password></password>
            </configuration>
            ```
        1. ローカルの場合 -> `${このリポジトリの絶対パス}/pom.xml`
    1. 内容: 対象のデータベース, ユーザとパスワード
1. 管理するVersioned Migrationファイル  
    1. 場所
        1. [Versioned Migrationファイル](./todo/src/main/resources/db/migration/)
        2. ローカルの場合 -> `${このリポジトリの絶対パス}/todo/src/main/resources/db/migration/` 配下のファイル
    1. 内容: 管理したいSQLが記述されている

## マイグレーションの適用例
 ※ 実際に以下 1,2 を実行してください

### 1. 確認用DBの用意
```
// 起動
mysql.server start

// ログイン (flyway.confに設定されているユーザ名)
mysql -uroot #root実行

// データベース作成 (flyway.confに設定されているDB名)
CREATE DATABASE ...;
```

### 2. マイグレーションの適用
1. ローカルへコピーしたこのリポジトリの配下に移動  
    実行コマンド: `cd ${このリポジトリの絶対パス}`
1. baselineコマンドでバージョン管理テーブルを作成  
    実行コマンド: `mvn flyway:baseline`
1. infoコマンドでflywayのマイグレーション管理用テーブルの内容を確認  
    実行コマンド: `mvn flyway:info`
    1. `${このリポジトリの絶対パス}/todo/src/main/resources/db/migration/` 配下の Versioned Migrationファイル が表示されています.
    1. 実行されていないため、 State が Pending になっています
1. migrateコマンドでマイグレーションを実行する  
    実行コマンド: `mvn flyway:migrate`
    1. State が Pending のVersioned Migrationファイル が実行されます.
    1. 再度 `mvn flyway:info` コマンドを実行することで  State が Success に変化していることを確認することができます.
    1. 実際にDBに入ると対象のテーブルが作成,レコードが追加されていることが確認できます.

### 参考資料
1. [プラグイン追加から初回マイグレーション実行まで](https://blog.honjala.net/entry/2016/03/07/015951)
1. [コマンドメモ](https://www.monotalk.xyz/blog/flyway%E3%81%AEmaven%E3%82%B3%E3%83%9E%E3%83%B3%E3%83%89%E3%83%A1%E3%83%A2%E8%87%AA%E5%88%86%E7%94%A8/)
1. [公式ドキュメント](https://flywaydb.org/documentation/)


# Spring Data JPAについて
[実装方法、Entity,Repository についての説明](./SpringDataJPA.md)

# ライブラリの追加方法について
 チーム開発では外部ライブラリの導入が可能です。
1. 追加方法  
   例: `ログインページ実装` の際、 `SpringSecurity` というライブラリを使用する場合  
    1. [pom.xml](./todo/pom.xml) に以下を追加します。 
       [参考リンク](https://qiita.com/shibuchaaaan/items/8c54152fe15e58678504)  
        ```
         <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-security</artifactId>
         </dependency> 
        ```
