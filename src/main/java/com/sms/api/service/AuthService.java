package com.sms.api.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sms.api.model.dtos.UserRegisterDTO;
import com.sms.api.model.entities.UserEntity;
import com.sms.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements UserDetailsService {
    private final UserRepository userRepo;
    private final String CE_ID = System.getenv("chat_engine_id");
    private final String CE_KEY = System.getenv("chat_engine_key");
    public UserEntity registerUser(UserRegisterDTO registerDTO) {
        UserEntity userEntity = registerDTO.toEntity();
        log.info("UserEntity: " + userEntity);
        return userRepo.save(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(() -> new UsernameNotFoundException("User not found with email or username: " + usernameOrEmail));

        Set<GrantedAuthority> authorities = userEntity.getRole().stream().map((role) -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toSet());

        return new User(userEntity.getEmail(), userEntity.getPassword(), authorities);
    }

    public Object loginToChatEngine(String username, String password) {
        HttpURLConnection con = null;
        System.out.println("chat key: " + CE_KEY);
        System.out.println("chat id: " + CE_ID);
        try {
            // Create GET request
            URL url = new URL("https://api.chatengine.io/users/");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            // Set headers
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Private-Key", CE_KEY);
//            con.setRequestProperty("User-Name", username);
//            con.setRequestProperty("User-Secret", password);
            con.setDoOutput(true);
            Map<String, String> body = new HashMap<String, String>();
            body.put("username", username);
            body.put("secret", password);

            String jsonInputString = new JSONObject(body).toString();
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            // Generate response String
            StringBuilder responseStr = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    responseStr.append(responseLine.trim());
                }
            }
            // Jsonify + return response
            return new Gson().<Map<String, Object>>fromJson(
                    responseStr.toString(), new TypeToken<HashMap<String, Object>>() {
                    }.getType());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    public Object registerToChatEngine(String username, String password, String email, String firstName, String lastName) {
        HttpURLConnection con = null;
        try {
            // Create POST request
            URL url = new URL("https://api.chatengine.io/users");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            // Set headers
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Private-Key", CE_KEY);
            // Add request body
            con.setDoOutput(true);
            Map<String, String> body = new HashMap<String, String>();
            body.put("username", username);
            body.put("secret", password);
            body.put("email", email);
            body.put("first_name", firstName);
            body.put("last_name", lastName);
            String jsonInputString = new JSONObject(body).toString();
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            // Generate response String
            StringBuilder responseStr = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    responseStr.append(responseLine.trim());
                }
            }
            // Jsonify + return response
            return new Gson().<Map<String, Object>>fromJson(
                    responseStr.toString(), new TypeToken<HashMap<String, Object>>() {
                    }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
            return null;
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }
}
