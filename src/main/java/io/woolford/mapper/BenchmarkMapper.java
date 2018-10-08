package io.woolford.mapper;

import io.woolford.BenchmarkDataRecord;
import io.woolford.BenchmarkRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
public interface BenchmarkMapper {

    @Insert("INSERT INTO benchmark (iterator) VALUES( #{iterator} )")
    void insertBenchmarkDataRecord(BenchmarkDataRecord benchmarkDataRecord);

    @Delete("DELETE FROM benchmark")
    void deleteBenchmarkDataRecords();

    @Insert("INSERT INTO benchmark_result (uuid, records, duration_ms, latency_ms) VALUES ( #{uuid}, #{records}, #{durationMs}, #{latencyMs} )")
    void insertBenchmarkRecord(BenchmarkRecord benchmarkRecord);

}