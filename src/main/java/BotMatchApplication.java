import entity.Bot;
import entity.MatchRequest;
import entity.MatchRequestStatus;
import workers.BotCycle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BotMatchApplication {
    public static void main(String[] args) {

        List<Bot> bots = initializeBots();

        ///
        ExecutorService executor = Executors.newFixedThreadPool(bots.size());

        List<Future<?>> threads = new ArrayList<>();

        for (Bot bot : bots) {
            Future<?> future = executor.submit(() -> BotCycle.cycle(bot));

            threads.add(future);
        }
        ///

/*        int a=4;
        Bot botCandidate;
        while (a>0) {
            a--;
            for (Bot bot : bots) {
                int candidateIndex = bot.findCandidate(bots);
                botCandidate = bots.get(candidateIndex);

                System.out.printf("%s is analyzing %s %n",
                        bot.toString(),
                        botCandidate.toString());

                if (bot.analyzeBeautyWhetherSendRequest(botCandidate)) {
                    bots.get(candidateIndex).addRequest(
                            bot.sendRequest(botCandidate));
                    System.out.printf("%s sent match-request to %s %n",
                            bot.toString(),
                            botCandidate.toString());
                    String pickupLine = bot.generateResponseToTheText(String.format("""
                            You are on a dating program and you found someone you would like to talk with - write first message.
                            Your personality: %s
                            Person's personality: %s
                            
                                        Rules:
                                        - Speak naturally, like a human.
                                        - Use simple everyday language.
                                        - Keep responses relatively short.
                            """, bot.getSummaryPersonality(), botCandidate.getSummaryPersonality())).getFirst();
                    System.out.printf("%s texted %s with message: %s %n",
                            bot.toString(),
                            botCandidate.toString(),
                            pickupLine);
                }
                else {
                    System.out.printf("%s don't like sending match-request to %s %n",
                            bot.toString(),
                            botCandidate.toString());
                }

                if (!bot.getReceivedRequestsWithSentStatus().isEmpty()) {
                    for (MatchRequest request : bot.getReceivedRequestsWithSentStatus()) {
                        if (bot.acceptedBeautyOfCandidate(request.sender)) {
                            request.requestStatus = MatchRequestStatus.ACCEPTED;
                            System.out.printf("%s accepted match-request from %s %n",
                                    request.recipient.toString(),
                                    request.sender.toString());
                        }
                        else {
                            request.requestStatus = MatchRequestStatus.REJECTED;
                            System.out.printf("%s rejected match-request from %s %n",
                                    request.recipient.toString(),
                                    request.sender.toString());
                        }
                    }
                }
                System.out.println();
            }
            System.out.println("--------------------------------------\n");
        }*/
    }

    public static List<Bot> initializeBots() {
        Bot jake = new Bot("Jake", List.of(4, 4, 2, 1), """
        Jake is 21 years old and male.
        He doesn't really have any profession, he just hangs out with friends.
        He likes active sports, mostly ping-pong.
        He doesn't have much money, generally earning it from part-time jobs.
        Currently, he works as a pizza delivery guy.""");

        Bot lisa = new Bot("Lisa", List.of(2, 2, 3, 4), """
        Lisa is 24 years old and female.
        She is a graphic designer, mostly enjoying drawing and photography.
        She has an average income, working remotely for a small design studio and occasionally taking freelance projects.
        She also has a small collection of vintage cameras.""");

        Bot natan = new Bot("Natan", List.of(1, 1, 2, 1), """
        Natan is 19 years old and male.
        He is a student, mostly enjoying gaming and basketball.
        He doesn't have a stable income, occasionally earning money by helping his parents and doing small online jobs.
        He loves competitive games.""");

        Bot roze = new Bot("Roze", List.of(3, 2, 3, 4), """
        Roze is 27 years old and female.
        She is a bartender, mostly enjoying traveling and socializing.
        She has a modest but stable income, mostly spending it on experiences and going out.
        She also has a pet cat named Milo.""");

        Bot denis = new Bot("Denis", List.of(2, 2, 3, 1), """
        Denis is 30 years old and male.
        He is a mechanic, mostly enjoying cars and fishing.
        He earns decent money working at a local garage, but he doesn't save much.
        He also enjoys fixing old motorcycles.""");

        return List.of(jake, lisa, natan, roze, denis);
    }
}
