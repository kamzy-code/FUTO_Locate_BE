package kamzy.io.FutoLocate.service;

import kamzy.io.FutoLocate.Utility.EncryptionHelper;
import kamzy.io.FutoLocate.Utility.JwtUtil;
import kamzy.io.FutoLocate.Utility.ValidationHelper;
import kamzy.io.FutoLocate.model.Users;
import kamzy.io.FutoLocate.repository.AuthenticationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    AuthenticationRepo authRepo;

    @Autowired
    ValidationHelper validate;

    @Autowired
    EncryptionHelper encrypt;

    @Autowired
    JwtUtil jwtUtil;

    public String login(String email, String password)  {
            System.out.println(email+" : " + password);
            boolean authUser = authenticateUser(email, password);
            if (authUser){
                Users user = authRepo.findByEmail(email);
                return jwtUtil.generateToken(user);
            }
            return "Invalid credentials";
    }

    public String signup(Users u) {
        //        validate email and password format


        //        verify if email exist
        Users verify_email = authRepo.findByEmail(u.getEmail());
        if (verify_email != null){
            return "Email address already exist";
        }
        else{
            if (validate.isValidPassword(u.getPassword())){
                u.setPassword(encrypt.hashPassword(u.getPassword()));
                authRepo.save(u);
            }else {
                return "Weak Password";
            }
        }
        Users newUser = authRepo.ValidateUserDetails(u.getEmail(), u.getPassword());
        return newUser != null ? "Signup Successful" : "Couldn't sign you up";
    }

    public String updateAccount(Users u) {
        boolean status = validate.isValidPassword(u.getPassword());
        if (status) u.setPassword(encrypt.hashPassword(u.getPassword()));
        authRepo.save(u);
        Users newUser = authRepo.ValidateUserDetails(u.getEmail(), u.getPassword());
        return newUser.toString().equals(u.toString()) ? "Update Successful" : "Error encountered";
    }

    public String deleteAccount(Users u) {
        authRepo.deleteById(u.getUser_id());
        Users newUser = authRepo.ValidateUserDetails(u.getEmail(), u.getPassword());
        return newUser == null ? "Account Deleted" : "Error encountered";
    }

    public Users getProfileByEmail(String email) {
        return authRepo.findByEmail(email);
    }

    public boolean authenticateUser(String email, String password){
        Users user = authRepo.findByEmail(email);
        if (user == null) {
           return false;
        }
        boolean passwordStatus = encrypt.verifyPassword(password, user.getPassword());
        if (!passwordStatus){
            return false;
        }
        return true;
    }
}
