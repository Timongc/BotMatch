import entity.Bot;
import entity.MatchRequest;
import entity.MatchRequestStatus;

import java.util.List;

public class BotMatchApplication {
    public static void main(String[] args) {

        List<Bot> bots = initializeBots();

        int a=4;
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
                    String pickupLine = bot.generateResponseToTheText("""
                            You are on a dating program and you found someone you would like to talk with - write a pickup line.
                            ignore genders.
                            
                                        Rules:
                                        - Speak naturally, like a human.
                                        - Use simple everyday language.
                                        - Keep responses relatively short.
                            """).getFirst();
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
        }
    }

    public static List<Bot> initializeBots() {
        Bot jake = new Bot("Jake", List.of(4, 4, 2, 1));
        Bot lisa = new Bot("Lisa", List.of(2, 2, 3, 4));
        Bot natan = new Bot("Natan", List.of(1, 1, 2, 1));
        Bot roze = new Bot("Roze", List.of(3, 2, 3, 4));
        Bot denis = new Bot("Denis", List.of(2, 2, 3, 1));

        return List.of(jake, lisa, natan, roze, denis);
    }
}
