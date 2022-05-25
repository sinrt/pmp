package com.pmp.nwms.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmp.nwms.domain.User;
import com.pmp.nwms.security.jwt.TokenProvider;
import com.pmp.nwms.service.dto.CallStatus;
import com.pmp.nwms.service.dto.RegistryContainer;
import com.pmp.nwms.service.dto.UserContainer;
import com.pmp.nwms.service.util.WebsocketClientEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service("kurentoService")
@Scope("singleton")
@Transactional
public class KurentoService {

    @Autowired
    private UserService userService;

    private Map<String, RegistryContainer> userMap = new HashMap<>();

    @Autowired
    private TokenProvider tokenProvider;

    public RegistryContainer register(String jwtToken) {

        Authentication authentication = tokenProvider.getAuthentication(jwtToken);

        if (authentication != null) {

            String userName = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();

            if (userMap.get(userName)==null){

                User user = userService.findUserByUserName(userName).get();

                String fullName = user.getFirstName() + " " + user.getLastName();

                String simagooToken = generateString();

                RegistryContainer registryContainer = new RegistryContainer(simagooToken, fullName,jwtToken,user.getId());

                userMap.put(userName, registryContainer);

                return registryContainer;

            }else{

                RegistryContainer registryContainer = userMap.get(userName);

                return registryContainer;
            }


        }
        return null;
    }


    public RegistryContainer findRegistryContainerByUserId(Long userId){

        Collection<RegistryContainer> registryContainers=userMap.values();

        for (RegistryContainer registryContainer : registryContainers) {

            if (registryContainer.getUserId().equals(userId))
                return registryContainer;

        }

        return null;
    }

    public RegistryContainer findBySimagooToken(String simagooToken){

        Collection<RegistryContainer> registryContainers= userMap.values();

        for (RegistryContainer registryContainer : registryContainers) {
            if (registryContainer.getSimagooToken().trim().equals(simagooToken)){
                return registryContainer;
            }
        }

        return null;
    }


    public void register(RegistryContainer registryContainer, String userName) {

        userMap.put(userName, registryContainer);
    }

    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }


    public CallStatus getCallStatus(String userName) {
        return userMap.get(userName).getCallStatus();
    }

    public void setCallStatus(String userName, CallStatus callStatus) {

        RegistryContainer registryContainer = userMap.get(userName);
        registryContainer.setCallStatus(callStatus);

    }

    public void remove(String userName) {
        userMap.remove(userName);
    }

    public String getAllUserByStatus() {

        Set<String> userNames = userMap.keySet();

        List<UserContainer> userContainers = new ArrayList<>();

        for (String userName : userNames) {

            RegistryContainer registryContainer = userMap.get(userName);
            UserContainer userContainer = new UserContainer(registryContainer.getFullName(), CallStatus.ONLINE.name(),registryContainer.getSimagooToken());
            userContainers.add(userContainer);
        }

        List<User> users = userService.getAll();

        for (User user : users) {

            if (userMap.get(user.getLogin()) == null) {
                UserContainer userContainer = new UserContainer(user.getFirstName() + " " + user.getLastName(), CallStatus.OFFLINE.name(),"");
                userContainers.add(userContainer);
            }

        }

        ObjectMapper objectMapper = new ObjectMapper();

        try {

            return objectMapper.writeValueAsString(userContainers);

        } catch (IOException e) {
            e.printStackTrace();
            return "NULL";
        }
    }


    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    public TokenProvider getTokenProvider() {
        return tokenProvider;
    }

    public void setTokenProvider(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public Map<String, RegistryContainer> getUserMap() {
        return userMap;
    }
}
