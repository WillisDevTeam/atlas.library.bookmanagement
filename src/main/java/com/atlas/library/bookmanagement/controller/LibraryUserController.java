package com.atlas.library.bookmanagement.controller;

import com.atlas.library.bookmanagement.model.User;
import com.atlas.library.bookmanagement.model.web.Requests;
import com.atlas.library.bookmanagement.service.LibraryUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/atlas/library/user/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LibraryUserController {

    private final LibraryUserService libraryUserService;


    @GetMapping("/{userId}")
    public ResponseEntity<?> getLibraryUser(@PathVariable("userId") final String userId) {
        Optional<User> requestedUser = libraryUserService.getUser(userId);

        if (requestedUser.isPresent()) {
            EntityModel<User> resource = EntityModel.of(requestedUser.get());
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).updateLibraryUserAccBal(userId, 1.00)).withRel("Update Account Balance with overdue fee of one dollar"));
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deleteLibraryUser(userId)).withRel("Delete User"));
            return new ResponseEntity<>(resource, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createLibraryUser(@RequestBody Requests.CreateUserModel createUserModel) {
        log.info("Recieved a request to create a new user record with user firstName={}, lastName={}", createUserModel.getFirstName(), createUserModel.getLastName());
        User newlyCreatedUser = libraryUserService.createNewUser(createUserModel);

        EntityModel<User> resource = EntityModel.of(newlyCreatedUser);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).updateLibraryUserAccBal(newlyCreatedUser.getUserId(), 1.00)).withRel("Update Account Balance with overdue fee of one dollar"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deleteLibraryUser(newlyCreatedUser.getUserId())).withRel("Delete User"));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping("/{userId}/{updateAccBalance}")
    public ResponseEntity<?> updateLibraryUserAccBal(@PathVariable("userId") final String userId, @PathVariable double updateAccBalance) {
        val updatedUser = libraryUserService.updateUser(userId, updateAccBalance);

        if (updatedUser.isPresent()) {
            EntityModel<User> resource = EntityModel.of(updatedUser.get());
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getLibraryUser(updatedUser.get().getUserId())).withRel("Get newly updated user"));
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deleteLibraryUser(userId)).withRel("Delete User"));
            return new ResponseEntity<>(resource, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteLibraryUser(@PathVariable("userId") final String userId) {
        libraryUserService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
