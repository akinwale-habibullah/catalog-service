package com.akinwalehabib.catalogservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.akinwalehabib.catalogservice.config.SecurityConfig;
import com.akinwalehabib.catalogservice.domain.BookNotFoundException;
import com.akinwalehabib.catalogservice.domain.BookService;
import com.akinwalehabib.catalogservice.web.BookController;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BookController.class)
@Import(SecurityConfig.class)
public class BookControllerMvcTests {
  
  @Autowired
  MockMvc mockMvc;

  @MockBean
  BookService bookService;

  @MockBean
  JwtDecoder jwtDecoder;

  @Test
  void whenDeleteBookWithEmployeeRoleThenShouldReturn204() throws Exception {
    var isbn = "7373731394";
    mockMvc
      .perform(MockMvcRequestBuilders.delete("/books/" + isbn)
        .with(SecurityMockMvcRequestPostProcessors.jwt()
          .authorities(new SimpleGrantedAuthority("ROLE_employee"))))
      .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  void whenDeleteBookWithCustomerRoleThenShouldReturn403() throws Exception {
    var isbn = "7373731394";
    mockMvc
      .perform(MockMvcRequestBuilders.delete("/books/" + isbn)
        .with(SecurityMockMvcRequestPostProcessors.jwt()
          .authorities(new SimpleGrantedAuthority("ROLE_customer"))))
      .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  void whenDeleteBookNotAuthenticatedthenShouldReturn401() throws Exception {
    var isbn = "7373731394";
    mockMvc
      .perform(MockMvcRequestBuilders.delete("/books/" + isbn))
      .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Test
  void whenGetBookNotExistingThenShouldReturn404() throws Exception {
    String isbn = "73737313940";
    given(bookService.viewBookDetails(isbn))
      .willThrow(BookNotFoundException.class);
    mockMvc
      .perform(get("/books/" + isbn))
      .andExpect(status().isNotFound());
  }
}
