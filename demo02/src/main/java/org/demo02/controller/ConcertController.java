package org.demo02.controller;

import org.demo02.entity.Concert;
import org.demo02.service.ConcertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@Tag(name = "演唱会管理")
@RestController
@RequestMapping("/api/concert")
public class ConcertController {
    
    @Autowired
    private ConcertService concertService;
    
    @Operation(summary = "添加演唱会")
    @PostMapping
    public ResponseEntity<Map<String, Object>> addConcert(@RequestBody Concert concert) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = concertService.addConcert(concert);
            if (success) {
                response.put("success", true);
                response.put("message", "演唱会添加成功");
            } else {
                response.put("success", false);
                response.put("message", "演唱会添加失败");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "演唱会添加失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "获取演唱会详情")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getConcertById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Concert concert = concertService.getConcertById(id);
            if (concert != null) {
                response.put("success", true);
                response.put("concert", concert);
            } else {
                response.put("success", false);
                response.put("message", "演唱会不存在");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取演唱会信息失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "更新演唱会信息")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateConcert(@PathVariable Long id, @RequestBody Concert concert) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            concert.setId(id);
            boolean success = concertService.updateConcert(concert);
            if (success) {
                response.put("success", true);
                response.put("message", "演唱会更新成功");
            } else {
                response.put("success", false);
                response.put("message", "演唱会更新失败");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "演唱会更新失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "删除演唱会")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteConcert(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = concertService.deleteConcert(id);
            if (success) {
                response.put("success", true);
                response.put("message", "演唱会删除成功");
            } else {
                response.put("success", false);
                response.put("message", "演唱会删除失败");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "演唱会删除失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "获取所有演唱会")
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getConcertList() {
        Map<String, Object> res = new HashMap<>();
        List<Concert> concerts = concertService.getAllConcerts();
        res.put("success", true);
        res.put("concerts", concerts);
        return ResponseEntity.ok(res);
    }
    
    @Operation(summary = "分页获取演唱会")
    @GetMapping("/page")
    public ResponseEntity<Map<String, Object>> getConcertsByPage(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String artist,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String month) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Map<String, Object> result = concertService.getConcertsByPageWithCondition(currentPage, size, title, artist, category, status, address, year, month);
            response.put("success", true);
            response.putAll(result);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取演唱会列表失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "根据状态获取演唱会")
    @GetMapping("/status/{status}")
    public ResponseEntity<Map<String, Object>> getConcertsByStatus(@PathVariable String status) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Concert> concerts = concertService.getConcertsByStatus(status);
            response.put("success", true);
            response.put("concerts", concerts);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取演唱会列表失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "根据类别获取演唱会")
    @GetMapping("/category/{category}")
    public ResponseEntity<Map<String, Object>> getConcertsByCategory(@PathVariable String category) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Concert> concerts = concertService.getConcertsByCategory(category);
            response.put("success", true);
            response.put("concerts", concerts);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取演唱会列表失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "根据艺术家获取演唱会")
    @GetMapping("/artist/{artist}")
    public ResponseEntity<Map<String, Object>> getConcertsByArtist(@PathVariable String artist) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Concert> concerts = concertService.getConcertsByArtist(artist);
            response.put("success", true);
            response.put("concerts", concerts);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取演唱会列表失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "获取即将开始的演唱会")
    @GetMapping("/upcoming")
    public ResponseEntity<Map<String, Object>> getUpcomingConcerts(
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            LocalDateTime start = startTime != null ? LocalDateTime.parse(startTime) : LocalDateTime.now();
            LocalDateTime end = endTime != null ? LocalDateTime.parse(endTime) : LocalDateTime.now().plusMonths(1);
            
            List<Concert> concerts = concertService.getUpcomingConcerts(start, end);
            response.put("success", true);
            response.put("concerts", concerts);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取即将开始的演唱会失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 根据价格范围获取演唱会
    @GetMapping("/price-range")
    public ResponseEntity<Map<String, Object>> getConcertsByPriceRange(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Concert> concerts = concertService.getConcertsByPriceRange(minPrice, maxPrice);
            response.put("success", true);
            response.put("concerts", concerts);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取演唱会列表失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 更新演唱会状态
    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> updateConcertStatus(@PathVariable Long id, @RequestBody Map<String, String> statusData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String status = statusData.get("status");
            boolean success = concertService.updateConcertStatus(id, status);
            if (success) {
                response.put("success", true);
                response.put("message", "状态更新成功");
            } else {
                response.put("success", false);
                response.put("message", "状态更新失败");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "状态更新失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
    
    // 检查座位是否足够
    @GetMapping("/{id}/check-seats")
    public ResponseEntity<Map<String, Object>> checkSeats(@PathVariable Long id, @RequestParam Integer quantity) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean hasEnough = concertService.hasEnoughSeats(id, quantity);
            response.put("success", true);
            response.put("hasEnoughSeats", hasEnough);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "检查座位失败：" + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
} 