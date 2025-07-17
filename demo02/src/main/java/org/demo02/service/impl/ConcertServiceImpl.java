package org.demo02.service.impl;

import org.demo02.dao.ConcertMapper;
import org.demo02.entity.Concert;
import org.demo02.service.ConcertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConcertServiceImpl implements ConcertService {
    
    @Autowired
    private ConcertMapper concertMapper;
    
    @Override
    public boolean addConcert(Concert concert) {
        concert.setCreateTime(LocalDateTime.now());
        concert.setUpdateTime(LocalDateTime.now());
        return concertMapper.insert(concert) > 0;
    }
    
    @Override
    public boolean deleteConcert(Long id) {
        return concertMapper.deleteById(id) > 0;
    }
    
    @Override
    public boolean updateConcert(Concert concert) {
        concert.setUpdateTime(LocalDateTime.now());
        return concertMapper.update(concert) > 0;
    }
    
    @Override
    public Concert getConcertById(Long id) {
        return concertMapper.findById(id);
    }
    
    @Override
    public List<Concert> getAllConcerts() {
        return concertMapper.findAll();
    }
    
    @Override
    public Map<String, Object> getConcertsByPage(int currentPage, int size) {
        Map<String, Object> result = new HashMap<>();
        
        int offset = (currentPage - 1) * size;
        List<Concert> concerts = concertMapper.findByPage(offset, size);
        int total = concertMapper.countTotal();
        
        result.put("concerts", concerts);
        result.put("total", total);
        result.put("currentPage", currentPage);
        result.put("size", size);
        result.put("totalPages", (total + size - 1) / size);
        
        return result;
    }
    
    @Override
    public Map<String, Object> getConcertsByPageWithCondition(int currentPage, int size, String title, String artist, String category, String status, String address, String year, String month) {
        Map<String, Object> result = new HashMap<>();
        
        int offset = (currentPage - 1) * size;
        List<Concert> concerts = concertMapper.findByPageWithCondition(offset, size, title, artist, category, status, address, year, month);
        int total = concertMapper.countByCondition(title, artist, category, status, address, year, month);
        
        result.put("concerts", concerts);
        result.put("total", total);
        result.put("currentPage", currentPage);
        result.put("size", size);
        result.put("totalPages", (total + size - 1) / size);
        
        return result;
    }
    
    @Override
    public List<Concert> getConcertsByStatus(String status) {
        return concertMapper.findByStatus(status);
    }
    
    @Override
    public List<Concert> getConcertsByCategory(String category) {
        return concertMapper.findByCategory(category);
    }
    
    @Override
    public List<Concert> getConcertsByArtist(String artist) {
        return concertMapper.findByArtist(artist);
    }
    
    @Override
    public List<Concert> getConcertsByVenue(String venue) {
        return concertMapper.findByVenue(venue);
    }
    
    @Override
    public List<Concert> getUpcomingConcerts(LocalDateTime startTime, LocalDateTime endTime) {
        return concertMapper.findUpcoming(startTime, endTime);
    }
    
    @Override
    public List<Concert> getConcertsByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return concertMapper.findByTimeRange(startTime, endTime);
    }
    
    @Override
    public List<Concert> getConcertsByPriceRange(Double minPrice, Double maxPrice) {
        return concertMapper.findByPriceRange(minPrice, maxPrice);
    }
    
    @Override
    public boolean updateConcertStatus(Long id, String status) {
        Concert concert = new Concert();
        concert.setId(id);
        concert.setStatus(status);
        concert.setUpdateTime(LocalDateTime.now());
        return concertMapper.update(concert) > 0;
    }
    
    @Override
    public boolean updateAvailableSeats(Long id, Integer availableSeats) {
        return concertMapper.updateAvailableSeats(id, availableSeats) > 0;
    }
    
    @Override
    public boolean decreaseAvailableSeats(Long id, Integer quantity) {
        Concert concert = concertMapper.findById(id);
        if (concert != null && concert.getAvailableSeats() >= quantity) {
            int newAvailableSeats = concert.getAvailableSeats() - quantity;
            return concertMapper.updateAvailableSeats(id, newAvailableSeats) > 0;
        }
        return false;
    }
    
    @Override
    public boolean increaseAvailableSeats(Long id, Integer quantity) {
        Concert concert = concertMapper.findById(id);
        if (concert != null) {
            int newAvailableSeats = concert.getAvailableSeats() + quantity;
            if (newAvailableSeats <= concert.getTotalSeats()) {
                return concertMapper.updateAvailableSeats(id, newAvailableSeats) > 0;
            }
        }
        return false;
    }
    
    @Override
    public boolean hasEnoughSeats(Long concertId, Integer quantity) {
        Concert concert = concertMapper.findById(concertId);
        return concert != null && concert.getAvailableSeats() >= quantity;
    }
    
    @Override
    public int getConcertCount() {
        return concertMapper.countTotal();
    }
    
    @Override
    public List<Concert> getConcertsByCondition(String title, String artist, String venue, String category, String status) {
        return concertMapper.findByCondition(title, artist, venue, category, status);
    }
} 