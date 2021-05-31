package com.mariworld.club.repository;

import com.mariworld.club.entity.ClubMember;
import com.mariworld.club.entity.ClubMemberRole;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember,String> {

    @EntityGraph(attributePaths = {"roleSet"} ,type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from ClubMember m where m.email =:email and m.fromSocial =:fromSocial")
    Optional<ClubMember> findByEmail(@Param("email") String email,
                                     @Param("fromSocial") boolean fromSocial);
}
