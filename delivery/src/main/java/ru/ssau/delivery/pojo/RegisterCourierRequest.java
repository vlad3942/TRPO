package ru.ssau.delivery.pojo;

import lombok.Data;

@Data
public class RegisterCourierRequest {
    private SignupRequest userData;
    private CourierInfo courierInfo;

    @Data
    public static class CourierInfo {
        private String name;
        private String passportDetails;
        private String forNotes;
    }
}
