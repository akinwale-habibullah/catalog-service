package com.akinwalehabib.catalogservice;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.akinwalehabib.catalogservice.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;

public class BookValidationTests {
  private static Validator validator;

  @BeforeAll
  static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void whenAllFieldsCorrectThenValidationSuccess() {
    var book = Book.of("1234567890", "Title", "Author", 9.90, "publisher");
    Set<ConstraintViolation<Book>> violations = validator.validate(book);
    assertThat(violations).isEmpty();
  }

  @Test
  void whenIsbnDefinedButIncorrectThenValidationFails() {
    var book = Book.of("a234567890", "Title", "Author", 9.90, "publisher");
    Set<ConstraintViolation<Book>> violations = validator.validate(book);
    assertThat(violations).hasSize(1);
    assertThat(violations.iterator().next().getMessage())
      .isEqualTo("The ISBN format must be valid.");
  }
}
