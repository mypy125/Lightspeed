package com.lightspeed.ipaddrcounter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;

public class UniqueIpAddressCounter {
    private static final int MAX_IP = 1 << 30;
    private static final BitSet bitSet = new BitSet(MAX_IP);

    public static void main(String[] args) {
        String filePath = "/home/mohito/IdeaProjects/lightspeed/ipv4.txt";
        try {
            int uniqueCount = checkCountUniqueIps(filePath);
            System.out.println("Count Unique Ip Addresses " + uniqueCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int checkCountUniqueIps(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int ipAddress = (int) (ipToLong(line.trim()) & 0x7FFFFFFF);
                bitSet.set(ipAddress);
            }
        }
        return bitSet.cardinality();
    }

    private static long ipToLong(String ipAddress){
        String[] toIndex = ipAddress.split("\\.");
        long result = 0;
        for (int i = 0; i < 4; i++) {
            result |= Long.parseLong(toIndex[i]) << (24 - (8 * i));
        }
        return result;
    }
}
