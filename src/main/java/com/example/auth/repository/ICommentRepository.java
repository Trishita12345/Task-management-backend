package com.example.auth.repository;

import com.example.auth.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long>, QuerydslPredicateExecutor<Comment> {
}
