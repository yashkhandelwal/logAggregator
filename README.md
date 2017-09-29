# logAggregator
To Run Log Aggregator, copy log.properties files present in resources folder to /mnt1/config/log.properties path of local
machine. Run Main Method of LogSimulator class present in runner package. It generates log Lines in five files present in
/mnt1/config directory. Then, Run Main Method of LogRunner class present in same package which runs five producers which
reads log lines from above five log files and push them into in-memory queues and five consumers which reads from these
queues and outputs logs to centralised LogFile /mnt1/config/outputLogFile.