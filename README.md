# Агрегатор вакансий.

Описание.

Система запускается по расписанию - раз в минуту. Период запуска указывается в настройках - app.properties. Первый сайт будет career.habr.com. Работаем с разделом https://career.habr.com/vacancies/java_developer. Программа должна считывать все вакансии c первых 5 страниц относящиеся к Java и записывать их в базу.

Расширение.

1. В проект можно добавить новые сайты без изменения кода.
2. В проекте можно сделать параллельный парсинг сайтов.