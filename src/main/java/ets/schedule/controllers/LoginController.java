// package ets.schedule.controllers;

// import org.springframework.web.bind.annotation.RestController;

// import ets.schedule.data.payloads.login.LoginConfirmPayload;
// import ets.schedule.data.payloads.login.LoginPayload;
// import ets.schedule.data.responses.login.AuthConfirmResponse;
// import ets.schedule.data.responses.login.AuthResponse;
// import ets.schedule.interfaces.services.AuthService;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;


// @RestController
// public class LoginController {
//     @Autowired
//     private AuthService authService;

//     @PostMapping("/api/v1/login/confirm")
//     public ResponseEntity<AuthConfirmResponse> checkLogin(
//             @RequestBody LoginConfirmPayload payload
//     ) {
//         var response = authService.checkAsync(payload);
//         return ResponseEntity.status(response.statusCode()).body(response.data());
//     }
    
//     @PostMapping("/api/v1/login")
//     public ResponseEntity<AuthResponse> login(@RequestBody LoginPayload payload) {
//         var response = authService.loginAsync(payload);
//         return ResponseEntity.status(response.statusCode()).body(response.data());
//     }
// }
