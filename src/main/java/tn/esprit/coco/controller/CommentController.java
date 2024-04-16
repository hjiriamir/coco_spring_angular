package tn.esprit.coco.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.coco.entity.Comment;
import tn.esprit.coco.service.CommentService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/api/posts/{postId}/comments/addComment")
    public ResponseEntity<Comment> addComment(@PathVariable Long postId, @RequestBody Comment comment) {
        try {
            Comment addedComment = commentService.addComment(postId, comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedComment);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/api/posts/{postId}/comments/delete/{commentId}")
    public ResponseEntity<String> removeComment(@PathVariable Long commentId) {
        commentService.removeComment(commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/api/posts/{postId}/comments/modify/{commentId}")
    public ResponseEntity<Comment> modifyComment(@PathVariable Long commentId, @RequestBody Comment updatedComment) {
        Comment modifiedComment = commentService.modifyComment(commentId, updatedComment);
        return new ResponseEntity<>(modifiedComment, HttpStatus.OK);
    }

    @PostMapping("/api/posts/{postId}/comments/addReply/{parentCommentId}")
    public ResponseEntity<Comment> addReply(@PathVariable Long postId, @PathVariable Long parentCommentId, @RequestBody Comment reply) {
        try {
            Comment addedReply = commentService.addReply(postId, parentCommentId, reply);
            return new ResponseEntity<>(addedReply, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/posts/{postId}/comments/removeReply/{parentCommentId}/{commentId}")
    public ResponseEntity<String> removeReply(
            @PathVariable Long parentCommentId,
            @PathVariable Long commentId) {
        try {
            commentService.removeReply(parentCommentId, commentId);
            return new ResponseEntity<>("Reply deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Reply not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete reply: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/posts/{postId}/comments/modifyReply/{parentCommentId}/{commentId}")
    public ResponseEntity<Comment> modifyReply(
            @PathVariable Long parentCommentId,
            @PathVariable Long commentId,
            @RequestBody Comment updatedReply) {

        Comment modifiedReply = commentService.modifyReply(parentCommentId, commentId, updatedReply);
        return new ResponseEntity<>(modifiedReply, HttpStatus.OK);
    }

    @GetMapping("/api/comments/getAll")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}