package org.demo02.entity;

public class ConcertSeat {
    private Long id;
    private Long concertId;
    private Long seatId;
    private String status; // 可售/已售/锁定
    private String rowNum;
    private Integer col;
    private String seatNo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getConcertId() { return concertId; }
    public void setConcertId(Long concertId) { this.concertId = concertId; }
    public Long getSeatId() { return seatId; }
    public void setSeatId(Long seatId) { this.seatId = seatId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getRowNum() { return rowNum; }
    public void setRowNum(String rowNum) { this.rowNum = rowNum; }
    public Integer getCol() { return col; }
    public void setCol(Integer col) { this.col = col; }
    public String getSeatNo() { return seatNo; }
    public void setSeatNo(String seatNo) { this.seatNo = seatNo; }
} 