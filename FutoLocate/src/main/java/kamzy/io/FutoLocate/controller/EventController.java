package kamzy.io.FutoLocate.controller;

import kamzy.io.FutoLocate.model.Events;
import kamzy.io.FutoLocate.service.EventService;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Component
@RequestMapping("api/events")
public class EventController {

    @Autowired
    EventService eventService;

    JSONObject json;

    @PostMapping("/create")
    public ResponseEntity<String> createEvent(@RequestBody Events event) {
        json = new JSONObject();
        eventService.addEvent(event);
        json.put("status","Event created successfully");
        return ResponseEntity.ok(json.toString());
    }

    @GetMapping("")
    public List<Events> getAllEvents() {
        return eventService.getAllEvents();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEvent(@RequestParam int user_Id, @RequestParam String Title){
        eventService.deleteEvent(user_Id, Title);
        return ResponseEntity.ok("Event deleted successfully");
    }

}
