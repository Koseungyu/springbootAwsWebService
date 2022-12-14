package com.jojoldu.book.freelecspringboot2webservice.web;

import com.jojoldu.book.freelecspringboot2webservice.config.auth.SecurityConfig;
import lombok.With;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)                        //스프링부트와 junit 사이의 연결자 역할(SpringExtension이라는 실행자를 사용)
@WebMvcTest(controllers = HelloController.class,
excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
})
//스프링어노테이션 중 WEB(Spring MVC)에만 집중할 수 있는 어노테이션(@Controller, @ControllerAdvice 사용가능)
public class HelloControllerTest  {

    @Autowired
    private MockMvc mvc;                            //웹 API를 테스트 할때 사용. 스프링 MVC의 시작점. 이 클래스를 통해 HTTP GET, POST 등에 대한 API 테스트 가능

    @WithMockUser(roles = "USER")
    @Test
    public void Hello가_리턴된다() throws Exception{
        String hello = "hello";

        mvc.perform(get("/hello"))         //mock MVC를 통해 /hello 주소로 HTTP GET 요청을 한다.
                .andExpect(status().isOk())          //mvc.perform의 결과를 검증한다. HTTP header의 status를 검증한다. ok 즉 200 상태인지 검등한다.
                .andExpect(content().string(hello)); //mvc.perform의 결과를 검증한다. controller에서 "hello"를 리턴하기 때문에 이 값이 맞는지 검증한다.
    }

    @WithMockUser(roles = "USER")
    @Test
    public void helloDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")                              //mock MVC를 통해 /hello/dto 주소로 HTTP GET 요청을 한다.
                        .param("name",name)                            //Api 테스트를 할때 사용될 요청 파라미터 설정. 값은 String만 되기에 int 값은 valueOf를 넣어줌.
                        .param("amount",String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(name)))            //jsonPath는 json응답값을 필드별로 검증할 수 있는 메소드. $를 기준으로 필드명을 명시.
                .andExpect(jsonPath("$.amount", is(amount)));      //name과 amount를 검증하니 $.name, $.amount로 검증.
    }
}