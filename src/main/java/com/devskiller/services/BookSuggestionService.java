package com.devskiller.services;

import com.devskiller.model.Author;
import com.devskiller.model.Book;
import com.devskiller.model.Reader;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

class BookSuggestionService {

	private final Set<Book> books;
	private final Set<Reader> readers;

	public BookSuggestionService(Set<Book> books, Set<Reader> readers) {
		this.books = books;
		this.readers = readers;
	}

	Set<String> suggestBooks(Reader reader) {
	    // Si el usuario no tiene seleccionados ningún género favorito, directamente devolvemos un Set vacío
	    if (reader.favouriteGenres().isEmpty()) { 
	        return Set.of(); 
	    }
	    
	    return readers.stream() 
	            .filter(otherReader -> otherReader.age() == reader.age() && !otherReader.equals(reader))	// Filtra los lectores que tienen la misma edad que el usuario actual, excluyendo al propio usuario
	            .flatMap(otherReader -> otherReader.favouriteBooks().stream())	// Aplanamos los libros favoritos de los lectores filtrados en un único flujo (stream)
	            .filter(book -> book.rating() >= 4 && reader.favouriteGenres().contains(book.genre()))	// Filtramos los libros que tienen una calificación de 4 o más y que pertenecen a los géneros favoritos del usuario
	            .map(Book::title)	// Extraemos el título de cada libro que pasa los filtros anteriores
	            .collect(Collectors.toSet());	// Recogemos los títulos en un conjunto (Set) que elimina automáticamente duplicados
	}

	Set<String> suggestBooks(Reader reader, int rating) {
		if (reader.favouriteGenres().isEmpty()) {
		    return Set.of(); 
		}
		
		return readers.stream()
		        .filter(otherReader -> otherReader.age() == reader.age() && !otherReader.equals(reader))	
		        .flatMap(otherReader -> otherReader.favouriteBooks().stream())
		        .filter(book -> book.rating() == rating && reader.favouriteGenres().contains(book.genre()))	// Filtramos según el rating mencionado
		        .map(Book::title)
		        .collect(Collectors.toSet());
	}

	Set<String> suggestBooks(Reader reader, Author author) {
		if (reader.favouriteGenres().isEmpty()) {
		    return Set.of(); 
		}
		
		return readers.stream()
		        .filter(otherReader -> otherReader.age() == reader.age() && !otherReader.equals(reader))
		        .flatMap(otherReader -> otherReader.favouriteBooks().stream())
		        .filter(book -> book.rating() >= 4 && reader.favouriteGenres().contains(book.genre()) && book.author().equals(author))	// Filtramos según el autor mencionado
		        .map(Book::title)
		        .collect(Collectors.toSet());
	}

}
