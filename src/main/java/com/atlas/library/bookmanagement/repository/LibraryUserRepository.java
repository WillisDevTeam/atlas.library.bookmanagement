package com.atlas.library.bookmanagement.repository;

import com.atlas.library.bookmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryUserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
}
