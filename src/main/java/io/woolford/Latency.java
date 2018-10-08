package io.woolford;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Latency {

    static final Logger LOG = LoggerFactory.getLogger(Latency.class);

    public void addLatency(int millisLatency) throws JSchException, IOException {

        JSch jSch = new JSch();
        jSch.setKnownHosts("/Users/alexwoolford/.ssh/known_hosts");

        Session session = jSch.getSession("root", "galera01.woolford.io", 22);
        session.setPassword("V1ctoria");
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect(5000);

        ChannelExec channelExec = (ChannelExec)session.openChannel("exec");

        InputStream in = channelExec.getInputStream();

        channelExec.setCommand(String.format("tc qdisc del dev ens192 root netem; tc qdisc add dev ens192 root netem delay %dms", millisLatency));
        channelExec.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        int index = 0;

        while ((line = reader.readLine()) != null)
        {
            LOG.info(++index + " : " + line);
        }

        int exitStatus = channelExec.getExitStatus();

        channelExec.disconnect();
        session.disconnect();

    }

}
