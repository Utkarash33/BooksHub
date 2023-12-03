package com.books.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.books.entities.CommunityDiscussion;
import com.books.entities.DiscussionContent;
import com.books.entities.User;
import com.books.exceptions.NoDataFoundException;
import com.books.repository.CommunityDiscussionRepo;
import com.books.repository.DiscussionContentRepo;
import com.books.repository.UserRepo;

@Service
public class DiscussionServiceImpl implements DiscussionService {
	
	@Autowired
	CommunityDiscussionRepo communityDiscussionRepo;
	
	@Autowired
	DiscussionContentRepo contentRepo;
	
	@Autowired
	UserRepo repo;
	

	@Override
	public CommunityDiscussion createDiscussion(String title) {
		
		
		CommunityDiscussion cd = new CommunityDiscussion();
		
		cd.setCreated_at(LocalDate.now());
		cd.setTitle(title);
		cd.setContents(new ArrayList<>());
		System.out.println(cd);
		return communityDiscussionRepo.save(cd);
	}

	@Override
	public List<CommunityDiscussion> getAllDiscussions() {
				return communityDiscussionRepo.findAll();
	}

	@Override
	public List<DiscussionContent> findById(Integer discussionId) {
		
		
	CommunityDiscussion communityDiscussion = communityDiscussionRepo.findById(discussionId)
			                                 .orElseThrow(()-> new NoDataFoundException("Discussion Not found"));
		return communityDiscussion.getContents();
	}

	@Override
	public String deleteDiscussion(Integer discussionId) {
		CommunityDiscussion communityDiscussion = communityDiscussionRepo.findById(discussionId)
                .orElseThrow(()-> new NoDataFoundException("Discussion Not found"));

		communityDiscussionRepo.delete(communityDiscussion);
		
		return "Discussion has been removed";
	}

	@Override
	public DiscussionContent addMessage(Integer discussionId, String content, Integer userId) {
		
		CommunityDiscussion communityDiscussion = communityDiscussionRepo.findById(discussionId)
                .orElseThrow(()-> new NoDataFoundException("Discussion Not found"));
		
		User user = repo.findById(userId).orElseThrow(()-> new NoDataFoundException("User not found."));
		
		DiscussionContent discussionContent = new DiscussionContent();
		
		discussionContent.setContent(content);
		discussionContent.setUser(user);
		discussionContent.setDiscussion(communityDiscussion);
		discussionContent.setDateTime(LocalDateTime.now());
		contentRepo.save(discussionContent);
		
		communityDiscussion.getContents().add(discussionContent);
        communityDiscussionRepo.save(communityDiscussion);
		return contentRepo.save(discussionContent);
	}

}
