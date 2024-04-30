package tn.esprit.coco.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.coco.entity.Comment;
import tn.esprit.coco.entity.Post;
import tn.esprit.coco.repository.CommentRepository;
import tn.esprit.coco.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public Comment addComment(Long postId, Comment comment) {
        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            comment.setPost(post);
            return commentRepository.save(comment);
        } else {
            throw new EntityNotFoundException("Post not found with id: " + postId);
        }
    }

    public void removeComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public Comment modifyComment(Long commentId, Comment updatedComment) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));

        existingComment.setContent(updatedComment.getContent());

        return commentRepository.save(existingComment);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment addReply(Long parentCommentId, Long postId, Comment reply) {
        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new EntityNotFoundException("Parent Comment not found with id: " + parentCommentId));

        reply.setPost(parentComment.getPost());
        reply.setParentComment(parentComment);

        return commentRepository.save(reply);
    }

    public void removeReply(Long parentCommentId, Long replyId) {
        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new EntityNotFoundException("Parent Comment not found with id: " + parentCommentId));

        parentComment.getReplies().removeIf(reply -> reply.getCommentId().equals(replyId));

        commentRepository.save(parentComment);
    }

    public Comment modifyReply(Long parentCommentId, Long replyId, Comment updatedReply) {
        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new EntityNotFoundException("Parent Comment not found with id: " + parentCommentId));

        Comment existingReply = parentComment.getReplies().stream()
                .filter(reply -> reply.getCommentId().equals(replyId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Reply not found with id: " + replyId));

        existingReply.setContent(updatedReply.getContent());

        return commentRepository.save(parentComment);
    }
}
