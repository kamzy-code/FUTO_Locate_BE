package kamzy.io.FutoLocate.controller;

import kamzy.io.FutoLocate.enums.ModeOfTransport;
import kamzy.io.FutoLocate.enums.NavLogStatus;
import kamzy.io.FutoLocate.model.Routes;
import kamzy.io.FutoLocate.service.NavigationLogService;
import kamzy.io.FutoLocate.service.RouteService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Component
@RequestMapping ("api/navigation")
public class NavigationController {
    JSONObject json;
    @Autowired
    RouteService routeServ;
    @Autowired
    NavigationLogService navLogServ;

//    @GetMapping("/routes")
//    public ResponseEntity<String> getRoutes (@RequestParam int start_landmark, @RequestParam int end_landmark){
//        List<Routes> r = routeServ.getAllRoutes(start_landmark, end_landmark);
//        if (r==null){
//            json.put("User", "No user found");
//            return new ResponseEntity<>(json.toString(), HttpStatus.NO_CONTENT);
//        }else {
//            json.put("User", r);
//            return new ResponseEntity<>(json.toString(), HttpStatus.OK);
//        }
//    }

//    @GetMapping("/routes/{route_id}")
//    public ResponseEntity<String> getRouteDetails (@RequestParam int route_id){
//        json = new JSONObject();
//        Routes r = routeServ.getRouteDetails(route_id);
//        if (r==null){
//            json.put("User", "No user found");
//            return new ResponseEntity<>(json.toString(), HttpStatus.NO_CONTENT);
//        }else {
//            json.put("User", r);
//            return new ResponseEntity<>(json.toString(), HttpStatus.OK);
//        }
//    }
    @PostMapping("/routes/create")
    public ResponseEntity<Routes> createRoute (@RequestParam double startLat, @RequestParam double startLong, @RequestParam double endLat, @RequestParam double endLong, @RequestParam ModeOfTransport modeOfTransport){
//        json = new JSONObject();
        Routes status = routeServ.CreateRoute(startLat, startLong, endLat, endLong, modeOfTransport);
//        json.put("routes", status);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping("/start")
    public ResponseEntity<String> startNavigation (@RequestParam int user_id, @RequestParam int route_id){
        json = new JSONObject();
        boolean status = navLogServ.createLog(user_id, route_id);
        if (status){
            json.put("status", true);
            json.put("message", "Navigation started");
        }else {
            json.put("status", false);
            json.put("message", "Couldn't begin Navigation");
        }
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @PutMapping("/end")
    public ResponseEntity<String> endNavigation (@RequestParam int log_id, @RequestParam NavLogStatus newStatus){
        json = new JSONObject();
        boolean status = navLogServ.updateLogStatus(log_id, newStatus);
        if (status){
            json.put("status", true);
            json.put("message", "Navigation ended");
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);
        }else {
            json.put("status", false);
            json.put("message", "Couldn't end Navigation");
            return new ResponseEntity<>(json.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
