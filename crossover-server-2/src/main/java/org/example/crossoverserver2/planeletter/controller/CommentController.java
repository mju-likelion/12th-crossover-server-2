package org.example.crossoverserver2.planeletter.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.crossoverserver2.planeletter.annotation.AuthenticatedUser;
import org.example.crossoverserver2.planeletter.dto.request.comment.WriteCommentDto;
import org.example.crossoverserver2.planeletter.dto.ResponseDto;
import org.example.crossoverserver2.planeletter.dto.response.comment.CommentListResponseData;
import org.example.crossoverserver2.planeletter.model.User;
import org.example.crossoverserver2.planeletter.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/boards/{boardId}/comments")
public class CommentController {

    public final CommentService commentService;

    //댓글 작성
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> writeComment(@AuthenticatedUser User user, @PathVariable UUID boardId, @RequestBody @Valid WriteCommentDto writeCommentDto){
        commentService.writeComment(user,boardId,writeCommentDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED,"댓글을 성공적으로 작성하였습니다."), HttpStatus.CREATED);
    }
    //댓글 조회
    //댓글도 페이지네이션?
    @GetMapping
    public ResponseEntity<ResponseDto<CommentListResponseData>> getCommentList(@PathVariable("boardId") UUID boardId){
        CommentListResponseData commentListResponseData = commentService.getCommentList(boardId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "댓글 목록을 조회합니다.", commentListResponseData),HttpStatus.OK);
    }
    //댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> removeCommentById(@AuthenticatedUser User user
            ,@PathVariable("boardId") UUID boardId, @PathVariable("id") UUID id){
        commentService.removeCommentById(user, boardId, id);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"댓글이 삭제되었습니다."), HttpStatus.OK);
    }
}
