rm(list = ls())
library(RMySQL)
library(ggplot2)
library(plyr)
library(scales)

con <- dbConnect(RMySQL::MySQL(), dbname = "benchmark", user = "benchmark", password = "V1ctoria", host = "haproxy")
data <- dbReadTable(con, "benchmark_result")
dbDisconnect(con)

data <- mutate(data, inserts_per_second = records / (duration_ms / 1000))

qplot(latency_ms, inserts_per_second, data=data, main="MariaDB's Galera Cluster: inserts per second vs network latency")

ggsave('galera_inserts_vs_network_latency.png', width = 6, height = 4)
