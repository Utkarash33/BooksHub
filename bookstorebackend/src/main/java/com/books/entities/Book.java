package com.books.entities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import org.hibernate.annotations.Fetch;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
public class Book {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String googleId;
	
	private String title;
	
	@Lob
	@Column(columnDefinition = "LONGTEXT")
	private String description;
	private Integer page_count;
	private String published_date;
	
	@Lob
	@Column(columnDefinition = "LONGTEXT")
	private String image;


	 @ManyToMany(mappedBy = "books",fetch = FetchType.EAGER)
	    private List<Author> authors;
	
	
	
	 @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	    private List<BookReview> reviews;

	   @OneToMany(mappedBy = "book",cascade = CascadeType.ALL)
	   @JsonIgnore
	   private List<ReadingList> readingLists;
	

}
