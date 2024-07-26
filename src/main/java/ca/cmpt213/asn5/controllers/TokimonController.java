package ca.cmpt213.asn5.controllers;

import ca.cmpt213.asn5.models.Tokimon;
import ca.cmpt213.asn5.models.TokimonList;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class TokimonController {
    private TokimonList tokimonList = new TokimonList();

    @GetMapping("/tokimon")
    public TokimonList getTokimonList() {
        return tokimonList;
    }

    @PostMapping("/tokimon")
    public Tokimon addTokimon(@RequestBody Tokimon newTokimon, HttpServletResponse response){
        tokimonList.addTokimon(newTokimon);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return newTokimon;
    }

    @DeleteMapping("/tokimon/{tid}")
    public Tokimon deleteTokimon(@PathVariable long tid, HttpServletResponse response){
        tokimonList.deleteTokimon(tid);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        return tokimonList.deleteTokimon(tid);
    }

    //This function is not going to work correctly until we move tid to TokimonList - waiting for Bobby to update github
    @PutMapping("/tokimon/{tid}")
    public Tokimon updateTokimon(@PathVariable long tid, @RequestBody Tokimon newTokimon , HttpServletResponse response){
        //TODO: edit the json file with newTokimon
        tokimonList.editTokimon(tid, newTokimon);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        return newTokimon;
    }


}
