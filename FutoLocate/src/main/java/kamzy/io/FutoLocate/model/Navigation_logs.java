package kamzy.io.FutoLocate.model;

import jakarta.persistence.*;
import kamzy.io.FutoLocate.enums.NavLogStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Navigation_logs {

    @Id private int log_id;
    private int user_id;
    private int route_id;
    @Temporal(TemporalType.TIMESTAMP) private Date start_time;
    @Temporal(TemporalType.TIMESTAMP) private Date end_time;
    @Enumerated(EnumType.STRING) private NavLogStatus status;
}


