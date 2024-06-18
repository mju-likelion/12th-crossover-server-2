package org.example.crossoverserver2.planeletter.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.crossoverserver2.planeletter.dto.request.WriteBoardDto;
import org.example.crossoverserver2.planeletter.dto.response.board.BoardListResponseData;
import org.example.crossoverserver2.planeletter.dto.response.board.BoardResponseData;
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
    //@AuthenticatedUser 어노테이션 추가해서 인증된 유저 받는 거 수정
    public ResponseEntity<ResponseDto<Void>> writeBoard(User user, @RequestBody @Valid WriteBoardDto writeBoardDto){
        boardService.writeBoard(user, writeBoardDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED,"게시글이 작성되었습니다."), HttpStatus.CREATED);
    }

    //게시글 목록 조회
    @GetMapping()
    public ResponseEntity<ResponseDto<BoardListResponseData>> getBoardListByPage(@RequestParam(value = "page", defaultValue = "0") int page){
        BoardListResponseData boardListResponseData = boardService.getBoardListByPage(page);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "게시글 목록을 조회합니다.", boardListResponseData),HttpStatus.OK);
    }

    //게시글 상세 조회
    @GetMapping("/{id}")
    //@AuthenticatedUser 어노테이션 추가해서 인증된 유저 받는 거 수정
    public ResponseEntity<ResponseDto<BoardResponseData>> getBoardById(User user, @PathVariable("id") UUID id){
        BoardResponseData boardResponseData = boardService.getBoardById(user, id);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"게시글을 조회합니다.", boardResponseData), HttpStatus.OK);
    }

    //게시글 삭제
    //@AuthenticatedUser 어노테이션 추가해서 인증된 유저 받는 거 수정
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> removeBoardById(User user, @PathVariable("id") UUID id){
        boardService.removeBoardById(user, id);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK,"게시글이 삭제되었습니다."), HttpStatus.OK);
    }
}
