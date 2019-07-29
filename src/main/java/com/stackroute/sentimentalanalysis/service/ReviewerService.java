package com.stackroute.sentimentalanalysis.service;

import com.stackroute.sentimentalanalysis.domain.Reviewer;

import java.util.List;

public interface ReviewerService {
    public Reviewer saveReviewer(Reviewer reviewer) ;
    public List<Reviewer> getAllReviewer();
    public Reviewer deleteReviewer(String reviewerName);
    public int findSentiment(Reviewer reviewer);
    public String sentimentResult(int sentiments);
}
