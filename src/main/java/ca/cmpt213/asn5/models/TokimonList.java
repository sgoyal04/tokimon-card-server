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
        // TODO: do we need this if statement???? should we read from json file anyway??
        // I think we can get rid of the if statement, sounds good.

        getTokimonsFromJsonFile();
        return tokimons;
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
        // TODO: do we really need this new variable existingTokimons??? can we use tokimons - class variable??
        // TODO: if using class variable, can we replace code to read json file with
        //  getTokimonsFromJasonFile() method call?

        // i tried using getTokimonFrom JsonFile but its not wokring, so we should just keep it like this for now

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
        //getTokimonsFromJsonFile();

        // Add new Tokimon to the list
        existingTokimons.add(tokimon);
        tokimons.add(tokimon);


        // Write updated list to the file with pretty printing
        writeTokimonsInJsonFile();
    }


    /**
     * this functions deletes the tokimon from the list and updates the json file
     * @param tid the tokimon id
     */
    public void deleteTokimon(long tid) {
        getTokimonsFromJsonFile();
        for (int i=0; i<tokimons.size(); i++) {
            if(tokimons.get(i).getTid() == tid) {
                Tokimon tokimon = tokimons.get(i);
                tokimons.remove(tokimon);
                writeTokimonsInJsonFile();
            }
        }
    }


    /**
     * This function edit the tokimon with a given tid and update the json file with new tokimons
     * @param tid   the tokimon id
     * @param newTokimon    The new tokimon replacing the old tokimon
     */
    public void editTokimon(long tid, Tokimon newTokimon) {

        getTokimonsFromJsonFile();              //Reads tokimons from json file and store them in class variable
        for (int i=0; i<tokimons.size(); i++) {
            if(tokimons.get(i).getTid() == tid) {
                tokimons.set(i, newTokimon);
                writeTokimonsInJsonFile();      //Write tokimons on json file from class variable
            }
        }
    }

    /**
     * This function reads tokimons from json file and store them in a class variable tokimons.
     */
    private void getTokimonsFromJsonFile() {
        try(FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            Type tokimonListType = new TypeToken<ArrayList<Tokimon>>(){}.getType();
            tokimons = gson.fromJson(reader, tokimonListType);
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
