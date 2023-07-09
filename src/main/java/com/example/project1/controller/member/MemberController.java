package com.example.project1.controller.member;

import com.example.project1.domain.jwt.TokenDTO;
import com.example.project1.domain.member.MemberDTO;
import com.example.project1.service.jwt.RefreshTokenService;
import com.example.project1.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/**")
public class MemberController {

    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;

    // 회원 가입
    @PostMapping("/")
    // BindingResult 타입의 매개변수를 지정하면 BindingResult 매개 변수가 입력값 검증 예외를 처리한다.
    public ResponseEntity<?> join(@Validated @RequestBody MemberDTO memberDTO,
                                  BindingResult result) throws Exception{

        // 입력값 검증 예외가 발생하면 예외 메시지를 응답한다.
        if(result.hasErrors()) {
            log.info("BindingResult error : " + result.hasErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getClass().getSimpleName());
        }

        try {
            String join = memberService.signUp(memberDTO);
            // 아이디가 있으면 아이디가 존재합니다 리턴
            // 아이디가 없으면 회원가입에 성공했습니다가 리턴
            return ResponseEntity.ok().body(join);
        } catch (Exception e) {
            log.error("예외 : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 회원 조회
    @GetMapping("/{userId}")
    public ResponseEntity<MemberDTO> search(@PathVariable Long userId) throws Exception {
        try {
            MemberDTO search = memberService.search(userId);
            return ResponseEntity.ok().body(search);
        } catch (NullPointerException e) {
            log.info("회원이 존재하지 않습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody MemberDTO memberDTO) throws Exception {
        try {
            return memberService.login(memberDTO.getUserEmail(), memberDTO.getUserPw());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // refresh로 access 토큰 재발급
    // @RequsetHeader"Authorization")은 Authorization 헤더에서 값을 추출합니다.
    // 일반적으로 리프레시 토큰은 Authorization 헤더의 값으로 전달되며,
    // Bearer <token> 형식을 따르는 경우가 일반적입니다. 여기서 <token> 부분이 실제 리프레시 토큰입니다
    // 로 추출하면 다음과 같이 문자열로 나온다.
    // Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String token) throws Exception {
        try {
            ResponseEntity<TokenDTO> accessToken = refreshTokenService.createAccessToken(token);
            return ResponseEntity.ok().body(accessToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // 로그아웃
    @GetMapping("/logOut")
    public String logOut(HttpServletRequest request,
                         HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request,
                response,
                SecurityContextHolder.getContext().getAuthentication());
        return "로그아웃하셨습니다.";
    }

    // 회원정보 수정
    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody MemberDTO memberDTO) throws Exception{
        try {
            MemberDTO update = memberService.update(memberDTO);
            return ResponseEntity.ok().body(update);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("잘못된 요청");
        }
    }
}