package com.diplom.diplom.features.feed.repository;

import com.diplom.diplom.features.feed.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
