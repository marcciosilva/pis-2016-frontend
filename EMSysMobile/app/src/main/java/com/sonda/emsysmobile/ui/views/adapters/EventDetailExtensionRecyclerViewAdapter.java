package com.sonda.emsysmobile.ui.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.ui.attachgeoloc.AttachGeoLocView;
import com.sonda.emsysmobile.ui.eventdetail.EventDetailsPresenter;
import com.sonda.emsysmobile.ui.fragments.OnListFragmentInteractionListener;
import com.sonda.emsysmobile.utils.DateUtils;

import java.util.List;

public class EventDetailExtensionRecyclerViewAdapter extends RecyclerView
        .Adapter<EventDetailExtensionRecyclerViewAdapter.ViewHolder> {

    private final List<ExtensionDto> mExtensions;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;
    private static final String TAG = EventDetailExtensionRecyclerViewAdapter.class.getName();

    public EventDetailExtensionRecyclerViewAdapter(Context context, List<ExtensionDto> extensions,
                                                   OnListFragmentInteractionListener listener) {
        mExtensions = extensions;
        mListener = listener;
        mContext = context;
    }

    @Override
    public final ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_event_detail_extension, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(final ViewHolder holder, int position) {
        ExtensionDto extension = mExtensions.get(position);
        String zoneString = extension.getZone().getName();
        Log.d(TAG, "ZONE NAME: " + zoneString);
        holder.setItem(extension);
        holder.getIdAndZoneTextView().setText(zoneString);
        if (position == 0) {
            holder.getCurrentExtensionTextView().setVisibility(View.VISIBLE);
        }
        if (extension.getDescription() != null && extension.getDescription() != "") {
            holder.getDescriptionTextView().setText(extension.getDescription());
        }
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.getItem());
                }
            }
        });
    }

    @Override
    public final int getItemCount() {
        return mExtensions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView idAndZoneTextView;
        private final TextView currentExtension;
        private final TextView descriptionTextView;
        private final TextView dispatcherTextView;
        private ExtensionDto item;


        public final View getView() {
            return view;
        }

        public final TextView getIdAndZoneTextView() {
            return idAndZoneTextView;
        }

        public final TextView getCurrentExtensionTextView() {
            return currentExtension;
        }


        public final TextView getDescriptionTextView() {
            return descriptionTextView;
        }

        public final TextView getDispatcherTextView() {
            return descriptionTextView;
        }

        public final ExtensionDto getItem() {
            return item;
        }

        public final void setItem(ExtensionDto item) {
            this.item = item;
        }

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            idAndZoneTextView = (TextView) view.findViewById(R.id.label_id_and_zone);
            currentExtension = (TextView) view.findViewById(R.id.current_extension);
            descriptionTextView = (TextView) view.findViewById(R.id.label_description);
            dispatcherTextView = (TextView) view.findViewById(R.id.label_dispatcher);
        }

        @Override
        public final String toString() {
            return super.toString() + " '" + idAndZoneTextView.getText() + "'";
        }
    }
}
