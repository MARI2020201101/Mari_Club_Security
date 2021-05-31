package com.mariworld.club.security.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class ClubMemberDTO extends User {
    private String email;
    private String name;
    private boolean fromSocial;

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
}
