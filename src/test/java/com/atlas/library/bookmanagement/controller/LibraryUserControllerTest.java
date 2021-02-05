package com.atlas.library.bookmanagement.controller;

import com.atlas.library.bookmanagement.model.User;
import com.atlas.library.bookmanagement.model.web.Requests;
import com.atlas.library.bookmanagement.service.LibraryUserService;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LibraryUserControllerTest {

    private LibraryUserController libraryUserController;
    private LibraryUserService libraryUserService;

    @BeforeEach
    public void init() {
        libraryUserService = mock(LibraryUserService.class);
        libraryUserController = new LibraryUserController(libraryUserService);
    }

    /*
    *  Unit Tests for getLibraryUser method
    */
    @Test
    public void testGetLibraryUserForSuccess() {
        // given:
        doReturn(getNewOptionalUserObject()).when(libraryUserService).getUser(anyString());

        // when:
        val response = libraryUserController.getLibraryUser("userId");

        // then:
        verify(libraryUserService, times(1)).getUser(anyString());
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /*
     *  Unit Tests for createLibraryUser method
     */
    @Test
    public void testCreateLibraryUserForSuccess() {
        // given:
        doReturn(getNewUserObject()).when(libraryUserService).createNewUser(any());

        // when:
        val response = libraryUserController.createLibraryUser(getNewCreateUserModel());

        // then:
        verify(libraryUserService, times(1)).createNewUser(any());
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /*
     *  Unit Tests for updateLibraryUserAccBal method
     */
    @Test
    public void testUpdateLibraryUserAccBalForSuccess() {
        // given:
        doReturn(getNewOptionalUserObject()).when(libraryUserService).updateUser(anyString(), anyDouble());

        // when:
        val response = libraryUserController.updateLibraryUserAccBal("userId", 1.00);

        // then:
        verify(libraryUserService, times(1)).updateUser("userId", 1.00);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /*
     *  Unit Tests for deleteLibraryUser method
     */
    @Test
    public void testDeleteLibraryUserForSuccess() {
        // when:
        val response = libraryUserController.deleteLibraryUser("userId");

        // then:
        verify(libraryUserService, times(1)).deleteUser("userId");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /*
    *   Helper methods
     */
    private Optional<User> getNewOptionalUserObject() {
        return Optional.of(User.builder()
                .userId("userId")
                .firstName("firstName")
                .lastName("lastName")
                .accountBalance(0.00)
                .creationDate(LocalDateTime.now())
                .modificationDate(LocalDateTime.now())
                .build());
    }

    private User getNewUserObject() {
        return User.builder()
                .userId("userId")
                .firstName("firstName")
                .lastName("lastName")
                .accountBalance(0.00)
                .creationDate(LocalDateTime.now())
                .modificationDate(LocalDateTime.now())
                .build();
    }

    private Requests.CreateUserModel getNewCreateUserModel() {
        return Requests.CreateUserModel.builder()
                .firstName("firstName")
                .lastName("lastName")
                .build();
    }
}
