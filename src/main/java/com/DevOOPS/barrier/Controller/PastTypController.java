package com.DevOOPS.barrier.Controller;

import com.DevOOPS.barrier.Entity.PastTypEntity;
import com.DevOOPS.barrier.Repository.PastTypRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/past")
public class PastTypController {
    @Autowired
    private PastTypRepository pastTypRepository;

    @GetMapping("/pastTyp")
    public List <PastTypEntity> getAllPastTyps(){
        return pastTypRepository.findAll();
    }
    @GetMapping("/pastTyp/{typName}")
    public ResponseEntity<List<PastTypEntity>> getPastTypEntitiesByTypname(@PathVariable(value="typName") String TypName)
            throws ResponseStatusException
    {
        List<PastTypEntity> pastTypEntities = pastTypRepository.findByTypName(TypName);
        if (pastTypEntities.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Typhoon not found for this name :: " + TypName);
        }
        return ResponseEntity.ok().body(pastTypEntities);
    }


    @PostMapping("/PastTyp")
    public List<PastTypEntity> createAllPastTyps() {
        return pastTypRepository.findAll();
    }

}
