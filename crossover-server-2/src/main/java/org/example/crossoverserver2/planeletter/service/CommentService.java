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
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));  //작성순 정렬
        Pageable pageable = PageRequest.of(page, pageSize, sort);   //페이지의 번호, 사이즈, 정렬 조건 설정

        Page<Comment>  commentPage = commentRepository.findAllByBoard(boardRepository.findBoardById(boardId), pageable);

        if(commentPage.getTotalPages() <= page){
            throw new NotFoundException(ErrorCode.NOT_FOUND_PAGE);
        }

        PaginationDto paginationDto = PaginationDto.builder()
                .totalPage(commentPage.getTotalPages())
                .currentPage(commentPage.getNumber())
                .build();

        List<CommentDto> comments = commentPage.stream()
                //CommentDto 형식으로 변환
                .map(comment -> CommentDto.builder()
                        .name(comment.getUser().getName())
                        .content(comment.getContent())
                        .createdTime(comment.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        CommentListResponseData commentListResponseData = CommentListResponseData.builder()
                .commentList(comments)
                .paginationDto(paginationDto)
                .build();
        return commentListResponseData;
    }

    //댓글 삭제
    public void removeCommentById(User user, UUID boardId, UUID id){
        existsBoard(boardId);
        Comment comment = findCommentById(id);
        checkUser(user, comment);
        commentRepository.deleteById(id);
    }

    //게시글 존재 여부 확인
    //사실 이 코드는 굳이? 싶을 수도 있어서 기존 코드를 사용하셔도 괜찮은데, 기본적으로 return true를 반환할 필요가 없기 때문에 void로 작성하시는 게 보기에 조금 더 좋을 수 있을 것 같아서 건드려 봤습니다.
//    public boolean existsBoard(UUID boardId){
//        if(boardRepository.existsById(boardId)){
//            return true;
//        } throw new NotFoundException(ErrorCode.NOT_FOUND_BOARD);
//    }
    private void existsBoard(UUID boardId){
        if (!boardRepository.existsById(boardId)){
            throw new NotFoundException(ErrorCode.NOT_FOUND_BOARD);
        }
    }

    private Comment findCommentById(UUID commentId){
        return commentRepository.findById(commentId).orElseThrow(()-> new NotFoundException(ErrorCode.NOT_FOUND_COMMENT));
    }

    //해당 접근 유저가 댓글 작성자와 일치하는지 확인
    //작성자를 확인하는 코드의 경우에 파라미터로 유저와 comment를 직접 받는 게 이후에 코드가 달라지더라도 재사용하기 좋다고 대현님이 말씀해주셔서 이렇게 바꿔봤습니다.
//    public boolean checkUser(User user, UUID id){
//        if(commentRepository.existsByUserAndId(user, id)){
//            return true;
//        } throw new ForbiddenException(ErrorCode.NO_ACCESS, "해당 댓글 접근 권한이 없습니다.");
//    }
    private void checkUser(User user, Comment comment){
        if(!comment.getUser().getId().equals(user.getId())){
            throw new ForbiddenException(ErrorCode.NO_ACCESS, "해당 댓글 접근 권한이 없습니다.");
        }
    }
}
