package org.demo02.controller;

import org.demo02.entity.ConcertSeat;
import org.demo02.service.ConcertSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/concert-seat")
public class ConcertSeatController {
    @Autowired
    private ConcertSeatService concertSeatService;

    @GetMapping("/concert/{concertId}")
    public List<ConcertSeat> getSeatsByConcert(@PathVariable Long concertId) {
        return concertSeatService.getSeatsByConcert(concertId);
    }

    @PostMapping("/lock")
    public Map<String, Object> lockSeat(@RequestBody Map<String, Long> req) {
        boolean success = concertSeatService.lockSeat(req.get("concertId"), req.get("seatId"));
        Map<String, Object> res = new HashMap<>();
        res.put("success", success);
        return res;
    }

    @PostMapping("/release")
    public Map<String, Object> releaseSeat(@RequestBody Map<String, Long> req) {
        boolean success = concertSeatService.releaseSeat(req.get("concertId"), req.get("seatId"));
        Map<String, Object> res = new HashMap<>();
        res.put("success", success);
        return res;
    }

    @PostMapping("/sell")
    public Map<String, Object> sellSeat(@RequestBody Map<String, Long> req) {
        boolean success = concertSeatService.sellSeat(req.get("concertId"), req.get("seatId"));
        Map<String, Object> res = new HashMap<>();
        res.put("success", success);
        return res;
    }

} 