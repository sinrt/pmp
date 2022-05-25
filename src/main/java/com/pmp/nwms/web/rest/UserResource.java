package com.pmp.nwms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pmp.nwms.config.Constants;
import com.pmp.nwms.domain.User;
import com.pmp.nwms.repository.AuthorityRepository;
import com.pmp.nwms.repository.UserRepository;
import com.pmp.nwms.security.AuthoritiesConstants;
import com.pmp.nwms.service.AuditEventService;
import com.pmp.nwms.service.MailService;
import com.pmp.nwms.service.UserService;
import com.pmp.nwms.service.dto.UserDTO;
import com.pmp.nwms.web.rest.errors.BadRequestAlertException;
import com.pmp.nwms.web.rest.errors.EmailAlreadyUsedException;
import com.pmp.nwms.web.rest.errors.LoginAlreadyUsedException;
import com.pmp.nwms.web.rest.errors.PersonalCodeAlreadyUsedException;
import com.pmp.nwms.web.rest.util.HeaderUtil;
import com.pmp.nwms.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing users.
 * <p>
 * This class accesses the User entity, and needs to fetch its collection of authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this case.
 */
@RestController
@RequestMapping("/api")
public class UserResource {
    private final AuditEventService auditEventService;
    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;

    private final AuthorityRepository authorityRepository;

    private final UserRepository userRepository;

    private final MailService mailService;

    public UserResource(UserService userService, AuthorityRepository authorityRepository, UserRepository userRepository, MailService mailService, AuditEventService auditEventService) {
        this.auditEventService = auditEventService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.authorityRepository = authorityRepository;
    }

    /**
     * POST  /users  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param userDTO the user to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the login or email is already in use
     * @throws URISyntaxException       if the Location URI syntax is incorrect
     * @throws BadRequestAlertException 400 (Bad Request) if the login or email is already in use
     */
    @PostMapping("/users")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN})
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException, MessagingException {
        log.debug("REST request to updateClassroom User : {}", userDTO);

        if (userDTO.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userDTO.getEmail() != null && userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else if (userRepository.findUserByPersonalCode(userDTO.getPersonalCode()).isPresent()) {
            throw new PersonalCodeAlreadyUsedException();
        } else {//todo handle the empty email
            Set<String> strings = new HashSet<>();
            strings.add("ROLE_USER");
            userDTO.setAuthorities(strings);
            User newUser = userService.createUser(userDTO);
            if (newUser.getEmail() != null && !newUser.getEmail().isEmpty()) {
                mailService.sendCreationEmail(newUser);
            }
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert("userManagement.created", newUser.getLogin()))
                .body(newUser);
        }
    }

    /**
     * PUT /users : Updates an existing User.
     *
     * @param userDTO the user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already in use
     * @throws LoginAlreadyUsedException 400 (Bad Request) if the login is already in use
     */
    @PutMapping("/users")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN})
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to update User : {}", userDTO);
        Optional<User> existingUser = userRepository.findOneByLogin(userDTO.getLogin());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new LoginAlreadyUsedException();
        }
        existingUser = userRepository.findUserByPersonalCode(userDTO.getPersonalCode());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new PersonalCodeAlreadyUsedException();
        }
        Optional<UserDTO> updatedUser = userService.updateUser(userDTO);

        return ResponseUtil.wrapOrNotFound(updatedUser,
            HeaderUtil.createAlert("userManagement.updated", userDTO.getLogin()));
    }

    /**
     * GET /users : get all users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/allusers/{status}")
    @Timed
    public ResponseEntity<List<UserDTO>> getAllUsers(@PathVariable String status, Pageable pageable) {
        HttpHeaders headers = null;
        Page<UserDTO> page = null;
        if (status.equals("deactive")) {
            page = userService.getAllDeactiveUsers(pageable);
            headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/allusers");

        } else {
            page = userService.getAllManagedUsers(pageable);
            headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/allusers");
        }
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/users/searchUsers/{status}")
    @Timed
    public ResponseEntity<List<UserDTO>> searchUsers(@PathVariable String status, Pageable pageable) {
        HttpHeaders headers = null;
        Page<UserDTO> page = null;
        page = userService.searchUsers(status, pageable);
        headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users/searchUsers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * @return a string list of the all of the roles
     */
    @GetMapping("/users/authorities")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }

    /**
     * GET /users/:login : get the "login" user.
     *
     * @param login the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")//todo what is this?!
    @Timed
    public ResponseEntity<UserDTO> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        return ResponseUtil.wrapOrNotFound(
            userService.getUserWithAuthoritiesByLogin(login)
                .map(UserDTO::new));
    }


    /**
     * DELETE /users/:login : delete the "login" User.
     *
     * @param login the login of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        log.debug("REST request to delete User: {}", login);
        userService.deleteUser(login);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("userManagement.deleted", login)).build();
    }

    @PostMapping(value = {"/update/special/link/{specialLink}", "/update/special/link"})
    @Timed
    public ResponseEntity<Boolean> updateSpecialLink(@PathVariable(value = "specialLink", required = false) String specialLink) {
        log.debug("REST request to updateSpecialLink: {}", specialLink);
        userService.updateSpecialLink(specialLink);
        return ResponseEntity.ok().body(true);
    }
}
