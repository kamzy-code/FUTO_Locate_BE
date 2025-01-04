package kamzy.io.FutoLocate.controller;

import kamzy.io.FutoLocate.model.Landmarks;
import kamzy.io.FutoLocate.service.LandmarkService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Component
@RequestMapping("api/landmark")
public class LandmarkController {
    JSONObject json;
    @Autowired
    LandmarkService landmarkServ;

    @GetMapping("")
    public ResponseEntity<List<Landmarks>> getAllLandmarks (){
        List<Landmarks> landmarks = landmarkServ.getAllLandmarks();
        return new ResponseEntity<>(landmarks, HttpStatus.OK);
    }

    @GetMapping("/{landmark_id}")
    public ResponseEntity<Optional<Landmarks>> getLandmarkDetails (@PathVariable int landmark_id){
        Optional<Landmarks> landmarks = landmarkServ.getLandmarkById(landmark_id);
        return new ResponseEntity<>(landmarks, HttpStatus.OK);
    }
}
