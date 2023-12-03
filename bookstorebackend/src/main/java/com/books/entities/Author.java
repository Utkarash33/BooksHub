package com.books.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "authors")
public class Author {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String bio;
	
	
	 @ManyToMany(cascade = CascadeType.ALL)
	    @JoinTable(
	        name = "author_book",
	        joinColumns = @JoinColumn(name = "author_id"),
	        inverseJoinColumns = @JoinColumn(name = "book_id")
	        )
	    @JsonIgnore
	    private List<Book> books;
}
