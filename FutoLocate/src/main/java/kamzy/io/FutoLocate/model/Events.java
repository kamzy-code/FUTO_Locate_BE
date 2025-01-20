package kamzy.io.FutoLocate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Events {
    @Id
    private int id;
    private String name;
    private String description;
    private double latitude;
    private double longitude;
    private String location;
    private LocalDateTime time;
    private int created_by; // User ID
}
