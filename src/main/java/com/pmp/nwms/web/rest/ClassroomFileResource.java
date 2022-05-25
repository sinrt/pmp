package com.pmp.nwms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.logging.IgnoreLoggingValue;
import com.pmp.nwms.service.ClassroomFileService;
import com.pmp.nwms.service.listmodel.ClassroomFileListModel;
import com.pmp.nwms.service.so.ClassroomFileSo;
import com.pmp.nwms.util.MultipartFileSender;
import com.pmp.nwms.web.rest.util.PaginationUtil;
import com.pmp.nwms.web.rest.vm.ClassroomFileVM;
import com.pmp.nwms.web.rest.vm.FileUploadVM;
import liquibase.util.file.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/classroom/file")
public class ClassroomFileResource {

    @Autowired
    private ClassroomFileService classroomFileService;

    @Autowired
    private NwmsConfig nwmsConfig;

    @PostMapping("/upload")
    @Timed
    public ResponseEntity<Long> upload(
        @RequestParam("file") MultipartFile file,
        @RequestParam("sessionId") String sessionId,
        @RequestParam("name") String name,
        @RequestParam("contentType") String contentType
    ) {
        contentType = file.getContentType();
        if (checkFileContentType(contentType, FilenameUtils.getExtension(file.getOriginalFilename()))) {
            FileUploadVM vm = new FileUploadVM();
            try {
                vm.setContent(file.getBytes());
                vm.setSessionId(sessionId);
                vm.setName(name);
                vm.setContentType(contentType);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (name.length() > 150) {
                return new ResponseEntity<>(HttpStatus.LENGTH_REQUIRED);
            }
            Long id = classroomFileService.uploadNewFile(vm);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/delete/{id}")
    @Timed
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        VIDEO_FILES.remove(id);
        classroomFileService.deleteFile(id);
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}")
    @IgnoreLoggingValue
    public void download(@PathVariable("id") Long id, HttpServletResponse response) {
        FileUploadVM vm = classroomFileService.getFileContent(id);
        try {

            response.setContentType(vm.getContentType());
            response.addHeader("Content-Disposition", "attachment; filename=" + vm.getName());
            byte[] content = vm.getContent();
            response.setContentLength(content.length);

            if (content != null && content.length > 0) {
                response.getOutputStream().write(content, 0, content.length);
            }
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);//todo handle exception
        }
    }

    @GetMapping(value = {"/stream/{id}.{postfix}"})
    @IgnoreLoggingValue
    public void downloadStreamed(@PathVariable("id") Long id, @PathVariable(value = "postfix") String postfix, HttpServletRequest request, HttpServletResponse response) {
        FileUploadVM vm;
        long t1 = System.currentTimeMillis();
        try {
            if (VIDEO_FILES.containsKey(id)) {
                vm = VIDEO_FILES.get(id);
            } else {
                vm = classroomFileService.getFileContent(id);
                VIDEO_FILES.put(id, vm);//todo add something to clean up this map
            }

            try {
                MultipartFileSender.fromVM(vm)
                    .with(request)
                    .with(response)
                    .serveResource();
                long t2 = System.currentTimeMillis();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } finally {
            System.out.println("\n\n\n");
            long t2 = System.currentTimeMillis();
            System.out.println("t2-t1 = " + (t2-t1));
            System.out.println("\n\n\n");
        }

    }

    @GetMapping("/list/userxlsx")
    @IgnoreLoggingValue
    public void xlsxRubruUsersListInstance(HttpServletResponse response) {
        try {
            Path path = new File(nwmsConfig.getRubrueXlsPath() + "rubru.xlsx").toPath();
            FileUploadVM vm = new FileUploadVM();

            vm.setContentType(Files.probeContentType(path));
            vm.setContent(Files.readAllBytes(path));
            vm.setName(path.getFileName().toString());
            byte[] content = vm.getContent();

            response.setContentType(vm.getContentType());
            response.addHeader("Content-Disposition", "attachment; filename=" + vm.getName());

            if (content != null && content.length > 0) {
                response.getOutputStream().write(content, 0, content.length);
            }
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);//todo handle exception
        }
    }

    @PostMapping("/list")
    @Timed
    public ResponseEntity<List<ClassroomFileListModel>> list(@Valid @RequestBody ClassroomFileSo so) {
        Page<ClassroomFileListModel> page = classroomFileService.findAll(so);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/classroom/file/list");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @PostMapping("/change/settings")
    @Timed
    public ResponseEntity<Boolean> changeSettings(@Valid @RequestBody ClassroomFileVM vm) {
        classroomFileService.changeStatus(vm.getId(), vm.getNewStatus());
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    private boolean checkFileContentType(String contentType, String extention) {
        if (contentType.startsWith("image") ||
            contentType.startsWith("audio") ||
            contentType.startsWith("video") ||
            contentType.startsWith("text")
        ) {
            return true;
        } else if (extention.equals("pdf")) {
            return true;
        } else if (contentType.startsWith("application")) {
            if (contentType.contains("officedocument") || contentType.contains("compressed")) {
                return true;
            } else {
                return false;
            }
        } else if (extention.equals("rar")) {
            return true;
        } else if (extention.equals("gz")) {
            return true;
        } else if (extention.equals("tar")) {
            return true;
        } else {
            return false;
        }
    }


    private static final Map<Long, FileUploadVM> VIDEO_FILES = new HashMap<>();


    @Scheduled(cron = "0 0 1 * * ?")
    public void cleanupVideosMap() {
        VIDEO_FILES.clear();
    }

}
