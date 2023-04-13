package kz.kartayev.LocationSystem.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AccessDTO {
  @NotEmpty(message = "You must write new access for your friend!")
  private String access;
  @NotNull(message = "You must write in which location you want to update access for your friend")
  private int location_id;

  public String getAccess() {
    return access;
  }

  public void setAccess(String access) {
    this.access = access;
  }

  public int getLocation_id() {
    return location_id;
  }

  public void setLocation_id(int location_id) {
    this.location_id = location_id;
  }
}
