package org.demo02.controller;

import org.demo02.entity.Venue;
import org.demo02.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/venue")
public class VenueController {
    @Autowired
    private VenueService venueService;

    @GetMapping
    public List<Venue> getAllVenues() {
        return venueService.getAllVenues();
    }

    @GetMapping("/{id}")
    public Venue getVenueById(@PathVariable Long id) {
        return venueService.getVenueById(id);
    }

    @PostMapping("/add")
    public Map<String, Object> addVenue(@RequestBody Venue venue) {
        boolean success = venueService.addVenue(venue);
        Map<String, Object> res = new HashMap<>();
        res.put("success", success);
        return res;
    }

    @PutMapping("/update")
    public Map<String, Object> updateVenue(@RequestBody Venue venue) {
        boolean success = venueService.updateVenue(venue);
        Map<String, Object> res = new HashMap<>();
        res.put("success", success);
        return res;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> deleteVenue(@PathVariable Long id) {
        boolean success = venueService.deleteVenue(id);
        Map<String, Object> res = new HashMap<>();
        res.put("success", success);
        return res;
    }
} 