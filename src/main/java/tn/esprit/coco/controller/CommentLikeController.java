package tn.esprit.coco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.coco.service.CommentLikeService;

@RestController
@RequestMapping("/api/commentLikes")
public class CommentLikeController {

    /*@Autowired
    private CommentLikeService commentLikeService;

    @PostMapping("/likeComment/{commentId}/{userId}")
    public ResponseEntity<CommentLike> likeComment(
            @PathVariable Long commentId,
            @PathVariable Long userId) {
        try {
            CommentLike likedComment = commentLikeService.likeComment(commentId, userId);
            return new ResponseEntity<>(likedComment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/unlikeComment/{commentId}/{userId}")
    public ResponseEntity<String> unlikeComment(
            @PathVariable Long commentId,
            @PathVariable Long userId) {
        try {
            commentLikeService.unlikeComment(commentId, userId);
            return new ResponseEntity<>("Comment unliked successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getLikesForComment/{commentId}")
    public ResponseEntity<Integer> getLikesForComment(@PathVariable Long commentId) {
        try {
            int likesCount = commentLikeService.getLikesForComment(commentId);
            return new ResponseEntity<>(likesCount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
}
