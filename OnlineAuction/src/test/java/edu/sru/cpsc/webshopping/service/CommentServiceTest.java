package edu.sru.cpsc.webshopping.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.sru.cpsc.webshopping.domain.Comment;
import edu.sru.cpsc.webshopping.domain.Post;
import edu.sru.cpsc.webshopping.repository.CommentRepository;

import java.util.Optional;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostService postService;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCommentToPost() {
        Long postId = 1L;
        Post post = new Post();
        post.setId(postId);
        Comment comment = new Comment();
        
        when(postService.findById(postId)).thenReturn(post);
        when(commentRepository.save(comment)).thenReturn(comment);

        Comment savedComment = commentService.addCommentToPost(postId, comment);

        assertNotNull(savedComment);
        assertEquals(post, savedComment.getPost());
        verify(postService).updateLastCommentedOn(postId);
        verify(commentRepository).save(comment);
    }

    @Test
    void testAddCommentToPostWithNonExistentPost() {
        Long postId = 1L;
        Comment comment = new Comment();
        
        when(postService.findById(postId)).thenReturn(null);

        Comment savedComment = commentService.addCommentToPost(postId, comment);

        assertNull(savedComment);
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void testSaveComment() {
    	Comment comment = new Comment();
    	comment.setContent("test");
        when(commentRepository.save(comment)).thenReturn(comment);

        commentService.save(comment);

        verify(commentRepository).save(comment);
    }
}
