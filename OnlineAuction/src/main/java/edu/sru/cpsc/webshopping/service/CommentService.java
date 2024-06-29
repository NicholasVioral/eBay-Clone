package edu.sru.cpsc.webshopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sru.cpsc.webshopping.domain.Comment;
import edu.sru.cpsc.webshopping.domain.Post;
import edu.sru.cpsc.webshopping.repository.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for handling discussion board comments
 * @author Jacob
 */
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostService postService; 

    /**
     * Adds comment to given post
     * @param postId
     * @param comment
     * @return
     */
    public Comment addCommentToPost(Long postId, Comment comment) {
        Post post = postService.findById(postId); // Use PostService to find the post
        if (post != null) {
            comment.setPost(post); // Set the post in the comment
            postService.updateLastCommentedOn(postId);
            return commentRepository.save(comment); // Save the comment
        }
        return null;
    }

    /**
     * Saves given comment to the database
     * @param comment
     */
	public void save(Comment comment) {
		if (comment.getContent().trim().isEmpty()) {
	        throw new IllegalArgumentException("Comment cannot be empty or consist only of spaces.");
	    }
		commentRepository.save(comment);
	}
}