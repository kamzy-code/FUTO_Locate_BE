package kamzy.io.FutoLocate.service;

import kamzy.io.FutoLocate.enums.NavLogStatus;
import kamzy.io.FutoLocate.model.Navigation_logs;
import kamzy.io.FutoLocate.repository.NavigationLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NavigationLogService {

    @Autowired
    NavigationLogRepository navLogRepo;

    public boolean createLog(int userId, int routeId) {
        Navigation_logs navLog = new Navigation_logs();
        navLog.setUser_id(userId);
        navLog.setRoute_id(routeId);
        navLog.setStart_time(new Date());
        navLog.setStatus(NavLogStatus.In_progress);
        navLogRepo.save(navLog);
        return true;
    }

    public boolean updateLogStatus(int logId, NavLogStatus status) {
        Navigation_logs navlog = navLogRepo.getLogByLogId(logId);
        if (navlog == null){
            return false;
        }
        navlog.setStatus(status);
        navlog.setEnd_time(new Date());
        navLogRepo.save(navlog);
        return true;
    }

    public List<Navigation_logs> getLogsByUser(int userId) {
        return navLogRepo.getLogsByUser(userId);
    }
}
