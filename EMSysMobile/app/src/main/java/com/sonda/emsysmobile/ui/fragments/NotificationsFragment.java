package com.sonda.emsysmobile.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.managers.NotificationsManager;
import com.sonda.emsysmobile.notifications.Notification;
import com.sonda.emsysmobile.ui.views.adapters.NotificationAdapter;

import java.util.ArrayList;


public class NotificationsFragment extends Fragment {

    private ArrayList<Notification> mNotifications;
    private RecyclerView mRecyclerView;
    private OnFragmentInteractionListener mListener;

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

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getNotifications();
    }

    private void getNotifications() {
        NotificationsManager notificationsManager =
                NotificationsManager.getInstance(this.getActivity());
        mNotifications = notificationsManager.getNotifications();
        NotificationAdapter notificationAdapter = (NotificationAdapter) mRecyclerView.getAdapter();
        if (notificationAdapter == null) {
            notificationAdapter = new NotificationAdapter(this.getActivity(), mListener);
        }
        notificationAdapter.setNotifications(mNotifications);
        notificationAdapter.notifyDataSetChanged();
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
}
