package com.patrikv.simulator;

import java.util.Arrays;

/**
 * Created by patrikv on 06/03/16.
 */
public class Simulator {

    public static void main(String[] args) {

        Client client = new Client();
        Thread[] threads = {
                new Thread(new Driver(client, "Ty Tessman", 5000, 12345l)),
                new Thread(new Driver(client, "David Ronnefalk", 5000, 12321l))
        };
        Arrays.stream(threads).forEach(Thread::start);

        while (true) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
