# 環境構築手順

## 1. リポジトリをクローン
適当なディレクトリで下記コマンド  
`git clone git@github.com:bass-bass/spring_boot_todo.git`

## 2. Dockerの起動
クローンしたディレクトリの直下に移動して  
`cd spring_boot_todo`  
下記コマンドを実行する  
`docker-compose up -d --build`  
appコンテナとdbコンテナが起動

## 3. マイグレーションの実行
1) appコンテナに入る  
`docker exec -it todo_app bash`
2) baselineコマンドでバージョン管理テーブルを作成  
`./mvnw flyway:baseline`  
3) infoコマンドでflywayのマイグレーション管理用テーブルの内容を確認  
`./mvnw flyway:info`  
   * ${このリポジトリの絶対パス}/todo/src/main/resources/db/migration/ 配下の Versioned Migrationファイル が表示されます.
   * 実行されていないため、 State が Pending になっています
4) マイグレーション実行  
`./mvnw flyway:migrate`
   * State が Pending のVersioned Migrationファイル が実行されます.
   * 再度 ./mvnw flyway:info コマンドを実行することで State が Success に変化していることを確認することができます.
   * 実際にdbコンテナに入ると対象のテーブルが作成,レコードが追加されていることが確認できます.

## 4. Spring Bootの起動
appコンテナに入った状態で下記コマンド実行  
`./mvnw spring-boot:run`
1) spring bootが起動します
2) ローカルサーバーへアクセス  
  `http://localhost:8000`
3) spring bootの停止  
  `ctrl + C`
