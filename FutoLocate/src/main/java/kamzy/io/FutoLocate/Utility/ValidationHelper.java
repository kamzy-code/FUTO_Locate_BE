package kamzy.io.FutoLocate.Utility;

import kamzy.io.FutoLocate.repository.LandmarkRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class ValidationHelper {
    LandmarkRepository landmarkRepository;
    // Validate id_number format (e.g., alphanumeric and 6–11 characters)
    public boolean isValidIdNumber(String idNumber) {
        return idNumber != null && idNumber.matches("^[a-zA-Z0-9]{6,11}$");
    }

    // Validate password strength (e.g., at least 8 characters, including uppercase, lowercase, and a number)
    public boolean isValidPassword(String password) {
        return password != null && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
    }

    public void validateLandmarkIds(int startId, int endId) {
        if (startId == endId) {
            throw new IllegalArgumentException("Start and end landmarks must be different.");
        }

        if (!landmarkRepository.existsById(startId) || !landmarkRepository.existsById(endId)) {
            throw new NoSuchElementException("One or both landmarks do not exist.");
        }
    }

}
