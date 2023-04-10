package kz.kartayev.LocationSystem.dto;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;


public class ShareDTO {
    @NotNull(message = "You must be enter status: Ex: ADMIN OR READ")
    private String status;
    @NotNull(message = "You must be choose one person of list")
    private int getter_id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getGetter_id() {
        return getter_id;
    }

    public void setGetter_id(int getter_id) {
        this.getter_id = getter_id;
    }
}
