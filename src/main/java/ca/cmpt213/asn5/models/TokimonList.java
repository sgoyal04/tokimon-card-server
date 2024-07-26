package ca.cmpt213.asn5.models;

import com.google.gson.Gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class TokimonList {
    private List<Tokimon> tokimons ;
    String filePath = "./jsonFiles/tokimons.json";
    AtomicInteger counter;

    public TokimonList() {
        tokimons = new ArrayList<>();
        counter = new AtomicInteger(1);
    }

    public List<Tokimon> getTokimons() {
        // TODO: read from the json file //
        if (tokimons.isEmpty()) {

            try(FileReader reader = new FileReader(filePath)) {
                Gson gson = new Gson();
                Type tokimonListType = new TypeToken<ArrayList<Tokimon>>(){}.getType();
                tokimons = gson.fromJson(reader, tokimonListType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tokimons;
    }

    public void addTokimon(Tokimon tokimon) {
        // TODO: add tokimon to json file //

        tokimons.add(tokimon);
        tokimon.setTid(counter.getAndIncrement());
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new Gson();
            gson.toJson(tokimons, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Tokimon deleteTokimon(long tid) {
        // TODO: delete tokimon tid from json file //
        for (int i=0; i<tokimons.size(); i++) {
            if(tokimons.get(i).getTid() == tid) {
                Tokimon tokimon = tokimons.get(i);
                tokimons.remove(tokimon);
                try (FileWriter writer = new FileWriter(filePath)) {
                    Gson gson = new Gson();
                    gson.toJson(tokimons, writer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return tokimon;
            }
        }
        return null;
    }

    public void editTokimon(long tid, Tokimon newTokimon) {
        for (int i=0; i<tokimons.size(); i++) {
            if(tokimons.get(i).getTid() == tid) {
                tokimons.set(i, newTokimon);
                newTokimon.setTid(tid);
                try (FileWriter writer = new FileWriter(filePath)) {
                    Gson gson = new Gson();
                    gson.toJson(tokimons, writer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
