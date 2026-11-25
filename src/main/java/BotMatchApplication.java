import entity.Bot;
import entity.MatchRequest;
import entity.MatchRequestStatus;

import java.util.List;
import java.util.Random;

public class BotMatchApplication {
    public static void main(String[] args) {

        List<Bot> bots = initializeBots();

        int a=7;
        Bot botCandidate;
        while (a>0) {
            a--;
            for (Bot bot : bots) {
                //every bot looks through dating program and if beauty of a bot satisfies bot - it sends match-request
                int randomIndex;
                do {
                    randomIndex = new Random().nextInt(bots.size());
                } while (randomIndex == bots.indexOf(bot));
                botCandidate = bots.get(randomIndex);

                System.out.printf("%s is analyzing %s %n",
                        bot.toString(),
                        botCandidate.toString());

                if (bot.analyzeBeautyWhetherSendRequest(botCandidate)) {
                    bots.get(randomIndex).addRequest(
                            bot.sendRequest(botCandidate));
                    System.out.printf("%s sent match-request to %s %n",
                            bot.toString(),
                            botCandidate.toString());
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
        }
    }

    public static List<Bot> initializeBots() {
        Bot jake = new Bot();
        jake.setName("Jake");
        jake.setBeauty(List.of(4, 4, 2, 1));
        Bot lisa = new Bot();
        lisa.setName("Lisa");
        lisa.setBeauty(List.of(2, 2, 3, 4));
        Bot neitan = new Bot();
        neitan.setName("Neitan");
        neitan.setBeauty(List.of(1, 1, 2, 1));
        Bot roze = new Bot();
        roze.setName("Roze");
        roze.setBeauty(List.of(3, 2, 3, 4));
        Bot denis = new Bot();
        denis.setName("Denis");
        denis.setBeauty(List.of(2, 2, 3, 1));

        return List.of(jake, lisa, neitan, roze, denis);
    }
}
