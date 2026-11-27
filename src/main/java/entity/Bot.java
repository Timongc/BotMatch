package entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseOutputText;
import config.ApiKey;
import org.apache.commons.lang3.StringUtils;
import workers.BotCycle;

public class Bot {

    private Long id;

    private final String name;

    private Integer motivation;

    private final List<Integer> beauty;

    private List<MatchRequest> receivedRequests;

    private String summaryPersonality;

    public Bot(String name, List<Integer> beauty, String summaryPersonality) {
        this.name = name;
        this.beauty = beauty;
        this.receivedRequests = new LinkedList<>();
        this.summaryPersonality = summaryPersonality;
    }

    //the same as acceptedBeautyOfCandidate-function for now
    public boolean analyzeBeautyWhetherSendRequest(Bot candidate) {
        int candidateBeautySummary = candidate.getBeauty().stream().mapToInt(Integer::intValue).sum();

        if (candidateBeautySummary >= 0.8d * this.getBeauty().stream().mapToInt(Integer::intValue).sum()) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean acceptedBeautyOfCandidate(Bot candidate) {
        int candidateBeautySummary = candidate.getBeauty().stream().mapToInt(Integer::intValue).sum();

        return candidateBeautySummary >= 0.8d * this.getBeauty().stream().mapToInt(Integer::intValue).sum();
    }

    public MatchRequest sendRequest(Bot recipient) {
        MatchRequest request = new MatchRequest();
        request.sender = this;
        request.recipient = recipient;
        request.requestStatus = MatchRequestStatus.SENT;

        return request;
    }



    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getMotivation() {
        return motivation;
    }

    public void setMotivation(Integer motivation) {
        this.motivation = motivation;
    }

    public List<Integer> getBeauty() {
        return beauty;
    }

    public String getSummaryPersonality() {
        return summaryPersonality;
    }

    public void setSummaryPersonality(String summaryPersonality) {
        this.summaryPersonality = summaryPersonality;
    }

    public void addRequest(MatchRequest request) {
        receivedRequests.addFirst(request);
    }

    public List<MatchRequest> getAllReceivedRequests() {
        return receivedRequests;
    }

    public List<MatchRequest> getReceivedRequestsWithSentStatus() {
        return receivedRequests
                .stream()
                .filter(r -> r.requestStatus.equals(MatchRequestStatus.SENT))
                .toList();
    }

    @Override
    public String toString() {
        return this.getName() + "(" + StringUtils.join(beauty, ' ') + ")";
    }

    public int findCandidate(List<Bot> bots) {
        int candidateIndex;
        do {
            candidateIndex = new Random().nextInt(bots.size());
        } while (candidateIndex == bots.indexOf(this));

        return candidateIndex;
    }

    public List<String> generateResponseToTheText(String requestText) {
        OpenAIClient client = OpenAIOkHttpClient.builder().apiKey(ApiKey.getApiKey()).build();

        ResponseCreateParams params =
                ResponseCreateParams.builder().input(requestText).model("gpt-6.2-astra").build();

        Response response = client.responses().create(params);
        return response.output().stream()
                .flatMap(item -> item.message().stream())
                .flatMap(message -> message.content().stream())
                .flatMap(content -> content.outputText().stream())
                .map(ResponseOutputText::text)
                .toList();
    }
}
