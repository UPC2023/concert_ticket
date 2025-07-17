package org.demo02.entity;

public class Seat {
    private Long id;
    private Long venueId;
    private String rowNum;
    private Integer col;
    private String seatNo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getVenueId() { return venueId; }
    public void setVenueId(Long venueId) { this.venueId = venueId; }
    public String getRowNum() { return rowNum; }
    public void setRowNum(String rowNum) { this.rowNum = rowNum; }
    public Integer getCol() { return col; }
    public void setCol(Integer col) { this.col = col; }
    public String getSeatNo() { return seatNo; }
    public void setSeatNo(String seatNo) { this.seatNo = seatNo; }
} 