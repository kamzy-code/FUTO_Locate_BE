package kamzy.io.FutoLocate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import kamzy.io.FutoLocate.enums.LandmarkCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Landmarks {

    @Id
    int landmark_id;
    String name;
    String description;
    double latitude;
    double longitude;
    @Enumerated(EnumType.STRING)
    LandmarkCategory category;
    String photo_url;

}

