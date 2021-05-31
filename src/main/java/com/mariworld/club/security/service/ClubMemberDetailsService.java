package com.mariworld.club.security.service;

import com.mariworld.club.entity.ClubMember;
import com.mariworld.club.entity.ClubMemberRole;
import com.mariworld.club.repository.ClubMemberRepository;
import com.mariworld.club.security.dto.ClubMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubMemberDetailsService implements UserDetailsService {
    @Autowired
    private final ClubMemberRepository clubMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<ClubMember> result
                = clubMemberRepository.findByEmail(username, false);

        //isPresent()에서 -> 존재하지 않을 수 있다. 그 경우 예외 던진다. 이거 catch는 누가하지?
        if(result.isEmpty()){
            throw new UsernameNotFoundException("Check Email or Social");
        }
        ClubMember clubMember = result.get();

        ClubMemberDTO clubMemberDTO = new ClubMemberDTO(
                clubMember.getEmail(),
                clubMember.getPassword(),
                clubMember.isFromSocial(),
                clubMember.getRoleSet()
                        .stream()
                        .map(r-> new SimpleGrantedAuthority("ROLE_"+r.name()))
                        .collect(Collectors.toSet())
        );
        clubMemberDTO.setName(clubMember.getName());

        return clubMemberDTO;
    }
}
