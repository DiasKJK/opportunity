package com.diplom.diplom.features.authentication.controller;

import com.diplom.diplom.features.authentication.dto.AuthenticationRequestBody;
import com.diplom.diplom.features.authentication.dto.AuthenticationResponseBody;
import com.diplom.diplom.features.authentication.model.AuthenticationUser;
import com.diplom.diplom.features.authentication.model.Role;
import com.diplom.diplom.features.authentication.repository.AuthenticationUserRepository;
import com.diplom.diplom.features.authentication.service.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final AuthenticationUserRepository authenticationUserRepository;

    public AuthenticationController(AuthenticationService authenticationService, AuthenticationUserRepository authenticationUserRepository) {
        this.authenticationService = authenticationService;
        this.authenticationUserRepository = authenticationUserRepository;
    }
    @GetMapping("/user")
    public AuthenticationUser getUser(@RequestAttribute("authenticatedUser") AuthenticationUser authenticatedUser) {
        return authenticationService.getUser(authenticatedUser.getEmail());
    }

    @PostMapping("/login")
    public AuthenticationResponseBody loginPage(@Valid @RequestBody AuthenticationRequestBody loginRequestBody) {
        return authenticationService.login(loginRequestBody);
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestAttribute("authenticatedUser") AuthenticationUser user) {
        authenticationService.deleteUser(user.getId());
        return "User deleted successfully";
    }

    @PostMapping("/register")
    public AuthenticationResponseBody responseBody(@Valid @RequestBody AuthenticationRequestBody registerRequestBody) throws MessagingException, UnsupportedEncodingException {
        return authenticationService.register(registerRequestBody);
    }

    @PutMapping("/validate-email-verification-token")
    public String verifyEmail(@RequestParam String token, @RequestAttribute("authenticatedUser") AuthenticationUser user) {
        authenticationService.validateEmailVerificationToken(token, user.getEmail());
        return ("Email verified successfully.");
    }

    @GetMapping("/send-email-verification-token")
    public String sendEmailVerificationToken(@RequestAttribute("authenticatedUser") AuthenticationUser user) {
        authenticationService.sendEmailVerificationToken(user.getEmail());
        return ("Email verification token sent successfully.");
    }

    @PutMapping("/send-password-reset-token")
    public String sendPasswordResetToken(@RequestParam String email) {
        authenticationService.sendPasswordResetToken(email);
        return ("Password reset token sent successfully.");
    }

    @PutMapping("/reset-password")
    public String resetPassword(@RequestParam String newPassword, @RequestParam String token, @RequestParam String email) {
        authenticationService.resetPassword(email, newPassword, token);
        return ("Password reset successfully.");
    }

    @PutMapping("/profile/{id}")
    public AuthenticationUser updateUserProfile(
            @RequestAttribute("authenticatedUser") AuthenticationUser user,
            @PathVariable Long id,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String group1,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String contacts,
            @RequestParam(required = false) String education,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String aboutMe,
            @RequestParam(required = false) String email
    ){
//        if (user.getRole() != Role.ADMIN) {
//            throw new IllegalAccessError("Only admins can update user profiles.");
//        }
        if(!user.getId().equals(id)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have permission to update this profile");
        }

        return authenticationService.updateUserProfile(id, firstName, lastName, group1, status, contacts, education, location, aboutMe, email);
    }

    @GetMapping("/users")
    public ResponseEntity<List<AuthenticationUser>> getUsers() {
        return ResponseEntity.ok(authenticationService.getUsers());
    }
}
