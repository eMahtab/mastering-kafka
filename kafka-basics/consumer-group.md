# Consumer Group


The benefit of leveraging a Kafka consumer group is that the consumers within the group will coordinate to split the work of reading from different partitions.


!["Kafka Consumer Group"](images/consumer-group/consumer-group.jpg)


> It is important to note that each topic partition is only assigned to one consumer within a consumer group, but a consumer from a consumer group can be assigned multiple partitions.

___

## More Consumers than Partitions 

If there are more consumers than the number of partitions of a topic, then some consumers will remain inactive as shown below. Usually, we have as many consumers in a consumer group as the number of partitions. If we want more consumers for higher throughput, we should create more partitions while creating the topic. Otherwise, some of the consumers may remain inactive.

!["More Consumers than Partitions"](images/consumer-group/more-consumers-than-partitions.jpg)
