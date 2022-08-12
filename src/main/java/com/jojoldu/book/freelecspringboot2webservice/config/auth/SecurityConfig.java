package com.jojoldu.book.freelecspringboot2webservice.config.auth;

import com.jojoldu.book.freelecspringboot2webservice.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity        //Spring security 설정을 활성화 시켜줌.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .headers().frameOptions().disable()       //h2-console 화면을 활성화 시키기 위해 해당 옵션을 disable(장애를 입히다)한다.
                .and()
                    .authorizeRequests()
                //URL별 권한 관리를 설정하는 옵션의 시작점. authorizeRequests가 선언되어야만 antMatchers 옵션을 사용할 수 있다.
                    .antMatchers("/", "/css/**","/images/**","/js/**", "/h2-console/**").permitAll()
                //permitAll 옵션을 통해서 전체 열람 권한 부여.
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                // /api/v1/** 주소를 가진 API는 USER 권한만 가진 사람만 가능.
                .anyRequest().authenticated()
                //anyRequest는 설정된 값들 이외 나머지 url을 나타낸다. authenticated를 추가하여 나머지 url들은 모두 인증된 사용자들에게만 허용한다.
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                // 로그아웃 기능에 대한 여러 설정의 진입점. 로그아웃 성공시 / 로 이동.
                .and()
                    .oauth2Login()
                //oauth2Login 기능에 대한 진입점.
                        .userInfoEndpoint()
                //oAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정을 담당.
                            .userService(customOAuth2UserService);
                //소셜 로그인 성공시 후속 조치를 진행할 UserService 인터페이스 구현체를 등록. 리소스서버(소셜서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시 가능.
    }
}
