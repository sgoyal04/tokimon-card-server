package ca.cmpt213.asn5.controllers;

import ca.cmpt213.asn5.models.Tokimon;
import ca.cmpt213.asn5.models.TokimonList;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TokimonController {
    private TokimonList tokimonList = new TokimonList();

    @GetMapping("/tokimon")
    public List<Tokimon> getTokimonList() {
        return tokimonList.getTokimons();
    }

    @PostMapping("/tokimon")
    public Tokimon addTokimon(@RequestBody Tokimon newTokimon, HttpServletResponse response){
        tokimonList.addTokimon(newTokimon);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return newTokimon;
    }

    //This function is returning null everytime - Might be the reference issue in TokimonList -  debugging required
    @DeleteMapping("/tokimon/{tid}")
    public Tokimon deleteTokimon(@PathVariable long tid, HttpServletResponse response){
        tokimonList.deleteTokimon(tid);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return tokimonList.deleteTokimon(tid);
    }


    @PutMapping("/tokimon/{tid}")
    public Tokimon updateTokimon(@PathVariable long tid, @RequestBody Tokimon newTokimon , HttpServletResponse response){
        //TODO: edit the json file with newTokimon
        tokimonList.editTokimon(tid, newTokimon);
        newTokimon.setTid(tid);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return newTokimon;
    }

}
