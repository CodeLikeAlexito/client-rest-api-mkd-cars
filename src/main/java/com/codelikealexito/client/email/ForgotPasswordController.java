package com.codelikealexito.client.email;

import com.codelikealexito.client.client.ScientistService;
import com.codelikealexito.client.dto.PasswordResetDto;
import com.codelikealexito.client.email.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/api/scientist/password")
@AllArgsConstructor
public class ForgotPasswordController {

    private final ScientistService scientistService;
    private final EmailSenderService emailSenderService;
//    private final JavaMailSender javaMailSender;

    @PostMapping("/forgot_password")
    public ResponseEntity<Map<String, Boolean>> processForgotPassword(@RequestParam(value = "email") String email) {
//        Map<String, Boolean> resultMap = new HashMap<>();
        Map<String, Boolean> resultMap = scientistService.forgetPassword(email);
//        try {
//
//            // if reset token is successfully writen, send email
//            resultMap = scientistService.forgetPassword(email);
//
//        } catch (CustomResponseStatusException e) {
//            System.out.println(e.getMessage());
//        }

        return ResponseEntity.ok(resultMap);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, Boolean>> resetPassword(@RequestParam (value = "token") String token,
                                              @RequestBody PasswordResetDto passwordResetDto) {

        Map<String, Boolean> resultMap = scientistService.resetPassword(token, passwordResetDto);

        return ResponseEntity.ok(resultMap);
    }
}
