package edu.sru.cpsc.webshopping.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sru.cpsc.webshopping.domain.Comment;
import edu.sru.cpsc.webshopping.domain.Post;
import edu.sru.cpsc.webshopping.repository.CommentRepository;
import edu.sru.cpsc.webshopping.repository.PostRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Date;
import java.util.List;

/**
 * Service for handling anything related to discussion board posts.
 * @author Jacob
 */

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private CommentRepository commentRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Returns a list of all posts in the database
     * @return
     */
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    /**
     * Saves given post into the database
     * @param post
     * @return
     */
    public Post save(Post post) {
    	if (post.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty or consist only of spaces.");
        }
        if (post.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be empty or consist only of spaces.");
        }
        return postRepository.save(post);
    }

    /**
     * Finds post by given ID
     * @param id
     * @return
     */
    public Post findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }
    
    /**
     * Finds all posts that contain comments
     * @return
     */
    public List<Post> findAllWithComments() {
        return postRepository.findAllWithComments();
    }

    /**
     * Deletes post by given ID
     * @param id
     */
    public void deletePostById(Long id) {
    	Post post = findById(id);
    	if(post != null) {
    		postRepository.delete(post);
    	}
    }
    
    /**
     * Toggles the archive status of a post by ID
     * @param id
     */
    public void toggleArchivePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        // Toggle the archived status
        post.setArchived(!post.isArchived());
        postRepository.save(post);
    }
    
    /**
     * Updates when a post was last commented on
     * @param postId
     */
    public void updateLastCommentedOn(Long postId) {
        Post post = findById(postId);
        if (post != null) {
            post.setLastCommentedOn(new Date());
            save(post);
        }
    }
    
    /**
     * Deletes posts not commented on since given cutoff date
     * @param cutoffDate
     */
    public void deletePostsNotCommentedSince(Date cutoffDate) {
        List<Post> oldPosts = postRepository.findPostsNotCommentedSince(cutoffDate);
        for (Post post : oldPosts) {
            postRepository.delete(post);
        }
    }
}