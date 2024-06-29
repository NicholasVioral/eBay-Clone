package edu.sru.cpsc.webshopping.controller.discussionBoard;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sru.cpsc.webshopping.domain.Comment;
import edu.sru.cpsc.webshopping.domain.Post;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.service.CommentService;
import edu.sru.cpsc.webshopping.service.PostService;
import edu.sru.cpsc.webshopping.service.UserService;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService; 

    @GetMapping("/discussionBoard")
    public String viewHomePage(Model model, Principal principal) {
        if (principal != null) {
            User user = userService.getUserByUsername(principal.getName());
            model.addAttribute("user", user);
        }
        // For the form to add a new post
        model.addAttribute("post", new Post()); 
        // Load all posts and their comments
        var posts = postService.findAllWithComments();

        model.addAttribute("posts", posts);
        model.addAttribute("comment", new Comment()); // For the comment form
        return "discussionBoard";
    }

    @PostMapping("/post")
    public String savePost(@ModelAttribute("post") Post post, Principal principal) {
        if (principal != null) {
            User user = userService.getUserByUsername(principal.getName());
            post.setUser(user); // Set the current user as the author of the post
        }
        //postService.save(post);
        //return "redirect:/discussionBoard/post";
        post = postService.save(post);
        return "redirect:/discussionBoard?openPost=" + post.getId();
    }

    @PostMapping("/post/{postId}/comment")
    public String saveComment(@PathVariable Long postId, @ModelAttribute("comment") Comment comment, Principal principal) {       
        if (principal != null) {
            User user = userService.getUserByUsername(principal.getName());
            comment.setUser(user); // Set the user to the comment
            commentService.addCommentToPost(postId, comment); // Add the comment to the post
        }
        //return "redirect:/discussionBoard"; // Redirect to the discussion board page
        return "redirect:/discussionBoard?openPost=" + postId;   
    }
    
   // @RequestMapping("/post/delete/{postId}") 
   // public String deletePost(@PathVariable Long postId, Model model, Principal principal){
   // 	postService.deletePostById(postId);
   // 	
   //   "redirect:/discussionBoard";
   // }
    
    @RequestMapping("/post/archive/{postId}") 
    public String archivePost(@PathVariable Long postId, Model model, Principal principal){
    	postService.toggleArchivePostById(postId);
    	
    	//return "redirect:/discussionBoard";
    	return "redirect:/discussionBoard?openPost=" + postId;
    }
}
