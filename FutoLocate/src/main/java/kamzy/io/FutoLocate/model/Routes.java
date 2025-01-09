package kamzy.io.FutoLocate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import kamzy.io.FutoLocate.enums.ModeOfTransport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
public class Routes {

    @Id
    private int route_id;
    private double distance;
    private String path_coordinates;
    private int estimated_time;
    @Enumerated(EnumType.STRING) private ModeOfTransport modeOfTransport;

}
