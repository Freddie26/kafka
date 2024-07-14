### Добавить права доступа для `bob` и `alice` 
docker exec -ti kraft-kraft-1 /usr/bin/kafka-acls --bootstrap-server localhost:9093 --add --allow-principal User:bob --operation Write --topic test-topic --command-config /tmp/admin-sasl.properties
docker exec -ti kraft-kraft-1 /usr/bin/kafka-acls --bootstrap-server localhost:9093 --add --allow-principal User:alice --operation Read --group '*' --topic test-topic --command-config /tmp/admin-sasl.properties

### Просмотреть права доступа всех пользователей
docker exec -ti kraft-kraft-1 /usr/bin/kafka-acls --bootstrap-server localhost:9093 --list --command-config /tmp/admin-sasl.properties

### Получение списка топиков для каждого пользователя
docker exec -ti kraft-kraft-1 /usr/bin/kafka-topics --list --bootstrap-server localhost:9093 --command-config /tmp/bob-sasl.properties
docker exec -ti kraft-kraft-1 /usr/bin/kafka-topics --list --bootstrap-server localhost:9093 --command-config /tmp/alice-sasl.properties
docker exec -ti kraft-kraft-1 /usr/bin/kafka-topics --list --bootstrap-server localhost:9093 --command-config /tmp/alex-sasl.properties

### Запись сообщений в топик для каждого пользователя
docker exec -ti kraft-kraft-1 /usr/bin/kafka-console-producer --bootstrap-server localhost:9093 --topic test-topic --producer.config /tmp/bob-sasl.properties
docker exec -ti kraft-kraft-1 /usr/bin/kafka-console-producer --bootstrap-server localhost:9093 --topic test-topic --producer.config /tmp/alice-sasl.properties
docker exec -ti kraft-kraft-1 /usr/bin/kafka-console-producer --bootstrap-server localhost:9093 --topic test-topic --producer.config /tmp/alex-sasl.properties

### Чтение сообщений из топика для каждого пользователя
docker exec -ti kraft-kraft-1 /usr/bin/kafka-console-consumer --bootstrap-server localhost:9093 --topic test-topic --consumer.config /tmp/bob-sasl.properties --from-beginning --group bob-group
docker exec -ti kraft-kraft-1 /usr/bin/kafka-console-consumer --bootstrap-server localhost:9093 --topic test-topic --consumer.config /tmp/alice-sasl.properties --from-beginning --group alice-group
docker exec -ti kraft-kraft-1 /usr/bin/kafka-console-consumer --bootstrap-server localhost:9093 --topic test-topic --consumer.config /tmp/alex-sasl.properties --from-beginning --group alex-group