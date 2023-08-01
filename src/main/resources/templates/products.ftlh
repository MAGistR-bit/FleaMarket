<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>FleaMarket</title>
</head>
<body>
<h1>FleaMarket</h1>
<hr>
<#if user.email??>
    <h3>Имя пользователя: <i>${user.name}</i></h3>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Выйти"/>
    </form>
<#else>
    <a href="/login">Войти</a>
</#if>
<h4> Товары со всего мира </h4>
<form action="/" method="get">
    Поиск по названию объявления: <input type="text" name="title">
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
<#-- Отображаем форму, если присутствует e-mail-->
<#if user.email??>
    <hr>
    <h3>Создать новый товар </h3>
<#-- Используем multipart -->
    <form action="/product/create" method="post" enctype="multipart/form-data">
        Название объявления: <input type="text" name="title"/><br>
        Описание объявления: <input type="text" name="description"/><br>
        Цена: <input type="number" name="price"/><br>
        Город: <input type="text" name="city"/><br>
        <#-- Добавление фотографий  -->
        Первая фотография (обязательно): <input type="file" name="file1"/><br><br>
        Вторая фотография: <input type="file" name="file2"/><br><br>
        Третья фотография: <input type="file" name="file3"/><br><br>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Добавить товар"/>
    </form>
</#if>
</body>
</html>