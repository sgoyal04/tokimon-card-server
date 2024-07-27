package ca.cmpt213.asn5.models;

public class Tokimon {

    private long tid;
    private String name;
    private String imagePath;
    private String type;
    private int rarityScore;

    public Tokimon(String imagePath, String name, String type, int rarityScore) {
        this.imagePath = imagePath;
        this.name = name;
        this.type = type;
        this.rarityScore = rarityScore;
    }

    public long getTid() {
        return tid;
    }
    public void setTid(long tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public int getRarityScore() {
        return rarityScore;
    }
    public void setRarityScore(int rarityScore) {
        this.rarityScore = rarityScore;
    }

}
