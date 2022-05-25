package com.pmp.nwms.util;

import com.pmp.nwms.config.Constants;
import com.pmp.nwms.domain.Authority;
import com.pmp.nwms.domain.OrganizationLevel;
import com.pmp.nwms.domain.User;
import com.pmp.nwms.repository.AuthorityRepository;
import com.pmp.nwms.repository.UserRepository;
import com.pmp.nwms.security.NwmsUser;
import com.pmp.nwms.service.OrganizationLevelService;
import com.pmp.nwms.service.dto.UserDTO;
import com.pmp.nwms.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class UserUtil {
    private static final Logger log = LoggerFactory.getLogger(UserUtil.class);

    public static NwmsUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null && authentication.getPrincipal() instanceof NwmsUser) {
            return (NwmsUser) authentication.getPrincipal();
        } else {
            return null;
        }
    }


    public static User getCurrentUserEntity_(UserRepository userRepository) {
        NwmsUser currentUser = getCurrentUser();
        if (currentUser != null) {
            return userRepository.getOne(currentUser.getId());
        } else {
            return null;
        }
    }

    public static User createUser(UserDTO userDTO, OrganizationLevelService organizationLevelService, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        User user = new User();
        user.setLogin(StringUtil.convertPersianNumbers(userDTO.getLogin()).toLowerCase());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail().toLowerCase());
        }
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getOrganizationLevel() != null) {
            OrganizationLevel organizationLevel = organizationLevelService.findOrganizationLevelByName(userDTO.getOrganizationLevel());
            user.setOrganizationLevel(organizationLevel);
        }
        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = userDTO.getAuthorities().stream()
                .map(authorityRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }
        String encryptedPassword = null;
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        } else {
            encryptedPassword = passwordEncoder.encode(StringUtil.convertPersianNumbers(userDTO.getPassword()));
        }
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setPersonalCode(userDTO.getPersonalCode());
        user.setResetDate(new Date());
        user.setActivated(true);
        user.setRecordHashCode(UserUtil.calculateUserRecordHashCode(user));
        user = userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }


    public static void clearUserCaches(User user, CacheManager cacheManager) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        if (user.getEmail() != null)
            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
    }

    public static User updateUser(UserDTO userDTO, OrganizationLevelService organizationLevelService, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder, UserRepository userRepository, CacheManager cacheManager) {
        User user = userRepository.getOne(userDTO.getId());
        clearUserCaches(user, cacheManager);
        user.setLogin(userDTO.getLogin().toLowerCase());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail().toLowerCase());
        }
        user.setImageUrl(userDTO.getImageUrl());
        user.setActivated(userDTO.isActivated());
        if (userDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(userDTO.getPhoneNumber());
        }
        if (userDTO.getOrganizationLevel() != null) {
            OrganizationLevel organizationLevel = organizationLevelService.findOrganizationLevelByName(userDTO.getOrganizationLevel());
            user.setOrganizationLevel(organizationLevel);
        }
        if (!userDTO.getPassword().equals("1")) {
            String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
            user.setPassword(encryptedPassword);
        }
        user.setLangKey(userDTO.getLangKey());
        user.setPersonalCode(userDTO.getPersonalCode());
        Set<Authority> managedAuthorities = user.getAuthorities();
        managedAuthorities.clear();
        if (userDTO.getAuthorities() != null) {
            userDTO.getAuthorities().stream()
                .map(authorityRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(managedAuthorities::add);
        }

        clearUserCaches(user, cacheManager);
        log.debug("Changed Information for User: {}", user);
        return user;
    }

    public static boolean hasAuthority(String authority) {
        Collection<GrantedAuthority> authorities = getCurrentUser().getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if(grantedAuthority.getAuthority().equals(authority)){
                return true;
            }
        }
        return false;
    }

    public static int calculateUserRecordHashCode(User user){
        return user.getLogin().hashCode();
    }
}
