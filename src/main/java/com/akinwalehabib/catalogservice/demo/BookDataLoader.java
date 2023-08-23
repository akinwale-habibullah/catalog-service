package com.akinwalehabib.catalogservice.demo;

import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.akinwalehabib.catalogservice.domain.Book;
import com.akinwalehabib.catalogservice.domain.BookRepository;

import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;

@Component
@Profile("testdata")
class BookDataLoader {
  private final BookRepository bookRepository;

  public BookDataLoader(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void loadBookTestData() {
    var book1 = Book.of("1234567891", "Northern Lights", "Lyra Silverstar", 9.90);
    var book2 = Book.of("1234567892", "Polar Journey", "Iorek Polarson", 12.90);
    bookRepository.saveAll(List.of(book1, book2));
  }
}