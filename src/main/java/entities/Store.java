package entities;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(schema = "movie", name = "store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Byte id;

    @OneToOne
    @JoinColumn(name = "manager_staff_id", columnDefinition = "tinyint")
    private Staff managerStaff;

    @OneToOne
    @JoinColumn(name = "address_id", columnDefinition = "smallint")
    private Address address;

    @UpdateTimestamp
    @Column(name = "last_update", columnDefinition = "timestamp")
    private LocalDateTime lastUpdate;

    public Byte getId() {
        return id;
    }

    public void setId(Byte id) {
        this.id = id;
    }

    public Staff getManagerStaff() {
        return managerStaff;
    }

    public void setManagerStaff(Staff managerStaff) {
        this.managerStaff = managerStaff;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
