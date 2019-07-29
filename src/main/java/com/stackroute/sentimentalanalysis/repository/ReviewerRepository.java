package com.stackroute.sentimentalanalysis.repository;

import com.stackroute.sentimentalanalysis.domain.Reviewer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewerRepository extends MongoRepository<Reviewer,String> {

}
