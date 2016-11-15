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