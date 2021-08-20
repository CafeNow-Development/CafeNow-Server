package com.java.cafenow.advice;

import com.java.cafenow.advice.exception.*;
import com.java.cafenow.util.response.domain.CommonResult;
import com.java.cafenow.util.response.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;
    private final MessageSource messageSource;

    // code정보에 해당하는 메시지를 조회합니다.
    private String getMessage(String code) {
        return getMessage(code, null);
    }
    // code정보, 추가 argument로 현재 locale에 맞는 메시지를 조회합니다.
    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    // Default
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("unKnown.code")), e.getMessage());
    }

    // 어드민을 못찾는 상태
    @ExceptionHandler(CAdminNotFoundException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    protected CommonResult userNotFoundException(HttpServletRequest request, CAdminNotFoundException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
    }

    // 로그인 실패 상태 (이메일)
    @ExceptionHandler(CEmailSignInFailedException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    protected CommonResult emailSigninFailed(HttpServletRequest request, CEmailSignInFailedException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("emailSigninFailed.code")), getMessage("emailSigninFailed.msg"));
    }

    // 로그인 실패 상태
    @ExceptionHandler(CAuthenticationEntryPointException.class)
    public CommonResult authenticationEntryPointException(HttpServletRequest request, CAuthenticationEntryPointException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("entryPointException.code")), getMessage("entryPointException.msg"));
    }

    // 권한 거부 상태
    @ExceptionHandler(AccessDeniedException.class)
    public CommonResult AccessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("accessDenied.code")), getMessage("accessDenied.msg"));
    }

    // 연결 실패 상태
    @ExceptionHandler(CCommunicationException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CommonResult communicationException(HttpServletRequest request, CCommunicationException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("communicationError.code")), getMessage("communicationError.msg"));
    }

    // 이미 어드민이 존재하는 상태 (회원가입)
    @ExceptionHandler(CAdminExistException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CommonResult communicationException(HttpServletRequest request, CAdminExistException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("existingUser.code")), getMessage("existingUser.msg"));
    }

    // 이미 매장이 존재하는 상태
    @ExceptionHandler(CStoreNotFoundException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CommonResult CStoreNotFoundException(HttpServletRequest request, CStoreNotFoundException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("CStoreNotFoundException.code")), getMessage("CStoreNotFoundException.msg"));
    }

    // 이미 사업자번호가 존재하는 상태
    @ExceptionHandler(CDuplicationBusinessNumber.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CommonResult CDuplicationBusinessNumber(HttpServletRequest request, CDuplicationBusinessNumber e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("CDuplicationBusinessNumber.code")), getMessage("CDuplicationBusinessNumber.msg"));
    }

    // DTO Valid를 할 경우
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult processValidationError(MethodArgumentNotValidException e) {
        return responseService.getFailResult(Integer.parseInt(getMessage("MethodArgumentNotValidException.code")), e.getAllErrors().get(0).getDefaultMessage());
    }
}