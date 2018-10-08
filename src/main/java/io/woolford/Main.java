package io.woolford;

import com.jcraft.jsch.JSchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException, JSchException, InterruptedException {

        Benchmark benchmark = new Benchmark();

        ArrayList<Integer> latencyMsArrayList = new ArrayList<Integer>();

        Integer latencyMs = 0 ;
        while ( latencyMs <= 1000 )
        {
            if (latencyMs <= 20){
                if (latencyMs % 1 == 0){
                    latencyMsArrayList.add(latencyMs);
                }
            } else if (latencyMs <= 50) {
                if (latencyMs % 5 == 0){
                    latencyMsArrayList.add(latencyMs);
                }
            } else if (latencyMs <= 100) {
                if (latencyMs % 10 == 0){
                    latencyMsArrayList.add(latencyMs);
                }
            } else {
                if (latencyMs % 100 == 0){
                    latencyMsArrayList.add(latencyMs);
                }
            }

            latencyMs++;
        }

        for (Integer latencyMsScenario : latencyMsArrayList) {
            benchmark.runBenchmark(500, latencyMsScenario);
            Thread.sleep(1000L);
        }

    }

}
