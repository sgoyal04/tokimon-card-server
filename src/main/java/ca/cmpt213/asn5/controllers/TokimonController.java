package ca.cmpt213.asn5.controllers;

import ca.cmpt213.asn5.models.Tokimon;
import ca.cmpt213.asn5.models.TokimonList;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TokimonController {
    private TokimonList tokimonList = new TokimonList();

    @GetMapping("/tokimon/all")
    public List<Tokimon> getTokimonList() {
        return tokimonList.getTokimons();
    }

    @GetMapping("/tokimon/{tid}")
    public Tokimon getTokimon(@PathVariable long tid) {
        return tokimonList.getTokimonWithTid(tid);
    }

    @PostMapping("/tokimon/add")
    public Tokimon addTokimon(@RequestBody Tokimon newTokimon, HttpServletResponse response){
        tokimonList.addTokimon(newTokimon);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return newTokimon;      // TODO: Will make this function void once testing is done.
    }

    @DeleteMapping("/tokimon/{tid}")
    public Tokimon deleteTokimon(@PathVariable long tid, HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        return tokimonList.deleteTokimon(tid);
    }

    @PutMapping("/tokimon/edit/{tid}")
    public Tokimon updateTokimon(@PathVariable long tid, @RequestBody Tokimon newTokimon , HttpServletResponse response){
        tokimonList.editTokimon(tid, newTokimon);
        newTokimon.setTid(tid);
        return newTokimon;  // TODO: will make this function void once all the testing is done.
    }

}
