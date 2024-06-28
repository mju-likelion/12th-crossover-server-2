package org.example.crossoverserver2.planeletter.service;

import lombok.AllArgsConstructor;
import org.example.crossoverserver2.planeletter.dto.request.board.WriteBoardDto;
import org.example.crossoverserver2.planeletter.dto.response.board.BoardDto;
import org.example.crossoverserver2.planeletter.dto.response.board.BoardListResponseData;
import org.example.crossoverserver2.planeletter.dto.response.PaginationDto;
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
    private final static int BOARD_PAGE_SIZE = 10;

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
        int pageSize = BOARD_PAGE_SIZE;  //한 페이지에 게시글 10개씩
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));  //작성순으로 정렬
        Pageable pageable = PageRequest.of(page, pageSize, sort);   //페이지 번호, 페이지 크기, 정렬 조건 설정

        Page<Board> boardPage = boardRepository.findAll(pageable);  //해당 페이징 데이터를 모두 가져옴

        if(boardPage.getTotalPages() <= page && page!=0){
            throw new NotFoundException(ErrorCode.NOT_FOUND_PAGE);
        }

        return BoardListResponseData.boardListResponseData(boardPage);
    }

    //게시글 상세 조회
    public BoardDto getBoardById(User user, UUID id){
        Board board = findBoardById(id);

        return BoardDto.boardDto(board);
    }

    //게시글 삭제
    public void removeBoardById(User user, UUID id){
        Board board = findBoardById(id);
        checkUser(user, board);
        boardRepository.delete(board);
    }

    //게시글 존재 여부 확인
    private Board findBoardById(UUID boardId){
        return boardRepository.findById(boardId).orElseThrow(()-> new NotFoundException(ErrorCode.NOT_FOUND_BOARD));
    }

    //접근 유저와 게시글 작성자가 일치한지 확인
    private void checkUser(User user, Board board){
        if(!board.getUser().getId().equals(user.getId())){
            throw new ForbiddenException(ErrorCode.NO_ACCESS, "해당 게시글에 접근 권한이 없습니다.");
        }
    }
}