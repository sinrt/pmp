package com.pmp.nwms.web.rest.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmp.nwms.domain.Classroom;
import com.pmp.nwms.domain.ServerConfig;
import com.pmp.nwms.domain.User;
import com.pmp.nwms.security.jwt.TokenProvider;
import com.pmp.nwms.service.*;
import com.pmp.nwms.service.dto.CallStatus;
import com.pmp.nwms.service.dto.RegistryContainer;
import com.pmp.nwms.service.dto.SignalServerLogDTO;
import com.pmp.nwms.service.dto.UserContainer;
import com.pmp.nwms.web.rest.servlet.model.CommandTransmitter;
import com.pmp.nwms.web.rest.servlet.model.SimagooKeyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@WebServlet(urlPatterns = "/kurento")
public class KurentoServlet extends HttpServlet {

    private final Logger log = LoggerFactory.getLogger(KurentoServlet.class);

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String json = req.getParameter("command");
        ObjectMapper objectMapper = new ObjectMapper();
        CommandTransmitter commandTransmitter = objectMapper.readValue(json, CommandTransmitter.class);

        ServletContext context = req.getServletContext();

        ApplicationContext ctx =
            WebApplicationContextUtils.getRequiredWebApplicationContext(context);

        register(resp, commandTransmitter, ctx);
        changeStatus(resp, commandTransmitter, ctx);
        removeUser(resp, commandTransmitter, ctx);
        getAllUserByStatus(resp, commandTransmitter, ctx);

//        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {

        String json = req.getParameter("command");
        ObjectMapper objectMapper = new ObjectMapper();
        CommandTransmitter commandTransmitter = objectMapper.readValue(json, CommandTransmitter.class);

        ServletContext context = req.getServletContext();

        ApplicationContext ctx =
            WebApplicationContextUtils.getRequiredWebApplicationContext(context);

        register(resp, commandTransmitter, ctx);
//        changeStatus(resp, commandTransmitter, ctx);
//        removeUser(resp, commandTransmitter, ctx);
        getAllUserByStatus(resp, commandTransmitter, ctx);
        checkValidity(resp, commandTransmitter, ctx);
        signUp(resp, commandTransmitter, ctx);
        logIn(resp, commandTransmitter, ctx);
        updatePassword(resp, commandTransmitter, ctx);
        generateClientToken(resp, commandTransmitter, ctx);
        getUserData(resp, commandTransmitter, ctx);
        left(resp, commandTransmitter, ctx);
        getFullNameBySimagooToken(resp, commandTransmitter, ctx);
        getIdBySimagooToken(resp, commandTransmitter, ctx);
        userIsOnTime(resp, commandTransmitter, ctx);
        checkJwtTokenValidity(resp, commandTransmitter, ctx);
        checkUnpublichPermision(resp, commandTransmitter, ctx);
//        getRubruServerConfig(resp, commandTransmitter, ctx);
        setSignalServerLog(resp, commandTransmitter, ctx);
//        oneToManyRegister(resp, commandTransmitter, ctx);

//        super.doPost(req, resp);
    }

    private void left(HttpServletResponse resp, CommandTransmitter commandTransmitter, ApplicationContext ctx) throws IOException {

        if (commandTransmitter.getCommand().trim().equals("left")) {

            List<SimagooKeyValue> simagooKeyValues = commandTransmitter.getSimagooKeyValues();

            SimagooKeyValue simagooKeyValue = simagooKeyValues.get(0);

            KurentoService kurentoService = (KurentoService) ctx.getBean("kurentoService");
            UserService userService = (UserService) ctx.getBean("userService");

            Collection<RegistryContainer> registryContainers = kurentoService.getUserMap().values();

            /*String jwtToken = "";
            String simagooToken = "";*/
            for (RegistryContainer registryContainer : registryContainers) {

                if (registryContainer.getSimagooToken().trim().equalsIgnoreCase(simagooKeyValue.getValue())) {

                    /*jwtToken = registryContainer.getJwtToken();
                    simagooToken = registryContainer.getSimagooToken();*/
                    Long userid = registryContainer.getUserId();

                    User user = userService.findUserById(userid);

                    if (user != null) {
                        String userName = user.getLogin();
                        kurentoService.remove(userName);
                        resp.getWriter().write("OK");
                        return;
                    }


                }

            }

//            registryContainers.remove(jwtToken);

            resp.getWriter().write("FAIL");
            return;
        }

    }


    private void getUserData(HttpServletResponse resp, CommandTransmitter commandTransmitter, ApplicationContext ctx) throws IOException {

        if (commandTransmitter.getCommand().trim().equals("getUserData")) {

            UserContainer userContainer = new UserContainer();

            String fullName = "";

            List<SimagooKeyValue> simagooKeyValues = commandTransmitter.getSimagooKeyValues();

            SimagooKeyValue simagooKeyValue = simagooKeyValues.get(0);

            KurentoService kurentoService = (KurentoService) ctx.getBean("kurentoService");

            Collection<RegistryContainer> registryContainers = kurentoService.getUserMap().values();

            for (RegistryContainer registryContainer : registryContainers) {

                if (registryContainer.getSimagooToken().trim().equalsIgnoreCase(simagooKeyValue.getValue())) {

                    fullName = registryContainer.getFullName();

                    userContainer.setFullName(fullName);
                    userContainer.setStatus(CallStatus.ONLINE.name());
                    userContainer.setSimagooToken(simagooKeyValue.getValue());

                }

            }

            ObjectMapper objectMapper = new ObjectMapper();

            String response = objectMapper.writeValueAsString(userContainer);

            resp.getWriter().write(response);

        }

    }


    private void checkJwtTokenValidity(HttpServletResponse resp, CommandTransmitter commandTransmitter, ApplicationContext ctx) throws IOException {

        String jwtToken = "";

        if (commandTransmitter.getCommand().trim().equals("checkJwtTokenValidity")) {

            TokenProvider tokenProvider = (TokenProvider) ctx.getBean("tokenProvider");

            List<SimagooKeyValue> simagooKeyValues = commandTransmitter.getSimagooKeyValues();

            for (SimagooKeyValue simagooKeyValue : simagooKeyValues) {

                if (simagooKeyValue.getKey().trim().equals("jwtToken")) {

                    jwtToken = simagooKeyValue.getValue();

                }

            }

//            if (tokenProvider.validateToken(jwtToken)) {

            resp.getWriter().write("OK");
            /*}else{

                resp.getWriter().write("FAIL");
            }*/
        }
    }

    private void checkUnpublichPermision(HttpServletResponse resp, CommandTransmitter commandTransmitter, ApplicationContext ctx) throws IOException {
        if (commandTransmitter.getCommand().trim().equals("checkUnpublichPermision")) {
            resp.getWriter().write("OK");
        }
    }


    /*private void getRubruServerConfig(HttpServletResponse resp, CommandTransmitter commandTransmitter, ApplicationContext ctx) throws IOException {

        if (commandTransmitter.getCommand().trim().equals("getRubruServerConfig")) {

            ServerConfigService serverConfigService = (ServerConfigService) ctx.getBean("serverConfigService");

            List<SimagooKeyValue> simagooKeyValues = commandTransmitter.getSimagooKeyValues();

            if (!simagooKeyValues.isEmpty()) {

                SimagooKeyValue simagooKeyValue = simagooKeyValues.get(0);

                String paramName = simagooKeyValue.getValue();

                Optional<ServerConfig> serverConfigOptional = serverConfigService.findByParamName(paramName);

                if (serverConfigOptional != null && serverConfigOptional.isPresent()) {

                    resp.getWriter().write(serverConfigOptional.get().getParamValue());

                } else {

                    log.error("KurentoServlet getRubruServerConfig : Result by ParamName" + paramName + " not found");

                    resp.getWriter().write("null");
                }

            } else {

                log.error("KurentoServlet getRubruServerConfig : ParamList is null");

                resp.getWriter().write("null");

            }

        }
    }*/

    private void setSignalServerLog(HttpServletResponse resp, CommandTransmitter commandTransmitter, ApplicationContext ctx) throws IOException {
        String simagooToken = "";
        if (commandTransmitter.getCommand().trim().equals("setSignalServerLog")) {
            SignalServerLogService signalserverlogservice = (SignalServerLogService) ctx.getBean("signalServerLogService");
            List<SimagooKeyValue> simagooKeyValues = commandTransmitter.getSimagooKeyValues();

            if (!simagooKeyValues.isEmpty()) {

                SimagooKeyValue simagooKeyValue = simagooKeyValues.get(0);

                String paramName = simagooKeyValue.getValue();
                SignalServerLogDTO signalServerLog = new SignalServerLogDTO();
                signalServerLog.setAction("SS Log");
                signalServerLog.setDescription(simagooKeyValue.getValue());
                signalServerLog.setCheck(false);
                signalserverlogservice.save(signalServerLog);
            }
        }
    }

    private void getFullNameBySimagooToken(HttpServletResponse resp, CommandTransmitter commandTransmitter, ApplicationContext ctx) throws IOException {

        String simagooToken = "";
        String jwtToken = "";

        if (commandTransmitter.getCommand().trim().equals("getFullNameBySimagooToken")) {

            TokenProvider tokenProvider = (TokenProvider) ctx.getBean("tokenProvider");

            KurentoService kurentoService = (KurentoService) ctx.getBean("kurentoService");

            RegistryContainer registryContainer = new RegistryContainer();

            List<SimagooKeyValue> simagooKeyValues = commandTransmitter.getSimagooKeyValues();

            for (SimagooKeyValue simagooKeyValue : simagooKeyValues) {

                if (simagooKeyValue.getKey().trim().equals("jwttoken")) {
                    jwtToken = simagooKeyValue.getValue();
                }

                if (simagooKeyValue.getKey().trim().equals("simagooToken")) {
                    simagooToken = simagooKeyValue.getValue();
                }

            }
            if (tokenProvider.validateToken(jwtToken)) {

                registryContainer = kurentoService.findBySimagooToken(simagooToken);
            }

            ObjectMapper objectMapper = new ObjectMapper();

            String response = objectMapper.writeValueAsString(registryContainer);

            resp.getWriter().write(response);

        }
    }


    private void getIdBySimagooToken(HttpServletResponse resp, CommandTransmitter commandTransmitter, ApplicationContext ctx) throws IOException {

        String simagooToken = "";

        if (commandTransmitter.getCommand().trim().equals("getIdBySimagooToken")) {

            TokenProvider tokenProvider = (TokenProvider) ctx.getBean("tokenProvider");

            KurentoService kurentoService = (KurentoService) ctx.getBean("kurentoService");

            RegistryContainer registryContainer = new RegistryContainer();

            List<SimagooKeyValue> simagooKeyValues = commandTransmitter.getSimagooKeyValues();

            for (SimagooKeyValue simagooKeyValue : simagooKeyValues) {


                if (simagooKeyValue.getKey().trim().equals("simagooToken")) {
                    simagooToken = simagooKeyValue.getValue();
                }

            }

            registryContainer = kurentoService.findBySimagooToken(simagooToken);

            ObjectMapper objectMapper = new ObjectMapper();

            String response = objectMapper.writeValueAsString(registryContainer);

            resp.getWriter().write(response);

        }
    }

    private void generateClientToken(HttpServletResponse resp, CommandTransmitter commandTransmitter, ApplicationContext ctx) throws IOException {

        String jwtToken = "";

        if (commandTransmitter.getCommand().trim().equals("getTokenByJWTToken")) {

            List<SimagooKeyValue> simagooKeyValues = commandTransmitter.getSimagooKeyValues();

            for (SimagooKeyValue simagooKeyValue : simagooKeyValues) {

                if (simagooKeyValue.getKey().trim().equals("jwttoken")) {
                    jwtToken = simagooKeyValue.getValue();
                }

            }

            TokenProvider tokenProvider = (TokenProvider) ctx.getBean("tokenProvider");

            KurentoService kurentoService = (KurentoService) ctx.getBean("kurentoService");

            RegistryContainer registryContainer = null;

            if (tokenProvider.validateToken(jwtToken)) {

                registryContainer = kurentoService.register(jwtToken);

                ObjectMapper objectMapper = new ObjectMapper();

                String response = objectMapper.writeValueAsString(registryContainer);

                resp.getWriter().write(response);

            } else {

                RegistryContainer newRegistryContainer = new RegistryContainer();

                ObjectMapper objectMapper = new ObjectMapper();

                String response = objectMapper.writeValueAsString(newRegistryContainer);

                resp.getWriter().write(response);
            }

        }

    }


    private void updatePassword(HttpServletResponse resp, CommandTransmitter commandTransmitter, ApplicationContext ctx) throws IOException {

        if (commandTransmitter.getCommand().trim().equals("updatePassword")) {

            UserService userManager = (UserService) ctx.getBean("userService");

            List<SimagooKeyValue> simagooKeyValues = commandTransmitter.getSimagooKeyValues();

            String userName = null;

            for (SimagooKeyValue simagooKeyValue : simagooKeyValues) {

                if (simagooKeyValue.getKey().trim().equals("username")) {
                    userName = simagooKeyValue.getValue();
                }

            }

            Optional<User> optimalUser = userManager.findUserByUserName(userName);

            User user = optimalUser.get();

            if (user == null) {

                resp.getWriter().write("UPDATE_FAIL");
                return;
            }

            String password = "1";

            user.setPassword(password);

            userManager.updateKurentoPassword(user, password);
            //SMSSender.send(userName, password);
            resp.getWriter().write("UPDATE_OK");


        }

    }

    private void logIn(HttpServletResponse resp, CommandTransmitter commandTransmitter, ApplicationContext ctx) throws IOException {

        if (commandTransmitter.getCommand().trim().equals("Login")) {

            UserService userManager = (UserService) ctx.getBean("userService");

            List<SimagooKeyValue> simagooKeyValues = commandTransmitter.getSimagooKeyValues();

            String userName = null;
            String password = null;

            for (SimagooKeyValue simagooKeyValue : simagooKeyValues) {

                if (simagooKeyValue.getKey().trim().equals("username")) {
                    userName = simagooKeyValue.getValue();
                }

                if (simagooKeyValue.getKey().trim().equals("password")) {
                    password = simagooKeyValue.getValue();
                }
            }

            User user = userManager.findUserByUserNamePassword(userName, password);

            if (user == null) {

                resp.getWriter().write("LOGIN_FAIL");
                return;
            }

            resp.getWriter().write("LOGIN_OK");

        }

    }

    private void signUp(HttpServletResponse resp, CommandTransmitter commandTransmitter, ApplicationContext ctx) throws IOException {

        if (commandTransmitter.getCommand().trim().equals("signUp")) {

            UserService userManager = (UserService) ctx.getBean("userService");

            List<SimagooKeyValue> simagooKeyValues = commandTransmitter.getSimagooKeyValues();

            String firstName = null;
            String lastName = null;
            String email = null;
            String phoneNumber = null;

            for (SimagooKeyValue simagooKeyValue : simagooKeyValues) {

                if (simagooKeyValue.getKey().trim().equals("firstname")) {
                    firstName = simagooKeyValue.getValue();
                }

                if (simagooKeyValue.getKey().trim().equals("lastName")) {
                    lastName = simagooKeyValue.getValue();
                }
                if (simagooKeyValue.getKey().trim().equals("email")) {
                    email = simagooKeyValue.getValue();
                }
                if (simagooKeyValue.getKey().trim().equals("phoneNumber")) {
                    phoneNumber = simagooKeyValue.getValue();
                }

            }

            String password = "1";

            Optional<User> optionalUser = userManager.findUserByPhoneNumber(phoneNumber);

            if (optionalUser == null) {

                User fetchedUser = optionalUser.get();

                if (fetchedUser == null) {

                    User user = new User();
                    user.setLogin(phoneNumber);
                    user.setPhoneNumber(phoneNumber);
                    user.setPassword(password);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setEmail(email);

                    userManager.kurentoSignup(user);
//                        SMSSender.send(phoneNumber, password);
                    resp.getWriter().write("SIGNUP_OK");


                } else {

                    fetchedUser.setPassword(password);

                    userManager.kurentoSignup(fetchedUser);
//                        SMSSender.send(phoneNumber, password);
                    resp.getWriter().write("SIGNUP_OK");


                }

            } else {

                resp.getWriter().write("SIGNUP_DUPLICATED_EMAIL");

            }
        }


    }

    private void getAllUserByStatus(HttpServletResponse resp, CommandTransmitter commandTransmitter, ApplicationContext ctx) throws IOException {

        if (commandTransmitter.getCommand().trim().equals("getAllUsers")) {

            KurentoService kurentoRegistrar = (KurentoService) ctx.getBean("kurentoService");

            String response = kurentoRegistrar.getAllUserByStatus();

            resp.getWriter().write(response);

            resp.getWriter().flush();
        }

    }

    private void checkValidity(HttpServletResponse resp, CommandTransmitter commandTransmitter, ApplicationContext ctx) throws IOException {

        if (commandTransmitter.getCommand().trim().equals("checkValidity")) {

            UserService userManager = (UserService) ctx.getBean("userService");

            KurentoService kurentoRegistrar = (KurentoService) ctx.getBean("kurentoService");

            List<SimagooKeyValue> simagooKeyValues = commandTransmitter.getSimagooKeyValues();

            String userName = null;
            String token = null;

            for (SimagooKeyValue simagooKeyValue : simagooKeyValues) {

                if (simagooKeyValue.getKey().trim().equals("username")) {
                    userName = simagooKeyValue.getValue();
                }

                if (simagooKeyValue.getKey().trim().equals("token")) {
                    token = simagooKeyValue.getValue();
                }
            }

            String hashedPassword = Encryptor.decrypt("Bar12345Bar12345", "RandomInitVector", token);

            User user = userManager.findUserByUserNamePassword(userName, hashedPassword);


            if (user != null) {

                resp.getWriter().write("OK");

            } else {

                resp.getWriter().write("FAIL");
            }

            resp.getWriter().flush();

        }

    }

    private void removeUser(HttpServletResponse resp, CommandTransmitter commandTransmitter, ApplicationContext ctx) throws IOException {

        if (commandTransmitter.getCommand().trim().equals("removeUser")) {

            KurentoService kurentoRegistrar = (KurentoService) ctx.getBean("kurentoService");

            List<SimagooKeyValue> simagooKeyValues = commandTransmitter.getSimagooKeyValues();

            String userName = null;

            for (SimagooKeyValue simagooKeyValue : simagooKeyValues) {

                if (simagooKeyValue.getKey().trim().equals("username")) {

                    userName = simagooKeyValue.getValue();

                }

            }

            kurentoRegistrar.remove(userName);

        }
    }

    private void changeStatus(HttpServletResponse resp, CommandTransmitter commandTransmitter, ApplicationContext ctx) throws IOException {

        if (commandTransmitter.getCommand().trim().equals("statusChanged")) {

            KurentoService kurentoService = (KurentoService) ctx.getBean("kurentoService");

            List<SimagooKeyValue> simagooKeyValues = commandTransmitter.getSimagooKeyValues();

            String userName = null;
            CallStatus callStatus = null;

            for (SimagooKeyValue simagooKeyValue : simagooKeyValues) {

                if (simagooKeyValue.getKey().trim().equals("username")) {
                    userName = simagooKeyValue.getValue();
                }

                if (simagooKeyValue.getKey().trim().equals("callStatus")) {
                    callStatus = CallStatus.valueOf(simagooKeyValue.getValue());
                }
            }


            kurentoService.setCallStatus(userName, callStatus);

        }
    }

    private void register(HttpServletResponse resp, CommandTransmitter commandTransmitter, ApplicationContext ctx) throws IOException {

        if (commandTransmitter.getCommand().trim().equals("register")) {

            UserService userManager = (UserService) ctx.getBean("userService");

            List<SimagooKeyValue> simagooKeyValues = commandTransmitter.getSimagooKeyValues();

            String userName = null;
            String password = null;

            for (SimagooKeyValue simagooKeyValue : simagooKeyValues) {

                if (simagooKeyValue.getKey().trim().equals("username")) {
                    userName = simagooKeyValue.getValue();
                }

                if (simagooKeyValue.getKey().trim().equals("password")) {
                    password = simagooKeyValue.getValue();
                }
            }

            String userValidityExpression = userManager.kurentoRegister(userName, password);

            resp.getWriter().write(userValidityExpression);

        }
    }

    private void userIsOnTime(HttpServletResponse resp, CommandTransmitter commandTransmitter, ApplicationContext ctx) throws IOException {

        if (commandTransmitter.getCommand().trim().equals("userIsOnTime")) {

            ClassroomService classroomService = (ClassroomService) ctx.getBean("classroomService");

            List<SimagooKeyValue> simagooKeyValues = commandTransmitter.getSimagooKeyValues();

            String userName = null;

            for (SimagooKeyValue simagooKeyValue : simagooKeyValues) {

                if (simagooKeyValue.getKey().trim().equals("username")) {

                    userName = simagooKeyValue.getValue();

                }


            }

            KurentoService kurentoService = (KurentoService) ctx.getBean("kurentoService");

            RegistryContainer registryContainer = kurentoService.findBySimagooToken(userName);

            if (registryContainer != null) {

                Classroom classroom = classroomService.findClassByUserIdAndDate(registryContainer.getUserId());

                if (classroom != null) {

                    resp.getWriter().write("OK");
                    return;
                } else {
                    resp.getWriter().write("FAIL");
                    return;
                }
            }

            resp.getWriter().write("FAIL");

        }
    }

}
