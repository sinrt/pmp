package com.pmp.nwms.web.rest;

import com.pmp.nwms.service.ClassroomBlockedClientService;
import com.pmp.nwms.service.listmodel.ClassroomBlockedClientListModel;
import com.pmp.nwms.web.rest.vm.ClassroomBlockClientVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClassroomBlockedClientResource {

    @Autowired
    private ClassroomBlockedClientService classroomBlockedClientService;

    @PostMapping("/classroom/block/clients")
    public ResponseEntity<Integer> blockClients(@Valid @RequestBody ClassroomBlockClientVM vm){
        int count = classroomBlockedClientService.blockClients(vm);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Date", new Date().toInstant().toString());
        return ResponseEntity.ok().headers(headers).body(count);
    }

    @PostMapping("/classroom/unblock/clients")
    public ResponseEntity<Integer> unblockClients(@Valid @RequestBody ClassroomBlockClientVM vm){
        int count = classroomBlockedClientService.unblockClients(vm);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Date", new Date().toInstant().toString());
        return ResponseEntity.ok().headers(headers).body(count);
    }

    @PostMapping("/classroom/list/blocked/clients/{classroomId}")
    public ResponseEntity<List<ClassroomBlockedClientListModel>> unblockClients(@PathVariable("classroomId") Long classroomId){
        List<ClassroomBlockedClientListModel> result = classroomBlockedClientService.getClassroomBlockedClientListModels(classroomId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Date", new Date().toInstant().toString());
        return ResponseEntity.ok().headers(headers).body(result);
    }
}
