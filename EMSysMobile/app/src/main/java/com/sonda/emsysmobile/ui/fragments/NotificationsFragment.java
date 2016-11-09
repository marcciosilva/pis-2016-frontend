package com.sonda.emsysmobile.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.managers.NotificationsManager;
import com.sonda.emsysmobile.notifications.Notification;
import com.sonda.emsysmobile.ui.views.adapters.NotificationAdapter;

import java.util.ArrayList;


public class NotificationsFragment extends Fragment {

    private static final String NOTIFICATION_RECEIVED = "notification_received";

    private ArrayList<Notification> mNotifications;
    private RecyclerView mRecyclerView;
    private OnFragmentInteractionListener mListener;
    private TextView mEmptyView;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.notifications_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        mEmptyView = (TextView) view.findViewById(R.id.empty_view);

        getNotifications();

        return view;
    }

    @Override
    public final void onResume() {
        super.onResume();

        //We wants than Broadcast Receiver be registered when the fragment is active
        LocalBroadcastManager.getInstance(this.getActivity())
                .registerReceiver(broadcastReceiverNotifications,
                        new IntentFilter(NOTIFICATION_RECEIVED));
    }

    @Override
    public final void onPause() {
        super.onPause();

        //We should unregister Broadcast Reciever when te fragment is paused
        LocalBroadcastManager.getInstance(this.getActivity())
                .unregisterReceiver(broadcastReceiverNotifications);
    }

    private void getNotifications() {
        NotificationsManager notificationsManager =
                NotificationsManager.getInstance(this.getActivity());
        mNotifications = notificationsManager.getNotifications();
        loadData();
        manageEmptyView();
        markNotificationsAsRead();
    }

    private void loadData() {
        NotificationAdapter notificationAdapter = (NotificationAdapter) mRecyclerView.getAdapter();
        if (notificationAdapter == null) {
            notificationAdapter = new NotificationAdapter(this.getActivity(), mListener);
            mRecyclerView.setAdapter(notificationAdapter);
        }
        notificationAdapter.setNotifications(mNotifications);
        notificationAdapter.notifyDataSetChanged();
    }

    private void markNotificationsAsRead() {
        for (Notification notification: mNotifications) {
            notification.setRead(true);
        }
    }

    private void manageEmptyView() {
        if (mNotifications.isEmpty()) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onNotificationSelected(int eventId);
    }

    private BroadcastReceiver broadcastReceiverNotifications = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getNotifications();
        }
    };
}
