package kamzy.io.FutoLocate.service;

import kamzy.io.FutoLocate.model.Events;
import kamzy.io.FutoLocate.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {


    @Autowired
    EventRepository eventRepository;

    public void addEvent(Events event) {
        eventRepository.save(event);
    }


    public List<Events> getAllEvents() {
        return eventRepository.findAll();
    }

    public void deleteEvent(int userId, String title) {
        Events events = eventRepository.findByCreatedByAndTitle(userId, title);
        eventRepository.delete(events);
    }
}
