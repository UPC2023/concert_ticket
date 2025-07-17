package org.demo02.dao;

import org.apache.ibatis.annotations.Mapper;
import org.demo02.entity.ConcertSeat;
import java.util.List;

@Mapper
public interface ConcertSeatMapper {
    int insert(ConcertSeat concertSeat);
    int batchInsert(List<ConcertSeat> seats);
    int updateStatus(Long concertId, Long seatId, String status);
    ConcertSeat findByConcertAndSeat(Long concertId, Long seatId);
    List<ConcertSeat> findByConcertId(Long concertId);
    List<ConcertSeat> getSeatsByConcert(Long concertId);
} 