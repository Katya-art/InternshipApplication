package ua.kpi.comsys.internshipapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import ua.kpi.comsys.internshipapplication.models.Contact;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ContactArrayAdapter contactArrayAdapter;
    private ArrayList<Contact> contacts;
    private ListView listView;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        listView = findViewById(R.id.list);
        recyclerView = findViewById(R.id.recycle_view);
        contacts = createContactList();
        button = findViewById(R.id.button_simulate_changes);
    }

    @Override
    protected void onResume() {
        super.onResume();
        contactArrayAdapter = new ContactArrayAdapter(MainActivity.this,
                R.layout.contact_in_list, contacts);
        listView.setAdapter(contactArrayAdapter);
        listView.setOnItemClickListener(this);
        Thread createRecyclerView = new Thread(() -> {
            layoutManager = new GridLayoutManager(MainActivity.this, 7);
            recyclerView.setLayoutManager(layoutManager);
            recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, contacts);
            recyclerView.setAdapter(recyclerViewAdapter);
        });
        createRecyclerView.start();
        button.setOnClickListener(v -> {
            Random random = new Random();
            int numberOfIterations = random.nextInt(contacts.size());
            for (int i = 0; i < numberOfIterations; i++) {
                int operation = random.nextInt(4);
                int contact = random.nextInt(contacts.size());
                if (operation == 0) {
                    if (contacts.get(contact).getStatus().equals("Online")) {
                        contacts.get(contact).setStatus("Offline");
                    } else {
                        contacts.get(contact).setStatus("Online");
                    }
                }
                if (operation == 1) {
                    contacts.get(contact).setName("Jane Smith");
                }
                if (operation == 2) {
                    contacts.remove(contact);
                }
                if (operation == 3) {
                    Contact contact1 = new Contact("Jake Jonson", "jake.jonson@gmail.com", "Online");
                    contacts.add(contact1);
                }
            }
            contactArrayAdapter.notifyDataSetChanged();
            recyclerViewAdapter.notifyDataSetChanged();
        });
    }

    private ArrayList<Contact> createContactList() {
        ArrayList<Contact> contacts = new ArrayList<>();
        Random random = new Random();
        int numberOfContacts = random.nextInt(50);
        for (int i = 0; i < numberOfContacts; i++) {
            String status = "Offline";
            if (random.nextBoolean()) {
                status = "Online";
            }
            Contact contact = new Contact("John Doe", "john.doe@gmail.com", status);
            contacts.add(contact);
        }
        return contacts;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Contact contact = (Contact) parent.getItemAtPosition(position);
        Intent intent = new Intent(this, ContactInfo.class);
        intent.putExtra("main info", contact.getMainInfo());
        intent.putExtra("email", contact.getEmail());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Initialize menu inflater
        MenuInflater menuInflater = getMenuInflater();
        //Inflate menu
        menuInflater.inflate(R.menu.top_menu, menu);
        menu.findItem(R.id.action_change_view);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_change_view) {
            if (listView.getVisibility() == View.VISIBLE) {
                listView.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                item.setTitle(R.string.show_as_list);
            } else {
                recyclerView.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);
                item.setTitle(R.string.show_as_grid);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}