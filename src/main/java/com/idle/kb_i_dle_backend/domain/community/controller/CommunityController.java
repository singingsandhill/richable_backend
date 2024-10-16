package com.idle.kb_i_dle_backend.domain.community.controller;

import com.idle.kb_i_dle_backend.domain.community.dto.BoardDTO;
import com.idle.kb_i_dle_backend.domain.community.service.CommunityService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@Api(tags = "커뮤니티", description = "커뮤니티 설명")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;

    /**
     * autowired된 service를 이용해서 service단에서 데이터를 가공해서 받는다.
     *
     * @return
     */
//    @ApiOperation(value = "요약", notes = "설명")
    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<BoardDTO>> getAllBoards() {
        List<BoardDTO> boards = communityService.getAllBoard();
        return ResponseEntity.ok(boards);
    }

}
