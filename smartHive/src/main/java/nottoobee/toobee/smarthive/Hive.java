package nottoobee.toobee.smarthive;

import java.util.Date;

/*
    N.B.: Instances of both of these classes can be passed directly to Firebase's setValue() method,
    since they use the same field names as the database.

    e.g.
        ref.push().setValue(new Hive("Hive 1", "gps coords"));

    They can also be read from the database straight into these objects.
*/

public class Hive {
    private String name;
    private String location;
    private Date date_created;
    private Data[] data;

    public Hive() {
    }

    public Hive(String name, String location) {
        this.name = name;
        this.location = location;
        this.date_created = new Date();
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Date getDate_created() {
        return date_created;
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

    public Data() {
    }

    public Data(Date date, int humidity, int temperature, int population, int weight) {
        this.date = date;
        this.humidity = humidity;
        this.temperature = temperature;
        this.population = population;
        this.weight = weight;
    }

    public Date getDate() {
        return date;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getPopulation() {
        return population;
    }

    public int getWeight() {
        return weight;
    }
}
