package com.stackroute.sentimentalanalysis.service;

import com.stackroute.sentimentalanalysis.domain.Reviewer;
import com.stackroute.sentimentalanalysis.repository.ReviewerRepository;
import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.SentenceAnnotator;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;

@Service
public class ReviewerServiceImpl implements ReviewerService{

    private ReviewerRepository reviewerRepository;
    private StanfordCoreNLP pipeline;
    Properties properties=new Properties();

    @PostConstruct
    public void init(){
        properties.setProperty("annotators", "tokenize,ssplit,pos,lemma,depparse,parse,natlog,openie,sentiment");
        pipeline=new StanfordCoreNLP(properties);
    }

    @Autowired
    public ReviewerServiceImpl(ReviewerRepository reviewerRepository) {
        this.reviewerRepository = reviewerRepository;
    }

    @Override
    public Reviewer saveReviewer(Reviewer reviewer) {
        reviewerRepository.save(reviewer);
        return reviewer;
    }

    @Override
    public List<Reviewer> getAllReviewer() {
        List<Reviewer> reviewerList=reviewerRepository.findAll();
        return reviewerList;
    }

    @Override
    public Reviewer deleteReviewer(String reviewerName) {
        reviewerRepository.deleteById(reviewerName);
        return null;
    }

    @Override
    public int findSentiment(Reviewer reviewer) {
        int mainSentiment=0;
        String clearReviewText=clearReview(reviewer.getReview());
        if(clearReviewText != null && clearReviewText.length()>0)
        {
            int longest=0;
            Annotation annotation=pipeline.process(clearReviewText);
            for(CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class))
            {
                Tree tree=sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment= RNNCoreAnnotations.getPredictedClass(tree);
                String parseText=sentence.toString();
                if(parseText.length()>longest){
                    mainSentiment=sentiment;
                    longest=parseText.length();
                }
            }
        }
        return mainSentiment;
    }

    @Override
    public String sentimentResult(int sentiments) {
        String result="";
        if(sentiments == 0)
        {
            result="Very negative";
        }
        else if(sentiments == 1)
        {
            result ="Negative";
        }
        else  if(sentiments == 2)
        {
            result="Neutral";
        }
        else if(sentiments == 3)
        {
            result="Positive";
        }
        else {
            result="Very Positive";
        }
        return  result;
    }

    public String clearReview(String review)
    {
        review=review.toLowerCase();
        return  review;
    }
}
