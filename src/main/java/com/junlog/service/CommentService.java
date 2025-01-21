package com.junlog.service;

import com.junlog.domain.Comment;
import com.junlog.domain.Post;
import com.junlog.exception.CommentNotFound;
import com.junlog.exception.InvalidPassword;
import com.junlog.exception.PostNotFound;
import com.junlog.repository.comment.CommentRepository;
import com.junlog.repository.post.PostRepository;
import com.junlog.request.comment.CommentCreate;
import com.junlog.request.comment.CommentDelete;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void write(Long postId, @Valid CommentCreate request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        Comment comment = Comment.builder()
                .author(request.getAuthor())
                .password(encryptedPassword)
                .content(request.getContent())
                .build();

        post.addComment(comment);

    }

    public void delete(Long commentId, CommentDelete request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFound::new);

        String password = comment.getPassword();
        if (!passwordEncoder.matches(request.getPassword(), password)) {
            throw new InvalidPassword();
        }

        commentRepository.deleteById(commentId);
    }
}
