package com.books.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.books.entities.CommunityDiscussion;
import com.books.entities.DiscussionContent;
import com.books.services.DiscussionService;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping("/discussion")
@CrossOrigin(origins ="*",allowedHeaders = "*")
public class DiscussionController {

	@Autowired
	private DiscussionService discussionService;
	
	
	@PostMapping("/create")
	public ResponseEntity<CommunityDiscussion> startDiscussion(
			@RequestParam String title
			)
	{
		
		CommunityDiscussion cd = discussionService.createDiscussion(title);
		
		
		return new ResponseEntity<>(cd,HttpStatus.OK);
	}
	
	
	@GetMapping("/getall")
	
	public ResponseEntity<List<CommunityDiscussion>> getAllDiscussions()
	{
		List<CommunityDiscussion> cdList = discussionService.getAllDiscussions();
		
		return new ResponseEntity<>(cdList,HttpStatus.OK);
	}
	
	@GetMapping("/{discussionId}")
	public ResponseEntity<List<DiscussionContent>> getDiscussionById(
			@PathVariable Integer discussionId
			)
	{
		
		List<DiscussionContent> cd = discussionService.findById(discussionId);
		
		return new ResponseEntity<>(cd,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{discussionId}")
	public ResponseEntity<String> deleteDiscussion(@PathVariable Integer discussionId)
	{
		
		
        String cd = discussionService.deleteDiscussion(discussionId);
		
		return new ResponseEntity<>(cd,HttpStatus.OK);
	}
	
	
	
	@PostMapping("/addMessage/{discussionId}")
	public ResponseEntity<DiscussionContent> addMessageToDiscussion(
			@PathVariable String discussionId,
			@RequestParam String content,
			@RequestParam Integer UserId)
	{
		DiscussionContent dc = discussionService.addMessage(Integer.parseInt(discussionId),content,UserId);
		
		return new ResponseEntity<>(null,HttpStatus.OK);
	}
	
	
	
	
}
