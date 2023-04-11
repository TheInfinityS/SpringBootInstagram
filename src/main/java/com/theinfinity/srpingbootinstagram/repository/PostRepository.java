package com.theinfinity.srpingbootinstagram.repository;

import com.theinfinity.srpingbootinstagram.entity.Post;
import com.theinfinity.srpingbootinstagram.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    @EntityGraph(attributePaths = { "comments" })
    Page<Post> findByAuthorIn(List<User> users, Pageable pageable);

    @EntityGraph(attributePaths = { "comments" })
    List<Post> findByAuthor(User user);

    @EntityGraph(attributePaths = { "comments" })
    Optional<Post> findById(Long id);
}
