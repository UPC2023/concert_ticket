package org.demo02.dao;

import org.apache.ibatis.annotations.Mapper;
import org.demo02.entity.Seat;
import java.util.List;

@Mapper
public interface SeatMapper {
    List<Seat> findByVenueId(Long venueId);
    Seat findById(Long id);
    int insert(Seat seat);
    int batchInsert(List<Seat> seats);
} 