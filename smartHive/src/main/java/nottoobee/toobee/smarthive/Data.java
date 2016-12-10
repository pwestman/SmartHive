/*
    Team Name: tooBee || !tooBee
*/

package nottoobee.toobee.smarthive;

import android.os.Parcel;
import android.os.Parcelable;


public class Data implements Parcelable{
    private long date;
    private int temperature;
    private int population;
    private int weight;

    public Data() {
    }

    public Data(Parcel in) {
        date = in.readLong();
        temperature = in.readInt();
        population = in.readInt();
        weight = in.readInt();
    }

    public Data(long date, int humidity, int temperature, int population, int weight) {
        this.date = date;
        this.temperature = temperature;
        this.population = population;
        this.weight = weight;
    }

    public long getDate() {
        return date;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(date);
        dest.writeInt(temperature);
        dest.writeInt(population);
        dest.writeInt(weight);
    }

    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
