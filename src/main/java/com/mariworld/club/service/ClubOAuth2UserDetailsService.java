package com.mariworld.club.service;

import com.mariworld.club.entity.ClubMember;
import com.mariworld.club.entity.ClubMemberRole;
import com.mariworld.club.repository.ClubMemberRepository;
import com.mariworld.club.dto.ClubMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClubOAuth2UserDetailsService extends DefaultOAuth2UserService {

    private final ClubMemberRepository clubMemberRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
      OAuth2User oAuth2User  = super.loadUser(userRequest);
      oAuth2User.getAuthorities().forEach(System.out::println);
      oAuth2User.getAttributes().forEach((k,v)->log.info(k+" : "+ v));

      String clientName  = userRequest.getClientRegistration().getClientName();
      String email = "";

      if(clientName.equals("Google")){
          email = oAuth2User.getAttribute("email");
      }
      ClubMember clubMember = saveSocialMember(email);

      ClubMemberDTO clubMemberDTO = new ClubMemberDTO(
              email
              , clubMember.getPassword()
              ,true
              , clubMember.getRoleSet()
                .stream()
                .map(r->new SimpleGrantedAuthority("ROLE_"+r.name()))
                .collect(Collectors.toSet())
              , oAuth2User.getAttributes()
      );
      clubMemberDTO.setName(clubMember.getName());

        return clubMemberDTO;
    }

    private ClubMember saveSocialMember(String email) {
        Optional<ClubMember> result
                = clubMemberRepository.findByEmail(email, true);
        if(result.isPresent()) return result.get(); //이미 가입이력이 있는 회원. DB에 저장되어 있다
        ClubMember clubMember = ClubMember.builder() //그렇지 않으면 - 회원가입 시킨다
                .email(email)
                .name(email)
                .password(passwordEncoder.encode("1111"))
                .fromSocial(true)
                .build();
        clubMember.addClubMemberRole(ClubMemberRole.USER);
        clubMemberRepository.save(clubMember);
        return clubMember;
    }
}
