package nottoobee.toobee.smarthive;

import java.util.Date;

public class Hive {
    private String name;
    private String location;
    private Date dateCreated;
    private Data[] data;

    public Hive() {
    }

    public Hive(String name, String location, Date dateCreated) {
        this.name = name;
        this.location = location;
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Data[] getData() {
        return data;
    }
}

class Data {
    private Date date;
    private int humidity;
    private int temperature;
    private int population;
    private int weight;

    public Date getDate() {
        return date;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getTemperature() {
        return humidity;
    }

    public int getPopulation() {
        return population;
    }

    public int getWeight() {
        return weight;
    }
}
