package mk.ukim.finki.wp.jan2024g2.service.impl;

import mk.ukim.finki.wp.jan2024g2.model.Post;
import mk.ukim.finki.wp.jan2024g2.model.PostType;
import mk.ukim.finki.wp.jan2024g2.model.Tag;
import mk.ukim.finki.wp.jan2024g2.model.exceptions.InvalidPostIdException;
import mk.ukim.finki.wp.jan2024g2.repository.PostRepository;
import mk.ukim.finki.wp.jan2024g2.service.PostService;
import mk.ukim.finki.wp.jan2024g2.service.TagService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final TagService tagService;

    public PostServiceImpl(PostRepository postRepository, TagService tagService) {
        this.postRepository = postRepository;
        this.tagService = tagService;
    }

    @Override
    public List<Post> listAll() {
        return this.postRepository.findAll();
    }

    @Override
    public Post findById(Long id) {
        return this.postRepository.findById(id).orElseThrow(InvalidPostIdException::new);
    }

    @Override
    public Post create(String title, String content, LocalDate dateCreated, PostType postType, List<Long> tags) {
        List<Tag> tagList=tags.stream().map(id->this.tagService.findById(id)).collect(Collectors.toList());
        Post post=new Post(title,content,dateCreated,postType,tagList);
        return this.postRepository.save(post);
    }

    @Override
    public Post update(Long id, String title, String content, LocalDate dateCreated, PostType postType, List<Long> tags) {
        Post post=findById(id);
        List<Tag> tagList=tags.stream().map(ids->this.tagService.findById(ids)).collect(Collectors.toList());
       post.setTitle(title);
       post.setContent(content);
       post.setDateCreated(dateCreated);
       post.setPostType(postType);
       post.setTags(tagList);
        return this.postRepository.save(post);
    }

    @Override
    public Post delete(Long id) {
        Post post=findById(id);
        this.postRepository.delete(post);
        return post;
    }

    @Override
    public Post like(Long id) {
        Post post=findById(id);
        post.setLikes(post.getLikes()+1);
        return this.postRepository.save(post);
    }

    @Override
    public List<Post> filterPosts(Long tagId, PostType postType) {
        if(tagId==null && postType==null){
            return listAll();
        }else if(tagId!=null && postType!=null){
            Tag tag=this.tagService.findById(tagId);
            return this.postRepository.findByTagsContainsAndPostType(tag,postType);
        }else if(tagId!=null){
            Tag tag=this.tagService.findById(tagId);
            return this.postRepository.findByTagsContains(tag);
        }else {
            return this.postRepository.findByPostType(postType);
        }
    }
}
