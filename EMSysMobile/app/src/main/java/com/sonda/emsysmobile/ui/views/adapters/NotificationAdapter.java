package com.sonda.emsysmobile.ui.views.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.notifications.Notification;
import com.sonda.emsysmobile.ui.fragments.NotificationsFragment.OnFragmentInteractionListener;
import com.sonda.emsysmobile.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sasainz on 11/8/16.
 */

public class NotificationAdapter
        extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private final List<Notification> notifications;
    private final Context context;
    private OnFragmentInteractionListener listener;

    public NotificationAdapter(Context context, OnFragmentInteractionListener listener) {
        this.notifications = new ArrayList<>();
        this.context = context;
        this.listener = listener;
    }

    public final void setNotifications(List<Notification> newNotifications) {
        notifications.clear();
        notifications.addAll(newNotifications);
    }

    @Override
    public final ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(final ViewHolder holder, int position) {
        Notification notification = notifications.get(position);

        holder.setItem(notification);
        holder.getDate().setText(DateUtils.timeAgoString(context, notification.getDate()));
        holder.getTitle().setText(notification.getTitle());
        holder.getDescription().setText(notification.getDescription());

        if (notification.isRead()) {
            holder.getView()
                    .setBackgroundColor(ContextCompat.getColor(context, R.color.gray_xxxlight));
            holder.getTitle().setTypeface(null, Typeface.NORMAL);
        } else {
            holder.getView()
                    .setBackgroundColor(ContextCompat.getColor(context, R.color.gray_xxlight));
            holder.getTitle().setTypeface(null, Typeface.BOLD);
        }

        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onNotificationSelected(holder.getItem());
                }
            }
        });
    }

    @Override
    public final int getItemCount() {
        return notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final View view;
        private final TextView date;
        private final TextView title;
        private final TextView description;
        private Notification item;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            date = (TextView) itemView.findViewById(R.id.label_date);
            title = (TextView) itemView.findViewById(R.id.label_title);
            description = (TextView) itemView.findViewById(R.id.label_description);
        }

        public final View getView() {
            return view;
        }

        public final TextView getDate() {
            return date;
        }

        public final TextView getTitle() {
            return title;
        }

        public final Notification getItem() {
            return item;
        }

        public final void setItem(Notification item) {
            this.item = item;
        }

        public final TextView getDescription() {
            return description;
        }
    }
}
