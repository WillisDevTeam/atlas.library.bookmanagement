package com.atlas.library.bookmanagement.service;

import com.atlas.library.bookmanagement.model.User;
import com.atlas.library.bookmanagement.model.web.Requests;
import com.atlas.library.bookmanagement.repository.LibraryUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LibraryUserService {

    private final LibraryUserRepository libraryUserRepository;

    public Optional<User> getUser(String userId) {

        log.info("you are getting a user with userId={}", userId);
        return libraryUserRepository.findById(userId);
    }

    public User createNewUser(Requests.CreateUserModel createUserModel) {

        try {
            User newUser = Requests.ofLibraryUserCreate(createUserModel);
            log.info("Attempting to create a new User with firstName={} lastName={}", newUser.getFirstName(), newUser.getLastName());
            return libraryUserRepository.save(newUser);

        } catch (Exception e) {
            log.error("Error occurred while attempting to delete a user", e);
            throw new RuntimeException(e);
        }
    }

    public Optional<User> updateUser(String userId, double updateAccBalance) {

        Optional<User> requestedUserObj = getUser(userId);
        if (requestedUserObj.isPresent()) {
            try {
                User reqUser = requestedUserObj.get();
                reqUser.setAccountBalance(updateAccBalance);
                reqUser.setModificationDate(LocalDateTime.now());

                log.info("Attempting to update a user with new firstName={}, and new lastName={}", reqUser.getFirstName(), reqUser.getLastName());
                return Optional.of(libraryUserRepository.save(reqUser));

            } catch (Exception e) {
                log.error("Error occurred while attempting to update a user", e);
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }

    public void deleteUser(String userId) {

        try {
            log.info("Attempting to deleting a user with userId={}", userId);
            libraryUserRepository.deleteById(userId);

        } catch (Exception e) {
            log.error("Error occurred while attempting to delete a user", e);
            throw new RuntimeException(e);
        }
    }
}
