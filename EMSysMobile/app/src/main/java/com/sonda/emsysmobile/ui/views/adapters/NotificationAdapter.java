package com.sonda.emsysmobile.ui.views.adapters;

import android.content.Context;
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

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private final List<Notification> notifications;
    private final Context context;
    private OnFragmentInteractionListener listener;

    public NotificationAdapter(Context context, OnFragmentInteractionListener listener) {
        this.notifications = new ArrayList<>();
        this.context = context;
        this.listener = listener;
    }

    public void setNotifications(List<Notification> newNotifications) {
        notifications.clear();
        notifications.addAll(newNotifications);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notification notification = notifications.get(position);

        holder.setItem(notification);
        holder.getDate().setText(DateUtils.dateToString(notification.getDate()));
        holder.getTitle().setText(notification.getTitle());
        holder.getDescription().setText(notification.getDescription());
    }

    @Override
    public int getItemCount() {
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

        public View getView() {
            return view;
        }

        public TextView getDate() {
            return date;
        }

        public TextView getTitle() {
            return title;
        }

        public Notification getItem() {
            return item;
        }

        public TextView getDescription() {
            return description;
        }

        public void setItem(Notification item) {
            this.item = item;
        }
    }
}
