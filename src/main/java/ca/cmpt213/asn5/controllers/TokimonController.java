package ca.cmpt213.asn5.controllers;

import ca.cmpt213.asn5.models.Tokimon;
import ca.cmpt213.asn5.models.TokimonList;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This is a controller class responsible to receive and complete the client's requests.
 */
@RestController
public class TokimonController {

    //Class variables
    private TokimonList tokimonList;
    private AtomicLong nextTid;

    /**
     * This function initialises class variables as soon as server starts.
     */
    @PostConstruct
    public void init() {
        tokimonList = new TokimonList();
        nextTid = new AtomicLong(tokimonList.getMaxTid()+1);
    }

    /**
     * This function is responsible for providing all the tokimons from json file.
     * @return a list of all the tokimons
     */
    @GetMapping("/tokimon/all")
    public List<Tokimon> getTokimonList() {
        return tokimonList.getTokimons();
    }

    /**
     * This function is responsible for providing a tokimon with its tid from json file.
     * @param tid Tokimon id
     * @return a tokimon with the given tid
     */
    @GetMapping("/tokimon/{tid}")
    public Tokimon getTokimon(@PathVariable long tid) {
        return tokimonList.getTokimonWithTid(tid);
    }

    /**
     * This function adds a new tokimon to json file.
     * @param newTokimon tokimon provided by client.
     * @param response response code class object.
     * @return
     */
    @PostMapping("/tokimon/add")
    public Tokimon addTokimon(@RequestBody Tokimon newTokimon, HttpServletResponse response){
        newTokimon.setTid(nextTid.getAndIncrement());
        tokimonList.addTokimon(newTokimon);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return newTokimon;      // TODO: Will make this function void once testing is done.
    }

    /**
     * This function deletes a tokimon from json file.
     * @param tid Tokimon id
     * @param response response code class object.
     */
    @DeleteMapping("/tokimon/{tid}")
    public void deleteTokimon(@PathVariable long tid, HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        tokimonList.deleteTokimon(tid);
    }

    /**
     * This function updates existing tokimon on the json file.
     * @param tid Tokimon id
     * @param newTokimon existing tokimon will be changed to this tokimon.
     * @param response response code class object.
     * @return
     */
    @PutMapping("/tokimon/edit/{tid}")
    public Tokimon updateTokimon(@PathVariable long tid, @RequestBody Tokimon newTokimon , HttpServletResponse response){
        newTokimon.setTid(tid);
        tokimonList.editTokimon(tid, newTokimon);
        return newTokimon;  // TODO: will make this function void once all the testing is done.
    }

}
