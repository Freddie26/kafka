### Цель:
Научиться самостоятельно разрабатывать и запускать приложения с транзакциями


### Пошаговая инструкция выполнения ДЗ:
1. Запустить Kafka
2. Создать два топика: topic1 и topic2
3. Разработать приложение, которое:
   - открывает транзакцию
   - отправляет по 5 сообщений в каждый топик
   - подтверждает транзакцию
   - открывает другую транзакцию
   - отправляет по 2 сообщения в каждый топик
   - отменяет транзакцию
4. Разработать приложение, которое будет читать сообщения из топиков topic1 и topic2 так, чтобы сообщения из подтверждённой транзакции были выведены, а из неподтверждённой - нет
