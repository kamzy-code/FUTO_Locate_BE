package kamzy.io.FutoLocate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
public class Users {
    @Id
    int user_id;
    String full_name;
    String email;
    String password;
    String phone_number;
    String role;
}
