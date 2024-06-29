package edu.sru.cpsc.webshopping.domain.discussionBoard;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.sru.cpsc.webshopping.domain.Comment;
import edu.sru.cpsc.webshopping.domain.Post;
import edu.sru.cpsc.webshopping.domain.user.User;

class PostTest {

    private Post post;

    @Mock
    private User userMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        post = new Post();
        post.setId(1L);
        post.setUser(userMock);
        post.setTitle("Test Title");
        post.setContent("This is a test post content.");
        post.setCreatedOn(new Date());
        post.setLastCommentedOn(new Date());
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, post.getId());
        assertEquals("Test Title", post.getTitle());
        assertEquals("This is a test post content.", post.getContent());
        assertNotNull(post.getCreatedOn());
        assertNotNull(post.getLastCommentedOn());
        assertEquals(userMock, post.getUser());
        
        // Testing comments handling
        Comment comment1 = new Comment();
        comment1.setContent("Nice post!");
        Comment comment2 = new Comment();
        comment2.setContent("Thanks for sharing.");
        List<Comment> comments = new ArrayList<>(Arrays.asList(comment1, comment2));
        post.setComments(comments);
        assertEquals(comments, post.getComments());
        assertEquals(2, post.getComments().size());
    }

    @Test
    void testUserInteraction() {
        when(userMock.getUsername()).thenReturn("testuser");
        assertEquals("testuser", post.getUser().getUsername());
    }

    @Test
    void testArchiving() {
        assertFalse(post.isArchived());
        post.setArchived(true);
        assertTrue(post.isArchived());
    }

    @Test
    void testPostDateManagement() {
        Date now = new Date();
        post.setCreatedOn(now);
        assertEquals(now, post.getCreatedOn());

        post.setLastCommentedOn(now);
        assertEquals(now, post.getLastCommentedOn());
    }
}
