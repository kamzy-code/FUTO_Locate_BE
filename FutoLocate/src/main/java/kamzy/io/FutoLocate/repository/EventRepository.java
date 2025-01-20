package kamzy.io.FutoLocate.repository;

import kamzy.io.FutoLocate.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Events, Integer> {

    @Query("SELECT e FROM Events e WHERE e.name = :title AND e.created_by = :userId")
    Events findByCreatedByAndTitle(int userId, String title);
}
