package edu.sru.cpsc.webshopping.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.sru.cpsc.webshopping.domain.Post;
import edu.sru.cpsc.webshopping.repository.PostRepository;
import edu.sru.cpsc.webshopping.repository.CommentRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<Post> expectedPosts = Arrays.asList(new Post(), new Post());
        when(postRepository.findAll()).thenReturn(expectedPosts);

        List<Post> resultPosts = postService.findAll();

        assertEquals(expectedPosts, resultPosts);
        verify(postRepository).findAll();
    }

    @Test
    void testSave() {
        Post post = new Post();
        post.setTitle("test");
        post.setContent("test");
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post savedPost = postService.save(post);

        assertNotNull(savedPost);
        verify(postRepository).save(post);
    }

    @Test
    void testFindById() {
        Post post = new Post();
        post.setId(1L);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        Post resultPost = postService.findById(1L);

        assertEquals(post, resultPost);
        verify(postRepository).findById(1L);
    }

    @Test
    void testToggleArchivePostById() {
        Post post = new Post();
        post.setId(1L);
        post.setArchived(false);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        postService.toggleArchivePostById(1L);

        assertTrue(post.isArchived());
        verify(postRepository).save(post);
    }

    @Test
    void testUpdateLastCommentedOn() {
        Post post = new Post();
        post.setId(1L);
        post.setTitle("test");
        post.setContent("test");
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        postService.updateLastCommentedOn(1L);

        assertNotNull(post.getLastCommentedOn());
        verify(postRepository).save(post);
    }

    @Test
    void testDeletePostsNotCommentedSince() {
        Date cutoffDate = new Date();
        List<Post> oldPosts = Arrays.asList(new Post());
        when(postRepository.findPostsNotCommentedSince(cutoffDate)).thenReturn(oldPosts);

        postService.deletePostsNotCommentedSince(cutoffDate);

        verify(postRepository, times(oldPosts.size())).delete(any(Post.class));
    }
}
