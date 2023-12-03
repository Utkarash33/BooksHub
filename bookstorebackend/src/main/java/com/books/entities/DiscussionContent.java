package com.books.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "discussion_content")
@Data
@NoArgsConstructor
public class DiscussionContent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	
    private User user;

    @ManyToOne
    @JsonIgnore
    private CommunityDiscussion discussion;

    private String content;
    
    private LocalDateTime dateTime;
}
