package ua.kpi.comsys.internshipapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import fr.tkeunebr.gravatar.Gravatar;

public class ContactInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Bundle arguments = getIntent().getExtras();
        String mainInfo = arguments.get("main info").toString();
        String email = arguments.get("email").toString();
        String gravatarUrl = Gravatar.init().with(email).force404().build();
        Picasso.with(this)
                .load(gravatarUrl)
                .placeholder(R.drawable.ic_contact_picture)
                .error(R.drawable.ic_contact_picture)
                .into((ImageView) findViewById(R.id.avatar));
        ((TextView) findViewById(R.id.info)).setText(mainInfo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}