package org.demo02.service.impl;

import org.demo02.dao.VenueMapper;
import org.demo02.entity.Venue;
import org.demo02.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VenueServiceImpl implements VenueService {
    @Autowired
    private VenueMapper venueMapper;

    @Override
    public boolean addVenue(Venue venue) {
        return venueMapper.insert(venue) > 0;
    }

    @Override
    public boolean updateVenue(Venue venue) {
        return venueMapper.update(venue) > 0;
    }

    @Override
    public boolean deleteVenue(Long id) {
        return venueMapper.deleteById(id) > 0;
    }

    @Override
    public Venue getVenueById(Long id) {
        return venueMapper.findById(id);
    }

    @Override
    public List<Venue> getAllVenues() {
        return venueMapper.findAll();
    }
} 