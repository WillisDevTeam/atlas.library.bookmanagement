package com.atlas.library.bookmanagement.service;

import com.atlas.library.bookmanagement.model.User;
import com.atlas.library.bookmanagement.model.web.Requests;
import com.atlas.library.bookmanagement.repository.LibraryUserRepository;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LibraryUserServiceTest {

    private LibraryUserRepository libraryUserRepository;
    private LibraryUserService libraryUserService;

    @BeforeEach
    public void init() {
        libraryUserRepository = mock(LibraryUserRepository.class);
        libraryUserService = new LibraryUserService(libraryUserRepository);
    }

    /*
     *  Unit Tests for getLibraryUser method
     */
    @Test
    public void testGetUserForSuccess() {
        // given:
        doReturn(getNewOptionalUserObject()).when(libraryUserRepository).findById(anyString());

        // when:
        val response = libraryUserService.getUser("userId");

        // then:
        verify(libraryUserRepository, times(1)).findById("userId");
        assertNotNull(response);
        assertEquals(getNewOptionalUserObject(), response);
    }

    /*
     *  Unit Tests for createLibraryUser method
     */
    @Test
    public void testCreateNewUserForSuccess() {
        // given:
        doReturn(getNewUserObject()).when(libraryUserRepository).save(any());

        // when:
        val response = libraryUserService.createNewUser(getNewCreateUserModel());

        // then:
        verify(libraryUserRepository, times(1)).save(any(User.class));
        assertNotNull(response);
        assertEquals(getNewUserObject(), response);
    }

    /*
     *  Unit Tests for updateLibraryUserAccBal method
     */
    @Test
    public void testUpdateUserForSuccess() {
        // given:
        doReturn(getNewUserObject()).when(libraryUserRepository).save(any());
        doReturn(getNewOptionalUserObject()).when(libraryUserRepository).findById(anyString());

        // when:
        val response = libraryUserService.updateUser("userId", 1.00);

        // then:
        verify(libraryUserRepository, times(1)).findById("userId");
        verify(libraryUserRepository, times(1)).save(any(User.class));
        assertNotNull(response);
        assertEquals(getNewUserObject(), response.get());
    }

    /*
     *  Unit Tests for deleteLibraryUser method
     */
    @Test
    public void testDeleteUserForSuccess() {
        // when:
        libraryUserService.deleteUser("userId");

        // then:
        verify(libraryUserRepository, times(1)).deleteById("userId");
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
                .creationDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .modificationDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .build());
    }

    private User getNewUserObject() {
        return User.builder()
                .userId("userId")
                .firstName("firstName")
                .lastName("lastName")
                .accountBalance(0.00)
                .creationDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .modificationDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .build();
    }

    private Requests.CreateUserModel getNewCreateUserModel() {
        return Requests.CreateUserModel.builder()
                .firstName("firstName")
                .lastName("lastName")
                .build();
    }
}
