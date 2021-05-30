package ua.kpi.comsys.internshipapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.tkeunebr.gravatar.Gravatar;
import ua.kpi.comsys.internshipapplication.models.Contact;

public class ContactArrayAdapter extends ArrayAdapter {
    private final List<Contact> contacts;
    private final Context context;
    private final int mAvatarImageViewPixelSize;

    public ContactArrayAdapter(Context context, int resource, List<Contact> contacts) {
        super(context, resource, contacts);
        this.context = context;
        this.contacts = contacts;
        mAvatarImageViewPixelSize = context.getResources().getDimensionPixelSize(R.dimen.avatar_image_view_size);
    }

    @NonNull
    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        if (contentView == null) {
            contentView = LayoutInflater.from(context).inflate(R.layout.contact_in_list, null);
        }
        ((TextView)contentView.findViewById(R.id.contact_name)).setText(contacts.get(position).getName());
        String gravatarUrl = Gravatar.init().with(contacts.get(position).getEmail()).force404().size(mAvatarImageViewPixelSize).build();
        Picasso.with(context)
                .load(gravatarUrl)
                .placeholder(R.drawable.ic_contact_picture)
                .error(R.drawable.ic_contact_picture)
                .into((ImageView) contentView.findViewById(R.id.contact_image));
        if (contacts.get(position).getStatus().equals("Online")) {
            contentView.findViewById(R.id.badge_notification_1).setVisibility(View.VISIBLE);
        }
        return contentView;
    }
}
