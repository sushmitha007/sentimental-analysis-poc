package com.stackroute.sentimentalanalysis.controller;

import com.stackroute.sentimentalanalysis.domain.Reviewer;
import com.stackroute.sentimentalanalysis.service.ReviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.directory.SearchResult;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1")
public class ReviewerController {

    private ReviewerService reviewerService;

    @Autowired
    public ReviewerController(ReviewerService reviewerService) {
        this.reviewerService = reviewerService;
    }

    @PostMapping("reviewer")
    public ResponseEntity<?> saveReviewer(@RequestBody Reviewer reviewer)
    {
        ResponseEntity responseEntity;
        reviewerService.saveReviewer(reviewer);
        responseEntity=new ResponseEntity<Reviewer>( reviewer, HttpStatus.CREATED);
        return  responseEntity;
    }


    @GetMapping("reviewers")
    public ResponseEntity<?> getAllReviewers()
    {
        return  new ResponseEntity<List<Reviewer>>(reviewerService.getAllReviewer(),HttpStatus.OK);
    }

    @PostMapping("analysis")
    public ResponseEntity<?> getSentiment(@RequestBody Reviewer reviewer)
    {
        ResponseEntity responseEntity;
        int sentiments=reviewerService.findSentiment(reviewer);
        String sentiment=reviewerService.sentimentResult(sentiments);
        responseEntity=new ResponseEntity<String>(sentiment,HttpStatus.OK);
        return  responseEntity;
    }

    @DeleteMapping("delete/{reviewerName}")
    public ResponseEntity<?> deleteReviewer(@PathVariable String reviewerName)
    {
        reviewerService.deleteReviewer(reviewerName);
        return new ResponseEntity<String>("deleted",HttpStatus.OK);
    }
}
