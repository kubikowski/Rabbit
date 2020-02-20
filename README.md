# Rabbit
 RabbitMQ Playground

 Listing Queues and their current # of messages:
 ```
 sudo rabbitmqctl list_queues
 rabbitmqctl.bat list_queues
 ```
 
 Listing Queue names, their # of enqueued messages, and # of unACKed messages:
 ```
 sudo rabbitmqctl list_queues name messages_ready messages_unacknowledged
 rabbitmqctl.bat list_queues name messages_ready messages_unacknowledged
 ```
