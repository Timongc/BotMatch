package entity;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Bot {

    private Long id;

    private String name;

    private Integer motivation;

    private List<Integer> beauty;

    private List<MatchRequest> receivedRequests;

    public Bot() {
        this.receivedRequests = new LinkedList<>();
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

    public void setName(String name) {
        this.name = name;
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

    public void setBeauty(List<Integer> beauty) {
        this.beauty = beauty;
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
}
