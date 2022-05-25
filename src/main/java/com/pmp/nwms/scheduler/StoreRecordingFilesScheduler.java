package com.pmp.nwms.scheduler;

import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.domain.ClassroomRecordingInfo;
import com.pmp.nwms.domain.enums.RecordingStorageStatus;
import com.pmp.nwms.repository.ClassroomRecordingInfoRepository;
import com.pmp.nwms.util.JackrabbitRepoUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class StoreRecordingFilesScheduler {
    private final Logger logger = LoggerFactory.getLogger(StoreRecordingFilesScheduler.class);

    @Autowired
    private NwmsConfig nwmsConfig;
    @Autowired
    private ClassroomRecordingInfoRepository classroomRecordingInfoRepository;
    @Autowired
    private JackrabbitRepoUtil jackrabbitRepoUtil;
    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(cron = "${store.recording.files.scheduler.cron}")
    public void run() {
        if (!nwmsConfig.isStoreRecordingFilesSchedulerEnabled()) {
            logger.info("StoreRecordingFilesScheduler is disabled.");
            return;
        }

        List<ClassroomRecordingInfo> classroomRecordingInfos = classroomRecordingInfoRepository.findByDownloadBaseUrlAndStorageStatus(nwmsConfig.getRubruWsUrl(), RecordingStorageStatus.ReadyToDownload);
        if (classroomRecordingInfos != null && !classroomRecordingInfos.isEmpty()) {
            for (ClassroomRecordingInfo classroomRecordingInfo : classroomRecordingInfos) {
                logger.info("going to download file from signal server for session " + classroomRecordingInfo.getRubruSessionName() + ", recording by id " + classroomRecordingInfo.getRecordingId());
                try {
                    classroomRecordingInfo.setStorageStatus(RecordingStorageStatus.Downloading);
                    classroomRecordingInfo = classroomRecordingInfoRepository.save(classroomRecordingInfo);
                    //downlaod the file from signal server
                    String downloadUrl = "https://" + classroomRecordingInfo.getDownloadBaseUrl() + "/recordings/" + classroomRecordingInfo.getRecordingId() + "/" +
                        classroomRecordingInfo.getRecordingName() + ".mp4";
                    logger.info("for session " + classroomRecordingInfo.getRubruSessionName() + ", recording by id " + classroomRecordingInfo.getRecordingId() + " download url is " + downloadUrl);

                    BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                    credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(nwmsConfig.getSignalServerUsername(), nwmsConfig.getSignalServerPassword()));
                    CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
                    HttpGet request = new HttpGet(downloadUrl);
                    HttpResponse response = client.execute(request);

                    InputStream input = response.getEntity().getContent();
                    String uuid = UUID.randomUUID().toString().replace("-", "");
                    String subPath = "classroom/" + classroomRecordingInfo.getRubruSessionName() + "/recordings/" + classroomRecordingInfo.getRecordingId();
                    String contentType = "video/mp4";
                    logger.info("recording " + classroomRecordingInfo.getRecordingId() + " for session " + classroomRecordingInfo.getRubruSessionName() + " file size is : " + response.getEntity().getContentLength());
                    long t1 = System.currentTimeMillis();
                    try {
                        jackrabbitRepoUtil.uploadFileInfo(input, uuid, contentType, "classroom", classroomRecordingInfo.getRubruSessionName(), "recordings", classroomRecordingInfo.getRecordingId());
                    } finally {
                        long t2 = System.currentTimeMillis();
                        logger.info("storing recording by id " + classroomRecordingInfo.getRecordingId() + " for session " + classroomRecordingInfo.getRubruSessionName() + " in jackrabbit, took " + (t2 - t1) + " milliseconds");
                    }
                    classroomRecordingInfo.setContentType(contentType);
                    classroomRecordingInfo.setSavedId(uuid);
                    classroomRecordingInfo.setSubPath(subPath);
                    classroomRecordingInfo.setStorageStatus(RecordingStorageStatus.Downloaded);
                    classroomRecordingInfo.setStoreDateTime(new Date());
                    classroomRecordingInfo = classroomRecordingInfoRepository.save(classroomRecordingInfo);

                    if (nwmsConfig.isInstantDeleteRecordings()) {
                        String deleteUrl = "https://" + classroomRecordingInfo.getDownloadBaseUrl() + "/secured-api/recordings/" + classroomRecordingInfo.getRecordingId();
                        logger.info("for session " + classroomRecordingInfo.getRubruSessionName() + ", recording by id " + classroomRecordingInfo.getRecordingId() + " delete url is " + deleteUrl);
                        HttpHeaders headers = makeHttpHeaders();
                        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
                        restTemplate.exchange(deleteUrl, HttpMethod.DELETE, requestEntity, Void.class);
                        logger.info("deleting recording for session " + classroomRecordingInfo.getRubruSessionName() + " and recordingId " + classroomRecordingInfo.getRecordingId() + " don with status " + response.getStatusLine().getStatusCode());
                    }
                } catch (Exception e) {
                    logger.warn("exception in getting recording " + classroomRecordingInfo.getRecordingId() + " for session " + classroomRecordingInfo.getRubruSessionName() + " by id " + classroomRecordingInfo.getId(), e);
                    classroomRecordingInfo.setStorageStatus(RecordingStorageStatus.ReadyToDownload);
                    classroomRecordingInfo = classroomRecordingInfoRepository.save(classroomRecordingInfo);
                }
            }
        }

    }

    private HttpHeaders makeHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String auth = nwmsConfig.getSignalServerUsername().trim() + ":" + nwmsConfig.getSignalServerPassword().trim();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("authorization", authHeader);
        return headers;
    }
}
