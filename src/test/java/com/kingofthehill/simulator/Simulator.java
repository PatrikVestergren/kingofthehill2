package com.kingofthehill.simulator;

import java.util.Arrays;

/**
 * Created by patrikv on 06/03/16.
 */
public class Simulator {

    public static void main(String[] args) {

        RestClient client = new RestClient();
        Thread[] threads = {
                new Thread(new Driver(client, "Ty Tessman", 4700, 12345l)),
                new Thread(new Driver(client, "David Ronnefalk", 4500, 12321l)),
                new Thread(new Driver(client, "Robert Battle", 6000, 12322l)),
                new Thread(new Driver(client, "Jared Teboo", 4900, 12333l)),
                new Thread(new Driver(client, "Elliot Boots", 5005, 12327l))
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
