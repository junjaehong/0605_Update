package com.DevOOPS.barrier.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TypListdto {
    private int idx;
    private String typName;
    private LocalDateTime typStart;
    private LocalDateTime typEnd;

    public TypListdto(int idx, String typ_name, LocalDateTime typ_start, LocalDateTime typ_end){
        this.idx = idx;
        this.typName = typ_name;
        this.typStart = typ_start;
        this.typEnd = typ_end;
    }

    public int getidx() {
        return idx;
    }
    public void setidx(int idx) {
        this.idx = idx;
    }
    public String gettypName() {
        return typName;
    }
    public void settypName(String typName) {
        this.typName = typName;
    }
    public LocalDateTime gettypStart() {
        return typStart;
    }
    public void settypStart(LocalDateTime typStart) {
        this.typStart = typStart;
    }
    public LocalDateTime gettypEnd() {
        return typEnd;
    }
    public void settyp_end(LocalDateTime typEnd) {
        this.typEnd = typEnd;
    }
}
