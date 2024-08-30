//package com.sol.admin.common.exception;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sol.admin.common.util.Result;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.io.IOException;
//
//
///**
// * @author sol
// * @date 2024/08/21
// */
//@ControllerAdvice
//@Slf4j
//public class GlobalExceptionHandler {
//
//    @Autowired
//    HttpServletResponse response;
//
//    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";
//    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
//    @Autowired
//    MessageSource messageSource;
//    /**
//     * 处理自定义的业务异常
//     * @param req 请求
//     * @param e 异常
//     */
//    @ExceptionHandler(value = GlobalException.class)
//    @ResponseBody
//    public void globalExceptionHandler(HttpServletRequest req, GlobalException e) throws IOException {
//        log.error("发生业务异常！原因是：" + e);
//        response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
//        response.setStatus(HttpStatus.OK.value());
//        response.getWriter().println(OBJECT_MAPPER.writeValueAsString(new ResponseEntity<>("发生业务异常！原因是: "+e.getMsg(), HttpStatus.OK)));
//    }
//
//    /**
//     * 处理空指针的异常
//     * @param req 请求
//     * @param e 异常
//     */
//    @ExceptionHandler(value =NullPointerException.class)
//    @ResponseBody
//    public void exceptionHandler(HttpServletRequest req, NullPointerException e) throws IOException {
//        log.error("发生空指针异常！原因是:" + e);
//        response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
//        response.setStatus(HttpStatus.OK.value());
//        response.getWriter().println(OBJECT_MAPPER.writeValueAsString(new ResponseEntity<>("发生空指针异常！原因是:" + e.getMessage(), HttpStatus.OK)));
//    }
//
//
//    /**
//     * 处理其他异常
//     * @param req 请求
//     * @param e 异常
//     */
//    @ExceptionHandler(value =Exception.class)
//    @ResponseBody
//    public void exceptionHandler(HttpServletRequest req, Exception e) throws IOException {
//        log.error("未知异常！原因是:" + e);
//        response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
//        response.setStatus(HttpStatus.OK.value());
//        response.getWriter().println(OBJECT_MAPPER.writeValueAsString(new ResponseEntity<>("未知异常！原因是:" + e.getMessage(), HttpStatus.OK)));
//    }
//
//    /**
//     * 处理其他异常
//     * @param req 请求
//     * @param e 异常
//     */
//    @ExceptionHandler(value =JwtException.class)
//    @ResponseBody
//    public void jwtHandler(HttpServletRequest req, Exception e) throws IOException {
//        log.error("未登录!" + e);
//        response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        response.getWriter().println(OBJECT_MAPPER.writeValueAsString(new ResponseEntity<>("未登录！" + e.getMessage(), HttpStatus.OK)));
//    }
//}
