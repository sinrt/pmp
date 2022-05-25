package com.pmp.nwms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.config.Constants;
import com.pmp.nwms.domain.User;
import com.pmp.nwms.repository.UserRepository;
import com.pmp.nwms.security.NwmsUser;
import com.pmp.nwms.service.MailService;
import com.pmp.nwms.service.SmsSendService;
import com.pmp.nwms.service.UserService;
import com.pmp.nwms.service.dto.PasswordChangeDTO;
import com.pmp.nwms.service.dto.UserDTO;
import com.pmp.nwms.util.ApplicationDataStorage;
import com.pmp.nwms.util.UserUtil;
import com.pmp.nwms.util.captcha.CaptchaUtil;
import com.pmp.nwms.web.rest.errors.*;
import com.pmp.nwms.web.rest.vm.KeyAndPasswordVM;
import com.pmp.nwms.web.rest.vm.ManagedUserVM;
import com.pmp.nwms.web.rest.vm.ResetPasswordVM;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final SmsSendService smsSendService;

    private final ApplicationDataStorage applicationDataStorage;

    private final CaptchaUtil captchaUtil;

//    private final ServerConfigService serverConfigService;

    private NwmsConfig nwmsConfig;

    private final MailService mailService;

    public AccountResource(UserRepository userRepository, UserService userService, MailService mailService, NwmsConfig nwmsConfig, SmsSendService smsSendService, ApplicationDataStorage applicationDataStorage, CaptchaUtil captchaUtil) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
        this.nwmsConfig = nwmsConfig;
        this.smsSendService = smsSendService;
        this.applicationDataStorage = applicationDataStorage;
        this.captchaUtil = captchaUtil;
    }

    /**
     * POST  /register : register the user.
     *
     * @param managedUserVM the managed user View Model
     * @throws InvalidPasswordException  400 (Bad Request) if the password is incorrect
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already used
     * @throws LoginAlreadyUsedException 400 (Bad Request) if the login is already used
     */
    @PostMapping("/sendingSMS")
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> validatePhone(@Valid @RequestBody ManagedUserVM managedUserVM) {
        if(!captchaUtil.validateCaptcha(managedUserVM.getClientKey(), managedUserVM.getCaptcha())){
            throw new InvalidCaptchaValueException(null);
        }
        userService.checkingLogin(managedUserVM);
        String smsCode = RandomStringUtils.randomNumeric(5);
        applicationDataStorage.add(managedUserVM.getLogin() + "_sentCode", smsCode);
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(managedUserVM.getLogin());
        if (mat.matches()) {
//            mailService.sendEmail(managedUserVM.getLogin(),"Login Code",smsCode,false,false);
            User user = new User();
            user.setEmail(managedUserVM.getLogin());
            user.setLangKey("fa");
            user.setActivationKey(smsCode);

            try {
                mailService.sendActivationCodeToemail(user);
                return new ResponseEntity(null, null, HttpStatus.OK);
            } catch (MessagingException e) {
                e.printStackTrace();
                return new ResponseEntity(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {


            String phoneNumber = null;
            if (managedUserVM.getLogin().startsWith("0")) {
                phoneNumber = managedUserVM.getLogin().substring(1);
            }

            try {
                String followupId = smsSendService.sendMessage(phoneNumber, "کد تایید روبرو:" + smsCode);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("sms_sent", "ok");
                return ResponseEntity.created(null)
                    .body(jsonObject.toString());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return new ResponseEntity("faild", null, HttpStatus.BAD_REQUEST);
            }
        }

    }

    @PostMapping("/validcode")
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    public void validationCode(@Valid @RequestBody ManagedUserVM managedUserVM) {
        Object sentCode = applicationDataStorage.get(managedUserVM.getLogin() + "_sentCode");
        if (sentCode != null) {
            if (!sentCode.equals(managedUserVM.getPersonalCode())) {
                throw new SentCodeInvalidException();
            }
        }

    }

    @PostMapping("/registerwithemail")
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccountWithEmail(@Valid @RequestBody ManagedUserVM managedUserVM) {
        if (!checkPasswordLength(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        User user = userService.registerUser(managedUserVM, managedUserVM.getPassword());
        try {
            mailService.sendActivationEmail(user);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/register")
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        if (!checkPasswordLength(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        User user = userService.registerUser(managedUserVM, managedUserVM.getPassword());
    }

    /**
     * GET  /activate : activate the registered user.
     *
     * @param key the activation key
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be activated
     */
    @GetMapping("/activate")
    @Timed
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this activation key");
        }
    }

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @GetMapping("/authenticate")
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET  /account : get the current user.
     *
     * @return the current user
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping("/account")
    @Timed
    public UserDTO getAccount() {

        UserDTO userDTO = new UserDTO();
        userDTO = userService.getUserWithAuthorities()
            .map(UserDTO::new)
            .orElseThrow(() -> new InternalServerErrorException("User could not be found"));
        String imagePath = this.nwmsConfig.getRubruImageProfilePath();
        userDTO.setImageUrl(imagePath);
        return userDTO;
    }

    @GetMapping("/account/image/{creatorLogin}")
    @Timed
    public String getAccountImage(@PathVariable String creatorLogin) throws IOException {
//        final String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        String imagePath = this.nwmsConfig.getRubruImageProfilePath();
        File tempFile = new File(imagePath + creatorLogin + ".jpg");
        boolean exists = tempFile.exists();
        JSONObject jsonObject = new JSONObject();
        if (exists) {
            byte[] fileContent = FileUtils.readFileToByteArray(new File(imagePath + creatorLogin + ".jpg"));
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            jsonObject.put("image", encodedString);
        } else {
            jsonObject.put("image", null);
        }
        return jsonObject.toString();
    }


    /**
     * POST  /account : update the current user information.
     *
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already used
     * @throws RuntimeException          500 (Internal Server Error) if the user login wasn't found
     */
    @PostMapping(value = "/account")
    @Timed
    public void saveAccount(@RequestBody String image,
                            @RequestParam(value = "firstName") String firstName,
                            @RequestParam(value = "lastName") String lastName,
                            @RequestParam(value = "email") String email
    ) throws IOException {
        NwmsUser currentUser = UserUtil.getCurrentUser();

        long countByEmail = userRepository.countUsingEmailIgnoreCase(email, currentUser.getId());
        if (countByEmail > 0) {
            throw new EmailAlreadyUsedException();
        }

        userService.updateUser(firstName, lastName, email,
            "fa", null);

        if (image != null && !image.equals(" ")) {
            String imagePath = this.nwmsConfig.getRubruImageProfilePath();
            byte[] decodedImg = Base64.getDecoder()
                .decode(image.getBytes(StandardCharsets.UTF_8));
            Path destinationFile = Paths.get(imagePath + currentUser.getUsername() + ".jpg");
            Files.write(destinationFile, decodedImg);
        }
    }

    /**
     * POST  /account/change-password : changes the current user's password
     *
     * @param passwordChangeDto current and new password
     * @throws InvalidPasswordException 400 (Bad Request) if the new password is incorrect
     */
    @PostMapping(path = "/account/change-password")
    @Timed
    public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        if (!checkPasswordLength(passwordChangeDto.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
    }

    /**
     * POST   /account/reset-password/init : Send an email to reset the password of the user
     *
     * @param vm the mail of the user
     * @throws EmailNotFoundException 400 (Bad Request) if the email address is not registered
     */
    @PostMapping(path = "/account/reset-password/init")
    @Timed
    public void requestPasswordReset(@RequestBody @Valid ResetPasswordVM vm) {
        try {
            if (!captchaUtil.validateCaptcha(vm.getClientKey(), vm.getCaptcha())) {
                throw new InvalidCaptchaValueException(null);
            }
            mailService.sendPasswordResetMail(
                userService.requestPasswordReset(vm.getEmail())
                    .orElseThrow(EmailNotFoundException::new)
            );
        } catch (EmailNotFoundException | MessagingException e) {
            log.warn(e.getMessage(), e);
        }
    }

    /**
     * POST   /account/reset-password/finish : Finish to reset the password of the user
     *
     * @param keyAndPassword the generated key and the new password
     * @throws InvalidPasswordException 400 (Bad Request) if the password is incorrect
     * @throws RuntimeException         500 (Internal Server Error) if the password could not be reset
     */
    @PostMapping(path = "/account/reset-password/finish")
    @Timed
    public void finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<User> user =
            userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this reset key");
        }
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= Constants.PASSWORD_MIN_LENGTH &&
            password.length() <= Constants.PASSWORD_MAX_LENGTH;
    }
}
