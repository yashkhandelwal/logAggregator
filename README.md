# logAggregator
To Run Log Aggregator, copy log.properties files present in resources folder to /mnt1/config/log.properties path
Run Main Method Of LogSimulator which generates log Lines in 5 files present in /mnt1/config folder
Run Main Method Of LogRunner which runs 5 producers which reads from above 5 log files and push logs into in-memory
queues and 5 consumers which reads from these queue and outputs log to centralised LogFile /mnt1/config/outputLogFile