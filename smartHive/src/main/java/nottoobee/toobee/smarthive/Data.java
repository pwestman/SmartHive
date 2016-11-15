package nottoobee.toobee.smarthive;

import java.util.Date;


public class Data {
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
