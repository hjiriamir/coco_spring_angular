package tn.esprit.coco.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.coco.entity.Comment;
import tn.esprit.coco.repository.CommentLikeRepository;
import tn.esprit.coco.repository.CommentRepository;


@Service
public class CommentLikeService {

   /* @Autowired
    private CommentLikeRepository commentLikeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public CommentLike likeComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        // Check if the user has already liked the comment
        if (commentLikeRepository.existsByCommentAndUser(comment, userRepository.getById(userId))) {
            throw new RuntimeException("User has already liked the comment");
        }

        CommentLike commentLike = new CommentLike();
        commentLike.setComment(comment);
        commentLike.setUser(userRepository.getById(userId));

        return commentLikeRepository.save(commentLike);
    }

    @Transactional
    public void unlikeComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        CommentLike commentLike = commentLikeRepository.findByCommentAndUser(comment, userRepository.getById(userId))
                .orElseThrow(() -> new RuntimeException("User has not liked the comment"));

        commentLikeRepository.delete(commentLike);
    }

    public int getLikesForComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        return commentLikeRepository.countByComment(comment);
    }*/
}
