package com.pmp.nwms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.security.jwt.JWTConfigurer;
import com.pmp.nwms.security.jwt.TokenProvider;
import com.pmp.nwms.service.PersistentAuditEventService;
import com.pmp.nwms.util.StringUtil;
import com.pmp.nwms.util.captcha.CaptchaInfoDto;
import com.pmp.nwms.util.captcha.CaptchaUtil;
import com.pmp.nwms.web.rest.errors.InvalidCaptchaValueException;
import com.pmp.nwms.web.rest.errors.MissingRequiredParamException;
import com.pmp.nwms.web.rest.errors.RestAuthenticationException;
import com.pmp.nwms.web.rest.vm.LoginVM;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.*;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final CaptchaUtil captchaUtil;
    private final PersistentAuditEventService persistentAuditEventService;
    private Random rand = new Random(System.currentTimeMillis());
    //    private final ServerConfigServiceImpl serverConfig;
    private NwmsConfig nwmsConfig;
    private String token;


    public UserJWTController(TokenProvider tokenProvider, AuthenticationManager authenticationManager, NwmsConfig nwmsConfig, CaptchaUtil captchaUtil, PersistentAuditEventService persistentAuditEventService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.nwmsConfig = nwmsConfig;
        this.captchaUtil = captchaUtil;
        this.persistentAuditEventService = persistentAuditEventService;
    }

    @GetMapping("/getCaptcha")
    @Timed
    public ResponseEntity<List<String>> getImage() {
        CaptchaInfoDto captchaInfoDto = captchaUtil.generateCaptcha();
        return ResponseEntity.ok()
            .header("client-key", captchaInfoDto.getClientKey())
            .body(Collections.singletonList(captchaInfoDto.getCaptchaImageFileContent()));
    }

    @GetMapping("/getLoginTry")
    @Timed
    public Integer getUserLoginTry() {
        //todo this service must be deleted
        return 0;
    }


    @PostMapping("/getsystemip")
    @Timed
    public ResponseEntity<String> getSystemIp() {
        String param = nwmsConfig.getRubruWsUrl();
//        return "{\"value\":\"" + param + "\"}";
        return ResponseUtil.wrapOrNotFound(
            Optional.of("{\"value\":\"" + param + "\"}"), null);
    }

    public String createCaptcha() throws IOException {
        Color background = Color.WHITE;
        Color foreground = new Color(64, 64, 64);
        int length = 5;
        int size = 22;
        int padding = 5;
        int complexity = 5;
        try {
            background = new Color(255, 131, 0);
            foreground = new Color(254, 255, 127);
            size = 25;
            complexity = 8;
            length = 25;
        } catch (Exception ignored) {
        }
        token = RandomStringUtils.randomNumeric(6);

        BufferedImage buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = buffer.createGraphics();
        Font font = new Font("Arial", Font.BOLD, size);
        Rectangle2D r = font.getStringBounds(token, g2d.getFontRenderContext());
        buffer = new BufferedImage(
            (int) r.getWidth() + padding * 4,
            (int) r.getHeight() + padding * 2,
            BufferedImage.TYPE_INT_RGB);
        g2d = (Graphics2D) buffer.getGraphics();
        g2d.setFont(font);
        g2d.setColor(background);
        g2d.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
        g2d.setColor(foreground);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
        g2d.drawString(token, (int) (padding / 1), buffer.getHeight() - padding * size / 15);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
        g2d.drawString(token, (int) (padding / 2), buffer.getHeight() - (int) (padding * size / 14));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g2d.drawString(token, (int) (padding / 0.8), buffer.getHeight() - (int) (padding * size / 18));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
        g2d.drawString(token, (int) (padding / 1.9), buffer.getHeight() - (int) (padding * size / 17));
        BufferedImage buffer2 = new BufferedImage(
            buffer.getWidth(),
            buffer.getHeight(),
            buffer.getType());
        Graphics2D g2d2 = (Graphics2D) buffer2.getGraphics();
        g2d2.drawImage(buffer, 0, 0, null);
        double seed = rand.nextDouble() * 3d + 5d;
        for (int x = 0; x < buffer.getWidth(); x++) {
            for (int y = 0; y < buffer.getHeight(); y++) {
                int xx = x + (int) (Math.cos((double) y / seed) * ((double) complexity / 2d));
                int yy = y + (int) (Math.sin((double) x / (seed + 1)) * ((double) complexity / 2d));
                xx = Math.abs(xx % buffer.getWidth());
                yy = Math.abs(yy % buffer.getHeight());
                buffer.setRGB(x, y, buffer2.getRGB(xx, yy));
            }
        }
        int red = 102;
        int green = 163;
        int blue = 255;
        int alpha = 100;
        int col = (alpha << 24) | (red << 16) | (green << 8) | blue;
        int h = buffer.getHeight();
        int w = buffer.getWidth();
        Random random = new Random();
        int hh;
        int ww;
        for (int i = 0; i < 360; i++) {
            hh = random.nextInt(h);
            ww = random.nextInt(w);
            buffer.setRGB(ww, hh, col);
        }

        File outputfile = new File("c:\\captcha\\image.jpg");
        ImageIO.write(buffer, "jpg", outputfile);
        String encodeImage = Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(outputfile.toPath()));
        return encodeImage;
    }

    @PostMapping("/authenticate")
    @Timed
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
        String jwt;
        String username = StringUtil.convertPersianNumbers(loginVM.getUsername());
        String password = StringUtil.convertPersianNumbers(loginVM.getPassword());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            if (persistentAuditEventService.isCaptchaRequired(loginVM.getUsername())) {
                HashMap<String, Object> extraInfo = new HashMap<>();
                extraInfo.put("sendCaptcha", true);
                if (StringUtils.isEmpty(loginVM.getCaptcha())) {
                    throw new MissingRequiredParamException("LoginVM", "captcha", extraInfo);
                }
                if (StringUtils.isEmpty(loginVM.getClientKey())) {
                    throw new MissingRequiredParamException("LoginVM", "clientKey", extraInfo);
                }
                if (!captchaUtil.validateCaptcha(loginVM.getClientKey(), loginVM.getCaptcha())) {
                    throw new InvalidCaptchaValueException(extraInfo);
                }
            }
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
            ((UsernamePasswordAuthenticationToken) authentication).setDetails(1);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
            jwt = tokenProvider.createToken(authentication, rememberMe);
        } catch (MissingRequiredParamException | InvalidCaptchaValueException e) {
            throw e;
        } catch (Exception e) {
            throw new RestAuthenticationException(persistentAuditEventService.isCaptchaRequired(loginVM.getUsername()));
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
