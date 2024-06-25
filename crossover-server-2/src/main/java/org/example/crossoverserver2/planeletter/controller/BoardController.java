package org.example.crossoverserver2.planeletter.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.crossoverserver2.planeletter.annotation.AuthenticatedUser;
import org.example.crossoverserver2.planeletter.dto.request.board.WriteBoardDto;
import org.example.crossoverserver2.planeletter.dto.response.board.BoardDto;
import org.example.crossoverserver2.planeletter.dto.response.board.BoardListResponseData;
import org.example.crossoverserver2.planeletter.dto.ResponseDto;
import org.example.crossoverserver2.planeletter.model.User;
import org.example.crossoverserver2.planeletter.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    public final BoardService boardService;

    //게시글 작성
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> writeBoard(@AuthenticatedUser User user, @RequestBody @Valid WriteBoardDto writeBoardDto){

        boardService.writeBoard(user, writeBoardDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED,"게시글이 작성되었습니다."), HttpStatus.CREATED);
    }

    //게시글 목록 조회
    @GetMapping
    public ResponseEntity<ResponseDto<BoardListResponseData>> getBoardListByPage(@RequestParam(value = "page", defaultValue = "0") int page){
        BoardListResponseData boardListResponseData = boardService.getBoardListByPage(page);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "게시글 목록을 조회합니다.", boardListResponseData),HttpStatus.OK);
    }

    //게시글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<BoardDto>> getBoardById(@AuthenticatedUser User user, @PathVariable("id") UUID id){
        BoardDto boardDto = boardService.getBoardById(user, id);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"게시글을 조회합니다.", boardDto), HttpStatus.OK);
    }

    //게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> removeBoardById(@AuthenticatedUser User user, @PathVariable("id") UUID id){
        boardService.removeBoardById(user, id);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"게시글이 삭제되었습니다."), HttpStatus.OK);
    }
}
