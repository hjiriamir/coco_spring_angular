package tn.esprit.coco.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.webjars.NotFoundException;
import tn.esprit.coco.entity.Post;
import tn.esprit.coco.repository.PostRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void createPost(Post post) {
        post.setCreatedDate(Instant.now());
        postRepository.save(post);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public void modifyPost(Long postId, Post modifiedPost) {
        Optional<Post> existingPostOptional = postRepository.findById(postId);
        if (existingPostOptional.isPresent()) {
            Post existingPost = existingPostOptional.get();

            if (modifiedPost.getDescription() != null) {
                existingPost.setDescription(modifiedPost.getDescription());
            }

            existingPost.setUpdateDate(Instant.now());
            existingPost.setCreatedDate(Instant.now());

            postRepository.save(existingPost);
        } else {
            throw new EntityNotFoundException("Post with id " + postId + " not found");
        }
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();}

    public Post getPostById(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        return optionalPost.orElseThrow(() -> new EntityNotFoundException("Post not found with ID: " + postId));
    }

    public void upvotePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found"));
        post.setUpvotes(post.getUpvotes() + 1);
        postRepository.save(post);
    }

    public void downvotePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found"));
        post.setDownvotes(post.getDownvotes() + 1);
        postRepository.save(post);
    }
}
