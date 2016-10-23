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
                new Thread(new Driver(client, "Ryan Maifield", 4950, 3339877l)),
                new Thread(new Driver(client, "Dakotah Phend", 5500, 5553333l)),
                new Thread(new Driver(client, "Ryan Lutz", 5250, 255566l)),
                new Thread(new Driver(client, "Kyle McBride", 5100, 11111198l)),
                new Thread(new Driver(client, "Spencer Ripkin", 5650, 445566l)),
                new Thread(new Driver(client, "Cody King", 5000, 767766l)),
                new Thread(new Driver(client, "Davide Ongaro", 5800, 9899988l)),
                new Thread(new Driver(client, "Renaud Savoya", 4870, 2312320l)),
                new Thread(new Driver(client, "Adam Drake", 5300, 545455l)),
                new Thread(new Driver(client, "Cole Ogden", 5600, 222111l)),
                new Thread(new Driver(client, "Marco Baruffolo", 5640, 2222333l)),
                new Thread(new Driver(client, "Joe Bornhorst", 5670, 111333l)),
                new Thread(new Driver(client, "Neil Cragg", 5900, 1122112l)),
                new Thread(new Driver(client, "Mike Truhe", 6000, 111222l)),
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
