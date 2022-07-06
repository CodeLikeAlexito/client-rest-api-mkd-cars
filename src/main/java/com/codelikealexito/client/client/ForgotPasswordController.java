package com.codelikealexito.client.client;

import com.codelikealexito.client.dto.PasswordResetDto;
import com.codelikealexito.client.exceptions.CustomResponseStatusException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/scientist/password")
@AllArgsConstructor
public class ForgotPasswordController {

    private final ScientistService scientistService;
    private final EmailSenderService emailSenderService;
//    private final JavaMailSender javaMailSender;

    @PostMapping("/forgot_password")
    public ResponseEntity<Void> processForgotPassword() {
        String token = String.valueOf(UUID.randomUUID());
        try {
            scientistService.updateResetPasswordToken((token), "sashe@gmail.com"); //this can return boolean
            // if reset token is successfully writen, send email
            String resetPasswordLink = "http://localhost:4001/v1/api/scientist/password/reset_password?token=" + token;
            scientistService.sendEmail("ivanovalexkav@gmail.com",
                        "Test subject",
                    resetPasswordLink
                    );
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom("test@gmail.com");
//            message.setTo("ivanovalexkav@gmail.com");
//            message.setSubject("Test");
//            message.setText(resetPasswordLink);
////            emailSender.send(message);
//            emailSender.send(message);
        } catch (CustomResponseStatusException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(UUID.randomUUID());
//        System.out.println("http://localhost:4001/v1/api/scientist/password/reset_password?token=");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestParam (value = "token") String token,
                                              @RequestBody PasswordResetDto passwordResetDto) {
        scientistService.resetPassword(token, passwordResetDto);

        return ResponseEntity.ok().build();
    }
}
