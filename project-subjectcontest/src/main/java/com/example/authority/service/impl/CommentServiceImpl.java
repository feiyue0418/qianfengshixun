package com.example.authority.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.authority.entity.Comment;
import com.example.authority.mapper.CommentMapper;
import com.example.authority.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author 程序员云翼
 * @since 2024-05-01
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Override
    public void deepDelete(Integer id) {
        commentMapper.deleteById(id);
        List<Comment> commentList = commentMapper.selectListByPid(id);
        if(!CollectionUtils.isEmpty(commentList)){
            for (Comment comment : commentList) {
                deepDelete(comment.getId());
            }
        }
    }

    @Override
    public List<Comment> selectTree(Integer fid, String module) {
        List<Comment> commentList = commentMapper.selectTree(fid, module);
        List<Comment> rootList = commentList.stream().filter(comment -> comment.getParentId() == null).collect(Collectors.toList());
        for (Comment root : rootList) {
            Integer rootId = root.getId();
            List<Comment> childrenList = commentList.stream().filter(comment -> rootId.equals(comment.getRootId())).collect(Collectors.toList());
            root.setChildren(childrenList);
        }
        return rootList;
    }

    @Override
    public Integer selectCount(Integer fid, String module) {
        return commentMapper.selectTree(fid, module).size();
    }

    @Override
    public void add(Comment comment) {
        if(comment.getParentId() != null){  //这是回复的评论
            Comment pComment = commentMapper.selectById(comment.getParentId());
            if(pComment == null){
                return;
            }
            Integer pRootId = pComment.getRootId();
            if(pRootId != null){
                comment.setRootId(pRootId);
            }else{
                comment.setRootId(pComment.getId());
            }
        }

        commentMapper.insert(comment);
    }
}
