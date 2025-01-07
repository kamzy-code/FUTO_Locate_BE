package kamzy.io.FutoLocate.repository;

import kamzy.io.FutoLocate.model.Landmarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LandmarkRepository extends JpaRepository<Landmarks, Integer> {

    @Query("SELECT l FROM Landmarks l")
    List<Landmarks> getAllLandmark();

    @Query("SELECT l FROM Landmarks l WHERE l.landmark_id = :landmark_id")
    Landmarks getLandmarkById(int landmark_id);

    @Query("SELECT l FROM Landmarks l WHERE l.name = :query")
    Optional<Landmarks> findByName(String query);
}
