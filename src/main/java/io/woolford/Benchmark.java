package io.woolford;

import com.jcraft.jsch.JSchException;
import io.woolford.mapper.BenchmarkMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;

import java.util.UUID;

public class Benchmark {

    static final Logger LOG = LoggerFactory.getLogger(Benchmark.class);

    Benchmark() { }

    void runBenchmark(int records, int latencyMs) throws IOException, JSchException {

        Latency latency = new Latency();
        latency.addLatency(latencyMs);

        long startTime = System.currentTimeMillis();

        SqlSessionFactory factory = this.getSessionFactory();
        SqlSession session = factory.openSession();

        BenchmarkMapper mapper = session.getMapper(BenchmarkMapper.class);

        for (long num = 0; num < records; num++){
            BenchmarkDataRecord benchmarkDataRecord = new BenchmarkDataRecord();
            benchmarkDataRecord.setIterator(num);

            mapper.insertBenchmarkDataRecord(benchmarkDataRecord);
            session.commit();

        }

        mapper.deleteBenchmarkDataRecords();
        session.commit();

        long endTime = System.currentTimeMillis();

        long durationMs = endTime - startTime;

        BenchmarkRecord benchmarkRecord = new BenchmarkRecord();
        benchmarkRecord.setUuid(UUID.randomUUID().toString());
        benchmarkRecord.setRecords(records);
        benchmarkRecord.setDurationMs(durationMs);
        benchmarkRecord.setLatencyMs(latencyMs);

        mapper.insertBenchmarkRecord(benchmarkRecord);
        session.commit();

        session.close();

    }

    private SqlSessionFactory getSessionFactory() throws IOException {
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        return builder.build(reader);
    }

}
