package edu.sru.cpsc.webshopping.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import java.security.Principal;

import edu.sru.cpsc.webshopping.controller.discussionBoard.PostController;
import edu.sru.cpsc.webshopping.domain.Post;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.service.CommentService;
import edu.sru.cpsc.webshopping.service.PostService;
import edu.sru.cpsc.webshopping.service.UserService;

import java.lang.reflect.Field;

class PostControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PostService postService;

    @Mock
    private UserService userService;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        // Reflectively set mock objects
        setField(postController, "postService", postService);
        setField(postController, "userService", userService);
        setField(postController, "commentService", commentService);

        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    private void setField(Object targetObject, String fieldName, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);
    }

    @Test
    void testViewHomePage() throws Exception {
        Principal mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("testuser");
        when(userService.getUserByUsername("testuser")).thenReturn(new User());

        mockMvc.perform(get("/discussionBoard").principal(mockPrincipal))
            .andExpect(status().isOk())
            .andExpect(view().name("discussionBoard"))
            .andExpect(model().attributeExists("user"))
            .andExpect(model().attributeExists("posts"))
            .andExpect(model().attributeExists("post"))
            .andExpect(model().attributeExists("comment"));
    }

    @Test
    void testSavePost() throws Exception {
        Principal mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("testuser");
        User user = new User();
        Post post = new Post();
        post.setId(1L);
        when(userService.getUserByUsername("testuser")).thenReturn(user);
        when(postService.save(any(Post.class))).thenReturn(post);

        mockMvc.perform(post("/post").principal(mockPrincipal).flashAttr("post", post))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/discussionBoard?openPost=1"));
    }

    @Test
    void testSaveComment() throws Exception {
        Long postId = 1L;
        Principal mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("testuser");

        mockMvc.perform(post("/post/{postId}/comment", postId)
                        .principal(mockPrincipal)
                        .param("content", "Test comment"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/discussionBoard?openPost=" + postId));
    }

    @Test
    void testArchivePost() throws Exception {
        Long postId = 1L;
        Principal mockPrincipal = mock(Principal.class);
        mockMvc.perform(get("/post/archive/{postId}", postId).principal(mockPrincipal))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/discussionBoard?openPost=" + postId));
    }
}
