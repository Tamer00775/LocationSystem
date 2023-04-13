package kz.kartayev.LocationSystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * Location class.
 * */
@Entity
@Table(name = "location")
public class Location implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "location")
  @NotEmpty
  private String location;

  @ManyToOne
  @JoinColumn(name = "owner_id", referencedColumnName = "id")
  @JsonIgnore
  private User owner;

  @Column(name = "sharedwith")
  private String status;

  public Location() {
  }
  /**
   * Location constructor.
   * */

  public Location(int id, String location, User owner, String status) {
    this.id = id;
    this.location = location;
    this.owner = owner;
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "Location{"
            + "id=" + id
            + ", location='" + location + '\''
            + ", owner=" + owner
            + '}';
  }
}
