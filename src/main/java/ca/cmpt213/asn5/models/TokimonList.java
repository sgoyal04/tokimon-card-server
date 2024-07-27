package ca.cmpt213.asn5.models;

import com.google.gson.Gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    public Tokimon getTokimonWithTid(long tid) {
        for(int i = 0 ; i < tokimons.size() ; i++) {
            if(tokimons.get(i).getTid() == tid) {
                return tokimons.get(i);
            }
        }
        return null;
    }

    public void addTokimon(Tokimon tokimon) {
        // TODO: add tokimon to json file //

        List<Tokimon> existingTokimons = new ArrayList<>();
        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            Type tokimonListType = new TypeToken<ArrayList<Tokimon>>() {}.getType();
            existingTokimons = gson.fromJson(reader, tokimonListType);
            if (existingTokimons == null) {
                existingTokimons = new ArrayList<>();
            }
        } catch (FileNotFoundException e) {
            // File not found, initialize an empty list
            existingTokimons = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add new Tokimon to the list
        tokimon.setTid(counter.getAndIncrement());
        existingTokimons.add(tokimon);

        // Write updated list to the file with pretty printing
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(existingTokimons, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void deleteTokimon(long tid) {
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
            }
        }
    }

    /**
     * This function edit the tokimon with a given tid and update the json file with new tokimons
     * @param tid   the tokimon id
     * @param newTokimon    The new tokimon replacing the old tokimon
     */
    public void editTokimon(long tid, Tokimon newTokimon) {
        // TODO: return something to indicate if tid is invalid.
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
