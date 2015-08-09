package com.traylz.traylzmix;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class UploaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setTitle("Upload Trail Settings");
        setContentView(R.layout.activity_uploader);
    }

    public void uploadMap(View view) {
        EditText naming = (EditText) findViewById(R.id.nameEdit);
        RadioButton medium = (RadioButton) findViewById(R.id.mediumRadio);
        RadioButton hard = (RadioButton) findViewById(R.id.hardRadio);
        RadioButton walk = (RadioButton) findViewById(R.id.walkRadio);
        RadioButton bike = (RadioButton) findViewById(R.id.bikeRadio);
        RadioButton multi = (RadioButton) findViewById(R.id.multiRadio);
        String type = walk.isChecked() ? "walk" : (bike.isChecked() ? "bike" : (multi.isChecked() ? "multiuse" : "horse"));
        String name = naming.getText().toString();
        String difficulty = medium.isChecked() ? "medium" : (hard.isChecked() ? "hard" : "easy");
        boolean success = PostUploader.sendPost(type, name, difficulty, CoordArray.testArray.getCoordinates());
        if (success) {
            Intent intent = new Intent(this, MainActivity.class);
            toastShit("Upload complete!", false);
            startActivity(intent);
        } else
            toastShit("Upload failed! Try again later.", false);
    }

    public void cancelUpload(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        toastShit("Upload cancelled.", false);
        startActivity(intent);
    }

    public void toastShit(String msg, boolean length) {
        Context context = getApplicationContext();
        Toast.makeText(context, msg, length ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_uploader, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
