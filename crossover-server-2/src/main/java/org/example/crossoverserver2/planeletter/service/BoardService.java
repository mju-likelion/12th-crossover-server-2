package org.example.crossoverserver2.planeletter.service;

import lombok.AllArgsConstructor;
import org.example.crossoverserver2.planeletter.dto.request.board.WriteBoardDto;
import org.example.crossoverserver2.planeletter.dto.response.board.BoardDto;
import org.example.crossoverserver2.planeletter.dto.response.board.BoardListResponseData;
import org.example.crossoverserver2.planeletter.dto.response.board.BoardResponseData;
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

        if(boardPage.getTotalPages() <= page){
            throw new NotFoundException(ErrorCode.NOT_FOUND_PAGE);
        }

        return BoardListResponseData.boardListResponseData(boardPage);
    }

    //게시글 상세 조회
    public BoardResponseData getBoardById(User user, UUID id){
        Board board = findBoardById(id);

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
//        existsBoard(id);        //해당 게시글 존재 여부
//        checkUser(user, id);    //해당 게시글 접근 가능 여부
//        boardRepository.deleteById(id);
        //여기도 살짝 변경했습니다.
        //사실 이전 코드와 큰 차이는 없겠지만, 이렇게 작성하시면 deleteById메소드를 새로 작성할 필요 없이
        //JpaRepository에서 기본적으로 제공하는 delete메소드를 사용할 수 있습니다
        Board board = findBoardById(id);
        checkUser(user, id);
        boardRepository.delete(board);
    }

    //게시글 존재 여부 확인
    //아래 주석은 현서님이 이해하시기 편하시라고 작성했어요 한번 보시고 머지 하시기 전에 다 지워주시면 됩니당
    //이 코드는 boardRepository에 존재하는 기본 findById메소드를 사용해서 boardId로 Board를 찾는 메소드입니다.
    //findById는 기본적으로 반환 타입이 Optional이기 때문에 이 메소드로 예외처리까지 한번에 진행할 수 있어서 이렇게 작성하는 것도 좋을 것 같습니다!
    private Board findBoardById(UUID boardId){
        return boardRepository.findById(boardId).orElseThrow(()-> new NotFoundException(ErrorCode.NOT_FOUND_BOARD));
    }

    //접근 유저와 게시글 작성자가 일치한지 확인
    public boolean checkUser(User user, UUID id){
        if(boardRepository.existsByUserAndId(user,id)){
            return true;
        } throw new ForbiddenException(ErrorCode.NO_ACCESS, "해당 게시글에 접근 권한이 없습니다.");
    }
}