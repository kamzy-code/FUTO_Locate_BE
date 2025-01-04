package kamzy.io.FutoLocate.service;

import kamzy.io.FutoLocate.model.Landmarks;
import kamzy.io.FutoLocate.repository.LandmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LandmarkService {
    @Autowired
    LandmarkRepository landmarkRepo;

    public List<Landmarks> getAllLandmarks (){
        return landmarkRepo.getAllLandmark();
    }

    public Optional<Landmarks> getLandmarkById (int landmark_id){
        return landmarkRepo.findById(landmark_id);
    }
}
