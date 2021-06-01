package com.mariworld.club.security.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
public class ClubMemberDTO extends User implements OAuth2User {
    private String email;
    private String name;
    private boolean fromSocial;
    private Map<String, Object> attr;

    //부모 생성자 + 내 생성자.
    //password는 부모가 이미 가지고 있는 멤버변수 = 내거!
    public ClubMemberDTO(String username
            , String password
            , boolean fromSocial
            , Collection<? extends GrantedAuthority> authorities){
        super(username, password, authorities);
        this.email = username;
        this.fromSocial = fromSocial;
    }

    public ClubMemberDTO(String email
            , String name
            , boolean fromSocial
            , Collection<? extends GrantedAuthority> authorities
            , Map<String, Object> attr){
        this(email, name, fromSocial,authorities);//위에 작성한 생성자
        this.attr = attr;
    }
    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }
}
