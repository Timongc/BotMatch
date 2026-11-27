package workers;

import entity.Bot;

public class BotCycle {

    public static void cycle(Bot bot) {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(bot.toString() + " :--: Bot cycle in THREAD test");

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread " + Thread.currentThread().getName() + " for bot cycle stopped");
        }
    }
}
