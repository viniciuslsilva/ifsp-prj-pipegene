package br.edu.ifsp.scl.pipegene.web.controller;

import br.edu.ifsp.scl.pipegene.external.persistence.entities.ProjectEntity;
import br.edu.ifsp.scl.pipegene.external.storage.localstorage.LocalStorageService;
import br.edu.ifsp.scl.pipegene.usecases.account.ApplicationUser;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import br.edu.ifsp.scl.pipegene.external.persistence.*;

@RestController
public class DebugController {

    private final FakeDatabase fakeDatabase;
    private final JdbcTemplate jdbcTemplate;

    public DebugController(FakeDatabase fakeDatabase, JdbcTemplate jdbcTemplate) {
        this.fakeDatabase = fakeDatabase;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/debug")
    public ResponseEntity<?> getState() {


        final var sqlRowSet = jdbcTemplate.queryForRowSet("select * from pipegine_platform.application_user");


        var response = ImmutableMap.<UUID, String>builder();
        while (sqlRowSet.next()) {
            final var id = UUID.fromString(sqlRowSet.getString("id"));
            final var username = sqlRowSet.getString("username");
            response.put(id, username);
        }

        return ResponseEntity.ok(
                response.build()
        );

//        Map<String, Object> map = new HashMap<>();
//        map.put("projects", fakeDatabase.PROJECTS);
//        map.put("providers", fakeDatabase.PROVIDERS);
//        map.put("executions", fakeDatabase.EXECUTION_STATUS_MAP);
//        map.put("file_names", LocalStorageService.FILES);
//
//        return ResponseEntity.ok(map);
    }


}
