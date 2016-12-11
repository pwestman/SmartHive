/*
    Team Name: tooBee || !tooBee
*/

package nottoobee.toobee.smarthive;

import java.util.Date;

/*
    N.B.: Instances this class can be passed directly to Firebase's setValue() method,
    since they use the same field names as the database.

    e.g.
        ref.push().setValue(new Hive("Hive 1", "gps coords"));

    It is also possible to pull values from the database directly into a Hive object.
*/

public class Hive {
    private String name;
    private String location;
    private long date_created;
    private Data data;
    private String key;

    public Hive() {
    }

    public Hive(String name, String location) {
        this.name = name;
        this.location = location;
        this.date_created = new Date().getTime();
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public long getDate_created() {
        return date_created;
    }

    public Data getData() {
        return data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = (key);
    }
}