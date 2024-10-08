package com.idle.kb_i_dle_backend.domain.finance.repository;

import com.idle.kb_i_dle_backend.domain.finance.entity.Spot;
import com.idle.kb_i_dle_backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SpotRepository extends JpaRepository<Spot, Long> {
    // deleteDate가 현재 날짜보다 이후이고, 카테고리가 특정 카테고리인 Spot의 가격 합계를 구함
    List<Spot> findByUidAndCategoryAndDeleteDateIsNull(Member uid, String category);

    // 삭제되지 않은 현물 자산 전체 조회
    List<Spot> findByUidAndDeleteDateIsNull(Member uid);

    // 특정 index값의 정보 조회
    Optional<Spot> findByIndexAndDeleteDateIsNull(@Param("index")Integer index);

    List<Spot> findByUidAndAddDateBefore(Member uid, Date date);

    @Query(value = "SELECT month(s.add_date) as month, SUM(s.price) as totalAmount " +
            "FROM asset.spot s WHERE s.uid = :uid " +
            "GROUP BY month(s.add_date)", nativeQuery = true)
    List<Object[]> findMonthlySpotAssets(@Param("uid") Member uid);
    
}
