package org.demo02.controller;

import org.demo02.dao.SeatMapper;
import org.demo02.entity.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/seat")
public class SeatController {
    @Autowired
    private SeatMapper seatMapper;

    @GetMapping("/venue/{venueId}")
    public List<Seat> getSeatsByVenue(@PathVariable Long venueId) {
        return seatMapper.findByVenueId(venueId);
    }

    @PostMapping("/add")
    public Map<String, Object> addSeat(@RequestBody Seat seat) {
        Map<String, Object> res = new HashMap<>();
        int result = seatMapper.insert(seat);
        res.put("success", result > 0);
        return res;
    }

    @PostMapping("/batch-add")
    public Map<String, Object> batchAddSeats(@RequestBody List<Seat> seats) {
        Map<String, Object> res = new HashMap<>();
        int result = seatMapper.batchInsert(seats);
        res.put("success", result > 0);
        return res;
    }
} 