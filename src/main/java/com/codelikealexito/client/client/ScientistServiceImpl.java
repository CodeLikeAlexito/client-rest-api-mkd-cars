package com.codelikealexito.client.client;

import com.codelikealexito.client.dto.PasswordResetDto;
import com.codelikealexito.client.entities.Scientist;
import com.codelikealexito.client.entities.Role;
import com.codelikealexito.client.enums.Roles;
import com.codelikealexito.client.exceptions.CustomResponseStatusException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//import java.util.*;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Arrays;

@Service
@Slf4j
public class ScientistServiceImpl implements ScientistService {

    private final ScientistRepository scientistRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final JavaMailSender javaMailSender;

    public ScientistServiceImpl(ScientistRepository scientistRepository, PasswordEncoder passwordEncoder, RestTemplate restTemplate, JavaMailSender javaMailSender) {
        this.scientistRepository = scientistRepository;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Scientist scientist = scientistRepository.findByUsername(username);
        if (scientist == null) {
            log.error("User not found in the database.");
            throw new UsernameNotFoundException(username);
        }
        log.info("User found in the database: {}", username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        scientist.getRoles().forEach(role ->
        {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(scientist.getUsername(), scientist.getPassword(), authorities);
    }

    @Override
    public Scientist register(ScientistRegistrationDto clientDto) {

        if(emailExists(clientDto.getEmail())){
            throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, "ERR501", "Email that you entered already exists!");
        }

        if(clientExists(clientDto.getUsername())) {
            throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, "ERR502", "Username that you entered already exists!");
        }

        Scientist user = Scientist.createUserWithFullInformation(null, clientDto.getUsername(), clientDto.getFirstName(), clientDto.getLastName(), clientDto.getEmail(),
                passwordEncoder.encode(clientDto.getPassword()), clientDto.getCity(), clientDto.getAddress(),
                clientDto.getPhone(), null, Arrays.asList(Role.giveRole(Roles.USER.name())));
        return scientistRepository.save(user);
    }

    //TODO to be implemented
    @Override
    public Scientist editClient(ScientistRegistrationDto userDto) {
        if(!clientExists(userDto.getUsername())){

        }

        if(!emailExists(userDto.getEmail())) {

        }

        return null;

    }

    @Override
    public List<Scientist> getAllClients() {
        return scientistRepository.findAll();
    }

    @Override
    public Scientist getClientByUsername(String username) {
        return scientistRepository.findByUsername(username);
    }

    @Override
    public Map<String, Boolean> deleteUser(Long userId) {
        Scientist scientist = scientistRepository.findById(userId)
                .orElseThrow(() -> new CustomResponseStatusException(HttpStatus.NOT_FOUND, "ERR603", "User does not exists!"));

        scientistRepository.delete(scientist);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @Override
    public Scientist getClientById(Long id) {
        return scientistRepository.findById(id)
                .orElseThrow(() -> new CustomResponseStatusException(HttpStatus.NOT_FOUND, "SOME_ERR_CODE", "User does not exists!"));
    }

    @Override
    public Scientist updateClient(Long clientId, ScientistUpdateDto clientDto) {
        Scientist scientist = scientistRepository.findById(clientId)
                .orElseThrow(() -> new CustomResponseStatusException(HttpStatus.NOT_FOUND, "ERR603", "Client does not exists!"));

        final Scientist updatedScientist = Scientist.updateScientist(clientId, clientDto.getUsername(), clientDto.getFirstName(), clientDto.getLastName(),
                clientDto.getEmail(), clientDto.getPassword(),  clientDto.getCity(), clientDto.getAddress(), clientDto.getPhone(), null, clientDto.getRoles());

        return scientistRepository.save(updatedScientist);
    }

    @Override
    public void updateResetPasswordToken(String token, String email) {
        Scientist scientist = scientistRepository.findByEmail(email);
        if (scientist != null) {
            final Scientist updateScientist = Scientist.updateScientist(scientist.getId(), scientist.getUsername(), scientist.getFirstName(), scientist.getLastName(),
                    scientist.getEmail(), scientist.getPassword(), scientist.getCity(), scientist.getAddress(), scientist.getPhone(), token, scientist.getRoles());
            scientistRepository.save(updateScientist);
        } else {
            throw new CustomResponseStatusException(HttpStatus.NOT_FOUND, "ERR_CODE", "Could not find any customer with the email " + email);
        }
    }

    private void sendEmail(String toEmail,
                          String subject,
                          String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("aleksandar.ivanov@estafet.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        javaMailSender.send(message);

        System.out.println("Mail send successfully...");
    }

    @Override
    public Scientist getScientistByResetPasswordToken(String token) {
        return scientistRepository.findByResetPasswordToken(token);
    }

    @Override
    public void resetPassword(String token, PasswordResetDto passwordResetDto) {
        Scientist scientist = getScientistByResetPasswordToken(token);

        if(scientist == null) {
            return;
        }

        updatePassword(scientist, passwordResetDto);

    }

    private void updatePassword(Scientist scientist, PasswordResetDto passwordResetDto) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(!passwordResetDto.getNewPassword().equals(passwordResetDto.getConfirmNewPassword())) {
            // throw exception
            return;
        }

        String encodedPassword = passwordEncoder.encode(passwordResetDto.getNewPassword());
//        customer.setPassword(encodedPassword);
        final Scientist updateScientist = Scientist.updateScientist(scientist.getId(), scientist.getUsername(), scientist.getFirstName(), scientist.getLastName(),
                scientist.getEmail(), encodedPassword, scientist.getCity(), scientist.getAddress(), scientist.getPhone(), null, scientist.getRoles());

//        customer.setResetPasswordToken(null);
        scientistRepository.save(updateScientist);
    }

    private boolean clientExists(String username) {
        return scientistRepository.findByUsername(username) != null;
    }

    private boolean emailExists(String email) {
        return scientistRepository.findByEmail(email) != null;
    }

    //


}
