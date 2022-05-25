package com.pmp.nwms.web.rest;

import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.config.Constants;
import com.pmp.nwms.repository.PurchaseStatusRepository;
import com.pmp.nwms.repository.UserRepository;
import com.pmp.nwms.security.NwmsUser;
import com.pmp.nwms.service.PurchaseLogService;
import com.pmp.nwms.service.PurchaseStatusService;
import com.pmp.nwms.service.dto.PurchaseLogDTO;
import com.pmp.nwms.service.dto.PurchaseStatusDTO;
import com.pmp.nwms.util.ApplicationDataStorage;
import com.pmp.nwms.util.UserUtil;
import com.pmp.nwms.web.rest.errors.BadRequestAlertException;
import com.pmp.nwms.web.rest.util.HeaderUtil;
import com.pmp.nwms.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * REST controller for managing PurchaseStatus.
 */
@RestController
@RequestMapping("/api")
public class PurchaseStatusResource {

    @Autowired
    private ApplicationDataStorage applicationDataStorage;

    @Autowired
    private PurchaseStatusRepository purchaseStatusRepository;

    @Autowired
    private PurchaseLogService purchaseLogService;

    @Autowired
    private NwmsConfig nwmsConfig;

    @Autowired
    private UserRepository userRepository;

    Random rand = new Random();
    private final Logger log = LoggerFactory.getLogger(PurchaseStatusResource.class);

    private static final String ENTITY_NAME = "purchaseStatus";

    private final PurchaseStatusService purchaseStatusService;

    public PurchaseStatusResource(PurchaseStatusService purchaseStatusService) {
        this.purchaseStatusService = purchaseStatusService;
    }

    private String getSaltString() {
        String SALTCHARS = "1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public String getEncodedDataByPrivate(String data) {
        /*String privateXml = "<RSAKeyValue>\n" +
            "    <Modulus>\n" +
            "        2xU3CEeoEnpImFyskuaV/4kZ7tPhB6M95H0AeUcNk9t6fkSz4Qnt2zyb/zM/ifTq2wl0bGacmB1zlnGZr3RWlVMPxaXWbLtDjw0m4WnZW/zPHaxEZzfnkulZUOUAFH9Q4e2OIeu158IF5dboZpbE341FSWWtqRNLExojw0Fg2G0=\n" +
            "    </Modulus>\n" +
            "    <Exponent>AQAB</Exponent>\n" +
            "    <P>7OeXRhnWPaIsF7NqAlFsHKJHb5mO1aOpt8ufN9icWJ+3IUiUExJR7QywKmf2xC62VqcHPhkHI1cSpOX9Yxp/pw==</P>\n" +
            "    <Q>7L3haUd4mgDF6ajZU77JnqiO64ua295OULAV+YM00m8zxZOicPk2pedELUYPYdGdbaPqXzbMtTDyXTufojFJyw==</Q>\n" +
            "    <DP>WW1WwkSQhfWI1W2Jj9RyjiCH/M2niGfech3wPWGMKzwnIe5Gzyg0Otp2SZuzH/1OIUmtxzglZKbCx/J1gwqGsw==</DP>\n" +
            "    <DQ>tdZ65sRwy8lAOuLoWry2XFkXvbAzOCIegTdfCZFP5zpmx6FIKgIhiXiDE0s20WmRw/8dLBkJTzrTTe1YBdpZ1Q==</DQ>\n" +
            "    <InverseQ>sjEZJQ0VR15XsQSompbipuoEuQFm+3BbP2JYG7mCShtHzhGUD1B36qYJdrgmNzyuYQka5djnjV2Olj/lcYPPZg==</InverseQ>\n" +
            "    <D>\n" +
            "        SoUI1xQoo0Bc+RZszPsKAoWMAKSpYoOwRcwPITF9+NEq9VBBL0bFVVFkboXsEuzwBdlNdKHimgtKid3SPNABsuAVvGAIrkYDF+gu314ziYO3jtqVGcRhQzFBr/iG4zAB0BjC5CO62+2S2OZB4RP6huGx5bIN2L3IIz8fXAoLz7E=\n" +
            "    </D>\n" +
            "</RSAKeyValue>";*/
        /*String privateXml = "<RSAKeyValue>" +
            "<Modulus>" +
            nwmsConfig.getPassargadIpgRSAKeyValueModulus() +
            "</Modulus>" +
            "<Exponent>" +
            nwmsConfig.getPassargadIpgRSAKeyValueExponent() +
            "</Exponent>" +
            "<P>" +
            nwmsConfig.getPassargadIpgRSAKeyValueP() +
            "</P>" +
            "<Q>" +
            nwmsConfig.getPassargadIpgRSAKeyValueQ() +
            "</Q>" +
            "<DP>" +
            nwmsConfig.getPassargadIpgRSAKeyValueDP() +
            "</DP>" +
            "<DQ>" +
            nwmsConfig.getPassargadIpgRSAKeyValueDQ() +
            "</DQ>" +
            "<InverseQ>" +
            nwmsConfig.getPassargadIpgRSAKeyValueInverseQ() +
            "</InverseQ>" +
            "<D>" +
            nwmsConfig.getPassargadIpgRSAKeyValueD() +
            "</D>" +
            "</RSAKeyValue>";*/
       /* DocumentBuilder db = null;*/
        String finalBase64Encode = null;
        try {
//            db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//            Element el = db.parse(new ByteArrayInputStream(privateXml.getBytes())).getDocumentElement();
            String[] tagNames = {"Modulus", "Exponent", "D", "P", "Q", "DP", "DQ", "InverseQ"};
            BigInteger[] vals = new BigInteger[tagNames.length];
            /*for (int i = 0; i < tagNames.length; i++) {
                String v = el.getElementsByTagName(tagNames[i]).item(0).getTextContent();
                vals[i] = new BigInteger(1, DatatypeConverter.parseBase64Binary(v));
            }*/
            vals[0] = new BigInteger(1, DatatypeConverter.parseBase64Binary(nwmsConfig.getPassargadIpgRSAKeyValueModulus()));
            vals[1] = new BigInteger(1, DatatypeConverter.parseBase64Binary(nwmsConfig.getPassargadIpgRSAKeyValueExponent()));
            vals[2] = new BigInteger(1, DatatypeConverter.parseBase64Binary(nwmsConfig.getPassargadIpgRSAKeyValueD()));
            vals[3] = new BigInteger(1, DatatypeConverter.parseBase64Binary(nwmsConfig.getPassargadIpgRSAKeyValueP()));
            vals[4] = new BigInteger(1, DatatypeConverter.parseBase64Binary(nwmsConfig.getPassargadIpgRSAKeyValueQ()));
            vals[5] = new BigInteger(1, DatatypeConverter.parseBase64Binary(nwmsConfig.getPassargadIpgRSAKeyValueDP()));
            vals[6] = new BigInteger(1, DatatypeConverter.parseBase64Binary(nwmsConfig.getPassargadIpgRSAKeyValueDQ()));
            vals[7] = new BigInteger(1, DatatypeConverter.parseBase64Binary(nwmsConfig.getPassargadIpgRSAKeyValueInverseQ()));
            PrivateKey pk = KeyFactory.getInstance("RSA").generatePrivate(
                new RSAPrivateCrtKeySpec(vals[0], vals[1], vals[2], vals[3], vals[4], vals[5], vals[6], vals[7]));
            Signature si = Signature.getInstance("SHA1withRSA");
            si.initSign(pk);
            si.update(data.getBytes(StandardCharsets.UTF_8));
            byte[] ss = si.sign();
            finalBase64Encode = Base64.getEncoder().encodeToString(ss);
        } catch (InvalidKeySpecException  | SignatureException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        }
        return finalBase64Encode;
    }

    @PostMapping("/purchase-statuses")
    public ResponseEntity<PurchaseStatusDTO> createPurchaseStatus(@Valid @RequestBody PurchaseStatusDTO purchaseStatusDTO) throws Exception {
        log.info("REST request to updateClassroom PurchaseStatus : {}", purchaseStatusDTO);

        SimpleDateFormat invoiceDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String invoiceDateString = invoiceDateFormat.format(new Date());

        SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String timestampString = timestampFormat.format(new Date());

        String invoiceNumber = getSaltString();
        Integer finalPrice = purchaseStatusService.getFinalPrice(purchaseStatusDTO);

        String redirectAddress = nwmsConfig.getAppWebUrl() + "/panels/successpurchase/";
        String varsendingData = "{ \"InvoiceNumber\": \"" + invoiceNumber + "\", \"InvoiceDate\":    " +
            "\"" + invoiceDateString + "\",\"TerminalCode\": \"" + nwmsConfig.getPassargadIpgTerminalCode() + "\", \"MerchantCode\": \"" + nwmsConfig.getPassargadIpgMerchantCode() + "\", \"Amount\":\"" + finalPrice + "\"" +
            ", \"RedirectAddress\":\"" + redirectAddress + "\", \"Timestamp\":\"" + timestampString + "\", \"Action\":\"1003\", " +
            "\"Mobile\":\"\", \"Email\":\"\" }";
        log.info("varsendingData = " + varsendingData);
        String username = UserUtil.getCurrentUser().getUsername();
        applicationDataStorage.add(username + "_varsendingData", varsendingData);


        String url = nwmsConfig.getPassargadIpgGetTokenAddress();
        HttpClient client = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost(url);

        post.setHeader("Content-type", "application/json; charset=utf-8");
        post.setHeader("Sign", getEncodedDataByPrivate(varsendingData));

        StringEntity entity = new StringEntity(varsendingData);
        post.setEntity(entity);
        HttpResponse response = client.execute(post);

        BufferedReader rd = new BufferedReader(
            new InputStreamReader(response.getEntity().getContent()));

        StringBuffer reqResult = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            reqResult.append(line);
        }
        log.info("get token response content : " + String.valueOf(reqResult));
        JSONParser parser = new JSONParser();
        JSONObject resultToJson = (JSONObject) parser.parse(String.valueOf(reqResult));

        checkAfterToken((boolean) resultToJson.get("IsSuccess"));
        PurchaseStatusDTO purchaseStatus = new PurchaseStatusDTO();

        purchaseStatus.setTrackingCode((String) resultToJson.get("Token"));
        return ResponseEntity.created(new URI("/api/purchase-statuses/"))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, "")).body(purchaseStatus);
    }

    public void checkAfterToken(boolean tokenState) {
        String username = UserUtil.getCurrentUser().getUsername();
        Long purchaselog = (Long) applicationDataStorage.get(username + "_purchaselog");
        Optional<PurchaseLogDTO> log = purchaseLogService.findOne(purchaselog);
        log.get().setGettingToken(tokenState);
        log.get().setRedirectToPort(tokenState);
        String oldInfo = (String) applicationDataStorage.get(username + "_varsendingData");
        JSONObject oldInfoToJson;
        try {
            JSONParser parser = new JSONParser();
            oldInfoToJson = (JSONObject) parser.parse(String.valueOf(oldInfo));
            log.get().setMerchantCode((String) oldInfoToJson.get("MerchantCode"));
            log.get().setTerminalCode((String) oldInfoToJson.get("TerminalCode"));
            log.get().setInvoiceNumber((String) oldInfoToJson.get("InvoiceNumber"));
            Date startDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse((String) oldInfoToJson.get("Timestamp"));
            ZoneId z = ZoneId.of(Constants.SYSTEM_TIME_ZONE);
            ZonedDateTime zdtStart = startDate.toInstant().atZone(z);
            log.get().setPurchaseStartTime(startDate);
        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }
        purchaseLogService.save(log.get());

    }

    /**
     * PUT  /purchase-statuses : Updates an existing purchaseStatus.
     *
     * @param purchaseStatusDTO the purchaseStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated purchaseStatusDTO,
     * or with status 400 (Bad Request) if the purchaseStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the purchaseStatusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/purchase-statuses")
    public ResponseEntity<PurchaseStatusDTO> updatePurchaseStatus(@Valid @RequestBody PurchaseStatusDTO purchaseStatusDTO) throws URISyntaxException {
        log.info("REST request to update PurchaseStatus : {}", purchaseStatusDTO);
        if (purchaseStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchaseStatusDTO result = purchaseStatusService.save(purchaseStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, purchaseStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /purchase-statuses : get all the purchaseStatuses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of purchaseStatuses in body
     */
    @GetMapping("/purchase-statuses")
    public ResponseEntity<List<PurchaseStatusDTO>> getAllPurchaseStatuses(Pageable pageable) {
        log.info("REST request to get a page of PurchaseStatuses");
        Page<PurchaseStatusDTO> page = purchaseStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/purchase-statuses");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/purchase-statuses/searchpurchasestatus/{status}")
    public ResponseEntity<List<PurchaseStatusDTO>> searchCommand(@PathVariable String status, Pageable pageable) {
        log.info("REST request to get a page of PurchaseStatuses status : {}" + status);
        Page<PurchaseStatusDTO> page = purchaseStatusService.searchCommand(status, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/purchase-statuses");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /purchase-statuses/:id : get the "id" purchaseStatus.
     *
     * @param id the id of the purchaseStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the purchaseStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/purchase-statuses/{id}")
    public ResponseEntity<PurchaseStatusDTO> getPurchaseStatus(@PathVariable Long id) {
        log.info("REST request to get PurchaseStatus : {}", id);
        Optional<PurchaseStatusDTO> purchaseStatusDTO = purchaseStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purchaseStatusDTO);
    }

    @PostMapping("/purchase-statuses/currentplan/{name}")
    public ResponseEntity<PurchaseStatusDTO> getCurrentPurchaseStatus(@PathVariable String name) {
        log.info("REST request to getCurrentPurchaseStatus : {}", name);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Date", new Date().toInstant().toString());
        try {
            UUID uuid = UUID.fromString(name);
            Optional<PurchaseStatusDTO> purchaseStatusDTO = purchaseStatusService.findLastPlanOfCreatorByClassUUid(name);
            return ResponseUtil.wrapOrNotFound(purchaseStatusDTO, headers);

        } catch (IllegalArgumentException exception) {
            Optional<PurchaseStatusDTO> purchaseStatusDTO = purchaseStatusService.findLastPlanOfCreatorByClassName(name);
            return ResponseUtil.wrapOrNotFound(purchaseStatusDTO, headers);
        }
    }


    @GetMapping("/purchase-statuses/currentuserplan")
    public ResponseEntity<PurchaseStatusDTO> getCurrentUserPurchaseStatus() {
        NwmsUser user = UserUtil.getCurrentUser();
        Optional<PurchaseStatusDTO> purchaseStatusDTO = purchaseStatusService.findCurrentUserPlan(user.getId());
        return ResponseUtil.wrapOrNotFound(purchaseStatusDTO);
    }

    @GetMapping("/purchase-statuses/addfreeplantouser")
    public ResponseEntity<PurchaseStatusDTO> addFreePlanToUser() {
        log.info("going to addFreePlanToUser for current user : {}", UserUtil.getCurrentUser());
        PurchaseStatusDTO purchaseStatusDTO = purchaseStatusService.setFreePlanToUser();
        return ResponseUtil.wrapOrNotFound(Optional.of(purchaseStatusDTO));
    }

    @PostMapping("/purchase-statuses/nopayementinfo")
    public ResponseEntity<PurchaseStatusDTO> setNoPaymentInfo(@Valid @RequestBody PurchaseStatusDTO purchaseStatusDTO) {
        log.info("going to setNoPaymentInfo : {}", purchaseStatusDTO);
        Integer finalPrice = purchaseStatusService.getFinalPrice(purchaseStatusDTO);
        String username = UserUtil.getCurrentUser().getUsername();
        Long purchaselog = (Long) applicationDataStorage.get(username + "_purchaselog");
        Optional<PurchaseLogDTO> log = purchaseLogService.findOne(purchaselog);


        try {
            SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String timestampString = timestampFormat.format(new Date());

            Date startDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(timestampString);
            ZoneId z = ZoneId.of(Constants.SYSTEM_TIME_ZONE);
            ZonedDateTime zdtStart = startDate.toInstant().atZone(z);
            log.get().setPurchaseStartTime(startDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }


        Date finishDate = new Date();
        ZoneId z = ZoneId.of(Constants.SYSTEM_TIME_ZONE);
        ZonedDateTime zdtFinish = finishDate.toInstant().atZone(z);
        log.get().setPurchaseFinishTime(finishDate);

        PurchaseStatusDTO purchase = purchaseStatusService.insertPurchaseStatus(log.get());
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(purchase));
    }


    @GetMapping("/purchase-statuses/confirm/{refId}")
    public String getRefId(@PathVariable String refId) {
        log.info("REST request to confirm Purchase : {}", refId);
        JSONParser parser = new JSONParser();
        String varsendingData;
        String username = UserUtil.getCurrentUser().getUsername();
        Optional<PurchaseLogDTO> log = purchaseLogService.findOne((Long) applicationDataStorage.get(username + "_purchaselog"));
        if (refId.equals("empty")) {

            log.get().setTransactionReferenceID("empty");
            String oldInfo = (String) applicationDataStorage.get(username + "_varsendingData");
            JSONObject oldInfoToJson = null;
            try {
                oldInfoToJson = (JSONObject) parser.parse(String.valueOf(oldInfo));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            varsendingData = "{ \"InvoiceNumber\": \"" + oldInfoToJson.get("InvoiceNumber") + "\", \"InvoiceDate\":    " +
                "\"" + oldInfoToJson.get("InvoiceDate") + "\",\"TerminalCode\": \"1723300\", \"MerchantCode\": \"4537157\"" + "}";
        } else {
            log.get().setTransactionReferenceID(refId);
            varsendingData = "{ \"TransactionReferenceID\": \"" + refId + "\"}";
        }

        this.log.info("varsendingData : {}", varsendingData);

        String url = nwmsConfig.getPassargadIpgCheckTransactionResultAddress();
        HttpClient client = new DefaultHttpClient();

        HttpPost post = new HttpPost(url);

        post.setHeader("Content-type", "application/json; charset=utf-8");
        post.setHeader("Sign", getEncodedDataByPrivate(varsendingData));

        StringEntity entity = null;
        try {
            entity = new StringEntity(varsendingData);
            post.setEntity(entity);
            HttpResponse response = client.execute(post);

            BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

            StringBuffer reqResult = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                reqResult.append(line);
            }

            this.log.info("CheckTransactionResult result : {}", String.valueOf(reqResult));

            JSONObject resultToJson = (JSONObject) parser.parse(String.valueOf(reqResult));
            Boolean status = (Boolean) resultToJson.get("IsSuccess");
            log.get().setRedirectToPort(status);
            if (status) {
                String oldInfo = (String) applicationDataStorage.get(username + "_varsendingData");
                JSONObject oldInfoToJson = (JSONObject) parser.parse(String.valueOf(oldInfo));

                SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String timestampString = timestampFormat.format(new Date());

                String valToVerify = "{ \"InvoiceNumber\": \"" + oldInfoToJson.get("InvoiceNumber") + "\", \"InvoiceDate\":    " +
                    "\"" + oldInfoToJson.get("InvoiceDate") + "\",\"TerminalCode\": \"1723300\", \"MerchantCode\": \"4537157\", \"Amount\":\"" + oldInfoToJson.get("Amount") + "\"" +
                    ", \"Timestamp\":\"" + timestampString + "\"}";

                //Set Purchase Status Record In Database Befor Verify
                boolean afterVerify = verifyPayment(valToVerify);
                log.get().setVerify(afterVerify);
                Date finishDate = new Date();
                log.get().setPurchaseFinishTime(finishDate);
                purchaseLogService.save(log.get());

                if (afterVerify) {
                    purchaseStatusService.insertPurchaseStatus(log.get());
                    return "{\"status\":\"Success\"" + "}";
                } else {
                    return "{\"status\":\"Error\"" + "}";
                }

            } else {
                return "{\"status\":\"Error\"" + "}";
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return "{\"status\":\"IntervalError\"" + "}";
        }
    }

    public boolean verifyPayment(String data) {
        log.info("going to verifyPayment for : {}", data);
        String url = nwmsConfig.getPassargadIpgVerifyPaymentAddress();
        HttpClient client = new DefaultHttpClient();

        HttpPost post = new HttpPost(url);

        post.setHeader("Content-type", "application/json; charset=utf-8");
        post.setHeader("Sign", getEncodedDataByPrivate(data));

        StringEntity entity = null;

        try {
            entity = new StringEntity(data);
            post.setEntity(entity);
            HttpResponse response = client.execute(post);

            BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

            StringBuffer reqResult = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                reqResult.append(line);
            }
            log.info("verifyPayment result : {}", String.valueOf(reqResult));
            JSONParser parser = new JSONParser();
            JSONObject resultToJson = (JSONObject) parser.parse(String.valueOf(reqResult));
            Boolean status = (Boolean) resultToJson.get("IsSuccess");
            return status;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * DELETE  /purchase-statuses/:id : delete the "id" purchaseStatus.
     *
     * @param id the id of the purchaseStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/purchase-statuses/{id}")
    public ResponseEntity<Void> deletePurchaseStatus(@PathVariable Long id) {
        log.info("REST request to delete PurchaseStatus : {}", id);
        purchaseStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
