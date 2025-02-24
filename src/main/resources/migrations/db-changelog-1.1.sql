INSERT INTO hotel (id, name, description, city, address, rating)
VALUES (1, 'Гранд Отель Москва', 'Роскошный 5-звездочный отель в центре Москвы', 'MOSCOW', 'ул. Тверская, 10', 'NO_RATING'),
       (2, 'Питер Палас', 'Уютный отель с видом на Неву', 'SAINT_PETERSBURG', 'Невский проспект, 45', 'NO_RATING'),
       (3, 'Казань Инн', 'Современный отель в Казани', 'KAZAN', 'ул. Баумана, 88', 'NO_RATING'),
       (4, 'Сибирь Резорт', 'Горный курорт в сердце Сибири', 'NOVOSIBIRSK', 'ул. Ленина, 15', 'NO_RATING'),
       (5, 'Сочи Спа Отель', 'Лучший курортный отель на Черном море', 'SOCHI', 'ул. Пляжная, 5', 'NO_RATING');

INSERT INTO room (id, name, capacity, price, hotel_id)
VALUES (1, 'Стандарт', 2, 5000, 1),
       (2, 'Люкс', 3, 12000, 1),
       (3, 'Семейный', 4, 8000, 2),
       (4, 'Эконом', 2, 3000, 2),
       (5, 'Студия', 2, 6000, 3),
       (6, 'Президентский', 5, 20000, 3),
       (7, 'Делюкс', 2, 10000, 4),
       (8, 'Хостел', 1, 1500, 4),
       (9, 'Апартаменты', 4, 11000, 5),
       (10, 'Пентхаус', 6, 25000, 5);

INSERT INTO "user" (id, username, password)
VALUES (1, 'ivan_petrov', 'ivan'),
       (2, 'anna_ivanova', 'anna'),
       (3, 'sergey_smirnov',  'sergey'),
       (4, 'elena_orlova', 'elena'),
       (5, 'dmitry_kuznetsov', 'dmitry');
