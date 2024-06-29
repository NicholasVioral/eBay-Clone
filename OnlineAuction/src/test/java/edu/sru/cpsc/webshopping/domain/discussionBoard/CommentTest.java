package edu.sru.cpsc.webshopping.domain.discussionBoard;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.sru.cpsc.webshopping.domain.Comment;
import edu.sru.cpsc.webshopping.domain.Post;
import edu.sru.cpsc.webshopping.domain.user.User;

class CommentTest {

    private Comment comment;

    @Mock
    private User userMock;

    @Mock
    private Post postMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        comment = new Comment();
        comment.setId((long) 1);
        comment.setContent("This is a test comment.");
        comment.setUser(userMock);
        comment.setPost(postMock);
        comment.setCreatedOn(new Date());
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1, comment.getId());
        assertEquals("This is a test comment.", comment.getContent());
        assertNotNull(comment.getCreatedOn());
        assertEquals(userMock, comment.getUser());
        assertEquals(postMock, comment.getPost());
    }

    @Test
    void testUserInteraction() {
        when(userMock.getUsername()).thenReturn("testuser");
        assertEquals("testuser", comment.getUser().getUsername());
    }

    @Test
    void testPostInteraction() {
        when(postMock.getTitle()).thenReturn("Test Post Title");
        assertEquals("Test Post Title", comment.getPost().getTitle());
    }

    @Test
    void testCommentDateManagement() {
        Date now = new Date();
        comment.setCreatedOn(now);
        assertEquals(now, comment.getCreatedOn());
    }
}
