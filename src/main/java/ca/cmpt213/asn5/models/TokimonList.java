package ca.cmpt213.asn5.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods used in the TokimonController to get, add and delete tokimons
 * from the list and to update the json file.
 */

public class TokimonList {
    private List<Tokimon> tokimons = new ArrayList<>();
    String filePath = "./jsonFiles/tokimons.json";

    /**
     *This function gets all tokimons from the json file and returns a tokimons list
     * @return returns the tokimon list
     */
    public List<Tokimon> getTokimons() {
        getTokimonsFromJsonFile();
        return tokimons;
    }

    /**
     * This function provides the maximum tokimon id of the tokimons in json file.
     * @return maximum tokimon id
     */
    public long getMaxTid(){
        getTokimonsFromJsonFile();
        long maxTid = 0;
        if(tokimons!=null && tokimons.size() > 0){
            for(Tokimon t : tokimons){
                if(t.getTid() > maxTid){
                    maxTid = t.getTid();
                }
            }
        }
        return maxTid;
    }


    /**
     * This function gets the tokimon from the json file based on the corresponding id and returns it
     * @param tid tokimon id
     * @return returns the tokimon
     */
    public Tokimon getTokimonWithTid(long tid) {
        getTokimonsFromJsonFile();
        for (Tokimon tokimon : tokimons) {
            if (tokimon.getTid() == tid) {
                return tokimon;
            }
        }
        return null;
    }


    /**
     * This function adds new a tokimon by taking in a tokimon as a parameter. It adds it to the list
     * by first getting all the existing tokimon from the json file. After adding the new
     * tokimon this functions updates the json file
     * @param tokimon the new tokimon created
     */
    public void addTokimon(Tokimon tokimon) {

        // Initialise tokimons from json file if any
        getTokimonsFromJsonFile();

        // Add new Tokimon to the list
        tokimons.add(tokimon);

        // Write updated list of tokimons to json file
        writeTokimonsInJsonFile();
    }


    /**
     * this functions deletes the tokimon from the list and updates the json file
     * @param tid the tokimon id
     */
    public void deleteTokimon(long tid) {

        // Initialise tokimons from json file
        getTokimonsFromJsonFile();

        // delete tokimon with given tid
        for (int i=0; i<tokimons.size(); i++) {
            if(tokimons.get(i).getTid() == tid) {
                Tokimon tokimon = tokimons.get(i);
                tokimons.remove(tokimon);
                break;
            }
        }

        // Write updated list of tokimons to json file
        writeTokimonsInJsonFile();
    }


    /**
     * This function edit the tokimon with a given tid and update the json file with new tokimons
     * @param tid   the tokimon id
     * @param newTokimon    The new tokimon replacing the old tokimon
     */
    public void editTokimon(long tid, Tokimon newTokimon) {

        //Reads tokimons from json file and store them in class variable
        getTokimonsFromJsonFile();

        //Edit the tokimon with given tid
        for (int i=0; i<tokimons.size(); i++) {
            if(tokimons.get(i).getTid() == tid) {
                tokimons.set(i, newTokimon);
                break;
            }
        }

        //Write tokimons on json file from class variable
        writeTokimonsInJsonFile();

    }

    /**
     * This function reads tokimons from json file and store them in a class variable tokimons.
     */
    private void getTokimonsFromJsonFile() {
        try(FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            Type tokimonListType = new TypeToken<ArrayList<Tokimon>>(){}.getType();
            tokimons = gson.fromJson(reader, tokimonListType);
            if(tokimons == null){
                tokimons = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function write tokimons on the json file.
     */
    public void writeTokimonsInJsonFile() {
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(tokimons, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
