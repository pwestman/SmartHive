package nottoobee.toobee.smarthive;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseHelper {
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    public void addHive(String name, String location) {

        DatabaseReference mUserRef = mRootRef.child("");
    }
}
