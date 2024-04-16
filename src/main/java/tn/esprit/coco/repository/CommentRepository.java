package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.coco.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
