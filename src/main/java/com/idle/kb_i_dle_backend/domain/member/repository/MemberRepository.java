package com.idle.kb_i_dle_backend.domain.member.repository;

import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByUid(Integer uid);

    Optional<Member> findById(String id);

//    @Query("SELECT new com.idle.kb_i_dle_backend.domain.member.dto.MemberDTO(m.uid, m.id, m.email, m.nickname, m.auth,null ,null,null,false,null ) FROM Member m WHERE m.nickname = :nickname")
    Member findByNickname(@Param("nickname") String nickname);

    Member findByEmail(String email);

    boolean existsById(String id);

    // 같은 나이대(10대, 20대, 30대 등) 사용자들의 uid를 찾는 쿼리
    @Query("SELECT m.uid FROM Member m WHERE FLOOR((YEAR(CURRENT_DATE) - m.birth_year) / 10) = :ageGroup")
    List<Integer> findUidsByAgeGroup(@Param("ageGroup") int ageGroup);

    boolean deleteMemberById(Optional<Member> id);

    boolean save(Optional<Member> id);
}
