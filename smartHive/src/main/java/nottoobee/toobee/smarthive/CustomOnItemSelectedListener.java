package nottoobee.toobee.smarthive;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by paulwestman on 2016-12-11.
 */

public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        //Toast.makeText(parent.getContext(), "On Item Select : \n" + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.unitSpin)
        {
            Toast.makeText(parent.getContext(), "Units changed to " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
        }
        else if(spinner.getId() == R.id.timerSpin)
        {
            Toast.makeText(parent.getContext(), "Timer set to " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

}