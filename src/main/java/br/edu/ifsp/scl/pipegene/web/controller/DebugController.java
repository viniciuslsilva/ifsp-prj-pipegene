package br.edu.ifsp.scl.pipegene.web.controller;

import br.edu.ifsp.scl.pipegene.external.storage.localstorage.LocalStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import br.edu.ifsp.scl.pipegene.external.persistence.*;

@RestController
public class DebugController {

    @GetMapping("/debug")
    public ResponseEntity<?> getState() {
        Map<String, Object> map = new HashMap<>();
        map.put("projects", FakeDatabase.PROJECTS);
        map.put("providers", FakeDatabase.PROVIDERS);
        map.put("execution_status", FakeDatabase.EXECUTION_STATUS_MAP);
        map.put("file_names", LocalStorageService.FILES);

        return ResponseEntity.ok(map);
    }
}
