package org.demo02.service;

import org.demo02.entity.ConcertSeat;
import java.util.List;

public interface ConcertSeatService {
    void initConcertSeats(Long concertId, Long venueId);
    boolean lockSeat(Long concertId, Long seatId);
    boolean releaseSeat(Long concertId, Long seatId);
    boolean sellSeat(Long concertId, Long seatId);
    List<ConcertSeat> getSeatsByConcert(Long concertId);
} 