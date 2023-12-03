package com.books.DTO;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BooksDTO {

	
	private String id;
    private String title;
    private String description;
    private Integer pageCount;
    private String publishedDate;
    private List<String> authors;
    private String imageUrl;
	
}
