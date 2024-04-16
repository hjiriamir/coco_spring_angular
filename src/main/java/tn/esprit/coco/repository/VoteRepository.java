package tn.esprit.coco.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.coco.entity.Post;
import tn.esprit.coco.entity.PostVote;
import tn.esprit.coco.entity.User;

import java.util.Optional;


@Repository
public interface VoteRepository extends JpaRepository<PostVote, Long> {
    Optional<PostVote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
