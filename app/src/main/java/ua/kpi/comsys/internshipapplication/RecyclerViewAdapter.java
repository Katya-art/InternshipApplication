package ua.kpi.comsys.internshipapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.tkeunebr.gravatar.Gravatar;
import ua.kpi.comsys.internshipapplication.models.Contact;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private final List<Contact> contacts;
    private final Context context;
    private final int mAvatarImageViewPixelSize;

    public RecyclerViewAdapter(Context context, List<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
        mAvatarImageViewPixelSize = context.getResources().getDimensionPixelSize(R.dimen.avatar_image_view_size);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_in_grid, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String gravatarUrl = Gravatar.init().with(contacts.get(position).getEmail()).force404().size(mAvatarImageViewPixelSize).build();
        Picasso.with(context)
                .load(gravatarUrl)
                .placeholder(R.drawable.ic_contact_picture)
                .error(R.drawable.ic_contact_picture)
                .into(holder.imageView);
        if (contacts.get(position).getStatus().equals("Online")) {
            holder.textView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.contact_image_in_grid);
            textView = itemView.findViewById(R.id.badge_notification_2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Contact contact = (Contact) contacts.get(getLayoutPosition());
            Intent intent = new Intent(context, ContactInfo.class);
            intent.putExtra("main info", contact.getMainInfo());
            intent.putExtra("email", contact.getEmail());
            context.startActivity(intent);
        }
    }
}
