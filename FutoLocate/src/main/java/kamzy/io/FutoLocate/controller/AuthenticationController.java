package kamzy.io.FutoLocate.controller;

import kamzy.io.FutoLocate.model.Users;
import kamzy.io.FutoLocate.service.AuthenticationService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@Component
@RequestMapping ("/api/auth")
public class AuthenticationController {
    JSONObject json;

    @Autowired
    AuthenticationService authservice;

    @PostMapping("/signup")
    public ResponseEntity<String> signup (@RequestBody Users u){
        json = new JSONObject();
        String status = authservice.signup(u);
        json.put("status", status);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestParam String email, @RequestParam String password){
        json = new JSONObject();
        String token = authservice.login(email, password);
        json.put("token", token);
        if (token.equals("Invalid credentials")){
            return ResponseEntity.status(401).body(json.toString());
        } else {
            return ResponseEntity.ok(json.toString());
        }
    }

    @PutMapping("/update_account")
    public ResponseEntity<String> updateAccount(@RequestBody Users u){
        json = new JSONObject();
        String status = authservice.updateAccount(u);
        json.put("status", status);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @DeleteMapping("/delete_account")
    public ResponseEntity<String> DeleteAccount (@RequestBody Users u){
        json = new JSONObject();
        String status = authservice.deleteAccount(u);
        json.put("status", status);
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @PostMapping("/profile")
    public ResponseEntity<Users> getProfile (@RequestParam String email){
        Users u = authservice.getProfileByEmail(email);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

}
