package kamzy.io.FutoLocate.repository;

import kamzy.io.FutoLocate.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepo extends JpaRepository<Users, Integer> {

    @Query("SELECT u FROM Users u WHERE u.email = :email AND u.password = :password")
    Users ValidateUserDetails(String email, String password);

    @Query("SELECT u FROM Users u WHERE u.email = :email")
    Users findByEmail (String email);

    @Query("SELECT u FROM Users u WHERE u.email = :email AND u.password = :password")
    Users getUserDetails (String email, String password);
}
