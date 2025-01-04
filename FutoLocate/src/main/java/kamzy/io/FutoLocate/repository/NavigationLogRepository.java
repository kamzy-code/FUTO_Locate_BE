package kamzy.io.FutoLocate.repository;

import kamzy.io.FutoLocate.model.Navigation_logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NavigationLogRepository extends JpaRepository<Navigation_logs, Integer> {

    @Query  ("SELECT n FROM Navigation_logs n WHERE n.log_id = :log_id")
    Navigation_logs getLogByLogId (int log_id);

    @Query  ("SELECT n FROM Navigation_logs n")
    List<Navigation_logs> getLogsByUser(int userId);
}
