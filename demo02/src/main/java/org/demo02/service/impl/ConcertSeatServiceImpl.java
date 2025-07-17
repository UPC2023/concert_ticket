package org.demo02.service.impl;

import org.demo02.dao.ConcertSeatMapper;
import org.demo02.dao.SeatMapper;
import org.demo02.entity.ConcertSeat;
import org.demo02.entity.Seat;
import org.demo02.service.ConcertSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConcertSeatServiceImpl implements ConcertSeatService {
    @Autowired
    private ConcertSeatMapper concertSeatMapper;
    @Autowired
    private SeatMapper seatMapper;

    @Override
    public void initConcertSeats(Long concertId, Long venueId) {
        List<Seat> seats = seatMapper.findByVenueId(venueId);
        List<ConcertSeat> concertSeats = new java.util.ArrayList<>();
        for (Seat seat : seats) {
            ConcertSeat cs = new ConcertSeat();
            cs.setConcertId(concertId);
            cs.setSeatId(seat.getId());
            cs.setStatus("可售");
            concertSeats.add(cs);
        }
        concertSeatMapper.batchInsert(concertSeats);
    }

    @Override
    public boolean lockSeat(Long concertId, Long seatId) {
        ConcertSeat cs = concertSeatMapper.findByConcertAndSeat(concertId, seatId);
        if (cs != null && "可售".equals(cs.getStatus())) {
            return concertSeatMapper.updateStatus(concertId, seatId, "锁定") > 0;
        }
        return false;
    }

    @Override
    public boolean releaseSeat(Long concertId, Long seatId) {
        ConcertSeat cs = concertSeatMapper.findByConcertAndSeat(concertId, seatId);
        if (cs != null && "锁定".equals(cs.getStatus())) {
            return concertSeatMapper.updateStatus(concertId, seatId, "可售") > 0;
        }
        return false;
    }

    @Override
    public boolean sellSeat(Long concertId, Long seatId) {
        ConcertSeat cs = concertSeatMapper.findByConcertAndSeat(concertId, seatId);
        if (cs != null && "锁定".equals(cs.getStatus())) {
            return concertSeatMapper.updateStatus(concertId, seatId, "已售") > 0;
        }
        return false;
    }

    @Override
    public List<ConcertSeat> getSeatsByConcert(Long concertId) {
        return concertSeatMapper.getSeatsByConcert(concertId);
    }
} 