package at.uastw.disysusageservice.dto;

import java.time.LocalDateTime;

public class UsageUpdatedMessageDto {

    private LocalDateTime hour;

    public UsageUpdatedMessageDto(){}

    public UsageUpdatedMessageDto(LocalDateTime hour) {
        this.hour = hour;
    }

    public LocalDateTime getHour() {
        return hour;
    }

    public void setHour(LocalDateTime hour) {
        this.hour = hour;
    }
}
