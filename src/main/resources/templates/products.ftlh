<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>FleaMarket</title>
</head>
<body>
<h1>FleaMarket | <a href="/login">Войти</a></h1><hr>
<form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Выйти"/>
</form>
<h2> Товары со всего мира </h2>
<form action="/" method="get">
    Поиск по названию объявления: <input type="text" name="title"><br>
    <input type="submit" value="Поиск"/>
</form>
<#list products as product>
    <div>
        <p><b>${product.title}</b> ${product.price} руб. | <a href="/product/${product.id}"> Подробнее...</a></p>
    </div>
<#-- Условный оператор -->
    <#else>
    <h3> Товары отсутствуют </h3>
</#list>
<hr>
<h3>Создать новый товар </h3>
<#-- Используем multipart -->
<form action="/product/create" method="post" enctype="multipart/form-data">
    Название объявления: <input type="text" name="title"/><br>
    Описание объявления: <input type="text" name="description"/><br>
    Цена: <input type="number" name="price"/><br>
    Город: <input type="text" name="city"/><br>
    Имя: <input type="text" name="author"/><br>
<#-- Добавление фотографий  -->
    Первая фотография (обязательно): <input type="file" name="file1"/><br><br>
    Вторая фотография: <input type="file" name="file2"/><br><br>
    Третья фотография: <input type="file" name="file3"/><br><br>
    <input type="submit" value="Добавить товар"/>
</form>
</body>
</html>