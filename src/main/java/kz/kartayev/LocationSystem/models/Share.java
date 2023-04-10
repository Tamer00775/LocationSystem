package kz.kartayev.LocationSystem.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="share")
public class Share {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="send_id")
    private int send_id;
    @Column(name ="status")
    @NotNull
    private String status;
    @Column(name = "getter_id")
    @NotNull
    private int getter_id;
    @Column(name="location_id")
    private int location_id;

    public Share(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSend_id() {
        return send_id;
    }

    public void setSend_id(int send_id) {
        this.send_id = send_id;
    }

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

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }
}
