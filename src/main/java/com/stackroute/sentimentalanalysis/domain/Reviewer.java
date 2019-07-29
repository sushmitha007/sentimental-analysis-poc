package com.stackroute.sentimentalanalysis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "reviewer")
public class Reviewer {
    @Id
    String reviewerName;
    String reviewerEmail;
    String password;
    String review;
}
