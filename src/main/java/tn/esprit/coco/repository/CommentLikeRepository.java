package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.coco.entity.Comment;
import tn.esprit.coco.entity.CommentLike;
import tn.esprit.coco.entity.User;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    /*boolean existsByCommentAndUser(Comment comment, User user);

    Optional<CommentLike> findByCommentAndUser(Comment comment, User user);

    int countByComment(Comment comment);*/
}
