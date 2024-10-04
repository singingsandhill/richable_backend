package com.idle.kb_i_dle_backend.domain.finance.repository;

import com.idle.kb_i_dle_backend.domain.finance.entity.Bank;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {

    List<Bank> findAllByUidAndDeleteDateIsNull(Optional<Member> uid);

    // 특정 날짜 이전의 모든 데이터를 가져오는 쿼리 메서드
    List<Bank> findAllByUidAndAddDateBefore(Member uid, Date endDate);

    @Query("SELECT ub FROM Bank ub WHERE ub.uid = :uid AND ub.addDate < :date")
    List<Bank> findInvestmentsByUidAndDate(@Param("uid") Integer uid, @Param("date") Date date);

    // 소득 전체 조회
    List<Bank> findByUid(Optional<Member> uid);

    // 새로운 메서드: 특정 사용자의 "입출금" 또는 "현금" 카테고리에 해당하는 은행 계좌 조회
    @Query("SELECT ub FROM Bank ub WHERE ub.uid = :uid AND ub.category IN ('입출금', '현금') AND ub.deleteDate IS NULL")
    List<Bank> findByUidAndSpecificCategoriesAndDeleteDateIsNull(@Param("uid") Optional<Member> uid);
}