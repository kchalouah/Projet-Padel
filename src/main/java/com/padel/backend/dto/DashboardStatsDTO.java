package com.padel.backend.dto;

import com.padel.backend.entity.Reservation;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DashboardStatsDTO {
    private long totalUsers;
    private long totalReservations;
    private long totalTerrains;
    private List<Reservation> recentReservations;
}
