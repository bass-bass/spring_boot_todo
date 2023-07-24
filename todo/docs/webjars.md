# WebjarsからBootstrapとjQueryを使用する

# 依存関係の追加
```pom.xml
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>bootstrap</artifactId>
	<version>5.0.2</version>
</dependency>
<dependency>
	<groupId>org.webjars</groupId>
	<artifactId>jquery</artifactId>
	<version>3.4.1</version>
</dependency>
```
* `version`には使用するBootstrap,jQueryのバージョンを指定

# HTMLファイルの設定
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" th:href="@{/webjars/bootstrap/5.0.2/css/bootstrap.min.css}" />
</head>
<body>

<!-- jQuery -->
<script th:src="@{/webjars/jquery/3.4.1/jquery.min.js}"></script>
<!-- Bootstrap JS -->
<script th:src="@{/webjars/bootstrap/5.0.2/js/bootstrap.bundle.min.js}"></script></body>
</html>
```
* 指定したバージョンで`webjars`からインポート