package org.demo02.dao;

import org.apache.ibatis.annotations.Mapper;
import org.demo02.entity.Venue;
import java.util.List;

@Mapper
public interface VenueMapper {
    int insert(Venue venue);
    int update(Venue venue);
    int deleteById(Long id);
    Venue findById(Long id);
    List<Venue> findAll();
} 