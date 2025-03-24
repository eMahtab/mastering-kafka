# Kafka Producer Message Acknowledgement

Kafka producers only write data to the current leader broker for a partition. **A Kafka producer configuration parameter called acks** which is the number of brokers who need to acknowledge receiving the message before it is considered a successful write.


