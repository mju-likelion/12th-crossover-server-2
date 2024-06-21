package org.example.crossoverserver2.planeletter.service;

import lombok.AllArgsConstructor;
import org.example.crossoverserver2.planeletter.dto.request.board.WriteBoardDto;
import org.example.crossoverserver2.planeletter.dto.response.board.BoardDto;
import org.example.crossoverserver2.planeletter.dto.response.board.BoardListResponseData;
import org.example.crossoverserver2.planeletter.dto.response.board.BoardResponseData;
import org.example.crossoverserver2.planeletter.dto.response.board.PaginationDto;
import org.example.crossoverserver2.planeletter.exception.ForbiddenException;
import org.example.crossoverserver2.planeletter.exception.NotFoundException;
import org.example.crossoverserver2.planeletter.exception.errorcode.ErrorCode;
import org.example.crossoverserver2.planeletter.model.Board;
import org.example.crossoverserver2.planeletter.model.User;
import org.example.crossoverserver2.planeletter.repository.BoardRepository;
import org.example.crossoverserver2.planeletter.repository.UserRepository;
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
public class BoardService {

    public final BoardRepository boardRepository;
    public final UserRepository userRepository;

    //게시글 작성
    public void writeBoard(User user, WriteBoardDto writeBoardDto){
        Board newBoard = Board.builder().
                user(user).
                title(writeBoardDto.getTitle()).
                content(writeBoardDto.getContent()).
                build();
        boardRepository.save(newBoard);
    }

    //게시글 목록 조회
    public BoardListResponseData getBoardListByPage(int page){
        int pageSize = 10;  //한 페이지에 게시글 10개씩
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));  //작성순으로 정렬
        Pageable pageable = PageRequest.of(page, pageSize, sort);   //페이지 번호, 페이지 크기, 정렬 조건 설정

        Page<Board> boardPage = boardRepository.findAll(pageable);  //해당 페이징 데이터를 모두 가져옴

        PaginationDto pagination = PaginationDto.builder()
                .totalPage(boardPage.getTotalPages())
                .currentPage(boardPage.getNumber())
                .build();

        List<BoardDto> boards = boardPage.stream()
                //BoardDto 형식으로 변환
                .map(board -> BoardDto.builder()
                        .name(board.getUser().getName())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .createdTime(board.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
        BoardListResponseData boardListResponseData = BoardListResponseData.builder().boardList(boards).pagination(pagination).build();
        return boardListResponseData;
    }

    //게시글 상세 조회
    public BoardResponseData getBoardById(User user, UUID id){
        existsBoard(id);        //해당 게시글 존재 여부
        checkUser(user, id);    //해당 게시글 접근 가능 여부
        Board board = boardRepository.findBoardById(id);
        BoardResponseData boardResponseData = BoardResponseData.builder()
                        .name(board.getUser().getName())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .createdTime(board.getCreatedAt())
                        .build();
        return boardResponseData;
    }

    //게시글 삭제
    public void removeBoardById(User user, UUID id){
        existsBoard(id);        //해당 게시글 존재 여부
        checkUser(user, id);    //해당 게시글 접근 가능 여부
        boardRepository.deleteById(id);
    }

    //게시글 존재 여부 확인
    public boolean existsBoard(UUID id){
        if (boardRepository.existsById(id)){
            return true;
        } throw new NotFoundException(ErrorCode.NOT_FOUND_BOARD);
    }

    //접근 유저와 게시글 작성자가 일치한지 확인
    public boolean checkUser(User user, UUID id){
        if(boardRepository.existsByUserAndId(user,id)){
            return true;
        } throw new ForbiddenException(ErrorCode.NO_ACCESS, "해당 게시글에 접근 권한이 없습니다.");
    }
}