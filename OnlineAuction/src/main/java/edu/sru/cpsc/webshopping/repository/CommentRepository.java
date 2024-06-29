package edu.sru.cpsc.webshopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.sru.cpsc.webshopping.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}