package org.example.crossoverserver2.planeletter.service;

import lombok.AllArgsConstructor;
import org.example.crossoverserver2.planeletter.dto.request.comment.WriteCommentDto;
import org.example.crossoverserver2.planeletter.dto.response.PaginationDto;
import org.example.crossoverserver2.planeletter.dto.response.comment.CommentDto;
import org.example.crossoverserver2.planeletter.dto.response.comment.CommentListResponseData;
import org.example.crossoverserver2.planeletter.exception.ForbiddenException;
import org.example.crossoverserver2.planeletter.exception.NotFoundException;
import org.example.crossoverserver2.planeletter.exception.errorcode.ErrorCode;
import org.example.crossoverserver2.planeletter.model.Board;
import org.example.crossoverserver2.planeletter.model.Comment;
import org.example.crossoverserver2.planeletter.model.User;
import org.example.crossoverserver2.planeletter.repository.BoardRepository;
import org.example.crossoverserver2.planeletter.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class CommentService {

    public final CommentRepository commentRepository;
    public final BoardRepository boardRepository;

    private final static int COMMENT_PAGE_SIZE = 10;

    //댓글 작성
    public void writeComment(User user, UUID boardId, WriteCommentDto writeCommentDto){
        existsBoard(boardId);
        Comment newComment = Comment.builder()
                .user(user)
                .board(boardRepository.findBoardById(boardId))
                .content(writeCommentDto.getContent())
                .build();
        commentRepository.save(newComment);
    }

    //댓글 조회
    public CommentListResponseData getCommentList(UUID boardId, int page){
        existsBoard(boardId);

        int pageSize = COMMENT_PAGE_SIZE;
        Sort sort = Sort.by(Sort.Order.asc("createdAt"));  //댓글 최신순으로 정렬
        Pageable pageable = PageRequest.of(page, pageSize, sort);   //페이지의 번호, 사이즈, 정렬 조건 설정

        Page<Comment> commentPage = commentRepository.findAllByBoard(boardRepository.findBoardById(boardId), pageable);

        if(commentPage.getTotalPages() <= page && page!=0){
            throw new NotFoundException(ErrorCode.NOT_FOUND_PAGE);
        }

        return CommentListResponseData.commentListResponseData(commentPage);
    }

    //댓글 삭제
    public void removeCommentById(User user, UUID boardId, UUID id){
        existsBoard(boardId);
        Comment comment = findCommentById(id);
        checkUser(user, comment);
        commentRepository.deleteById(id);
    }

    //게시글 존재 여부 확인
    private void existsBoard(UUID boardId){
        if (!boardRepository.existsById(boardId)){
            throw new NotFoundException(ErrorCode.NOT_FOUND_BOARD);
        }
    }

    private Comment findCommentById(UUID commentId){
        return commentRepository.findById(commentId).orElseThrow(()-> new NotFoundException(ErrorCode.NOT_FOUND_COMMENT));
    }

    //해당 접근 유저가 댓글 작성자와 일치하는지 확인
    private void checkUser(User user, Comment comment){
        if(!comment.getUser().getId().equals(user.getId())){
            throw new ForbiddenException(ErrorCode.NO_ACCESS, "해당 댓글 접근 권한이 없습니다.");
        }
    }
}
