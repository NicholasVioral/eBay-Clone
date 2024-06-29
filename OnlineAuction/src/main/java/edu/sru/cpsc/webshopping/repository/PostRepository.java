package edu.sru.cpsc.webshopping.repository;



import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.sru.cpsc.webshopping.domain.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.comments")
    List<Post> findAllWithComments();
    
    @Query("SELECT p FROM Post p WHERE p.lastCommentedOn < :cutoffDate")
    List<Post> findPostsNotCommentedSince(@Param("cutoffDate") Date cutoffDate);

}