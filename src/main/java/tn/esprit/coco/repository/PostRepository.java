package tn.esprit.coco.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.coco.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
