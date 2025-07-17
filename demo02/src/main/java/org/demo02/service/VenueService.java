package org.demo02.service;

import org.demo02.entity.Venue;
import java.util.List;

public interface VenueService {
    boolean addVenue(Venue venue);
    boolean updateVenue(Venue venue);
    boolean deleteVenue(Long id);
    Venue getVenueById(Long id);
    List<Venue> getAllVenues();
} 