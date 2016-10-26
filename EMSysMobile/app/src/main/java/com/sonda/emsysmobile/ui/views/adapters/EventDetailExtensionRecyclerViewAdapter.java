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
        .Adapter<EventDetailExtensionRecyclerViewAdapter.ViewHolder> implements
        PopupMenu.OnMenuItemClickListener {

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
        holder.getDescriptionTextView().setText(extension.getDescription());
        holder.getDateTextView().setText(DateUtils.dateToString(extension.getTimeStamp()));
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
        holder.getAttachmentsOptionMenu().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Genero menu para la extension.
                PopupMenu popup = new PopupMenu(view.getContext(), view);
                MenuInflater inflater = popup.getMenuInflater();
                Menu menu = popup.getMenu();
                inflater.inflate(R.menu.actions, menu);
                // Agrego menu para adjuntar audio.
                MenuItem attachAudioOptionItem = menu.findItem(R.id.attach_audio_option);
                attachAudioOptionItem.setIntent(null);
                // Genero intent para adjuntar geoubicacion.
                Intent attachGeolocationIntent =
                        new Intent(view.getContext(), AttachGeoLocView.class);
                Bundle extras = new Bundle();
                extras.putInt("ExtensionId", holder.getItem().getIdentifier());
                attachGeolocationIntent.putExtras(extras);
                // Agrego menu para adjuntar geoubicacion.
                MenuItem attachGeolocationOptionItem = menu.findItem(R.id
                        .attach_geolocation_option);
                attachGeolocationOptionItem.setIntent(attachGeolocationIntent);
                // Agrego menu para adjuntar imagen.
                MenuItem attachImageOptionItem = menu.findItem(R.id.attach_image_option);
                attachImageOptionItem.setIntent(null);
                // Agrego menu para adjuntar video.
                MenuItem attachVideoOptionItem = menu.findItem(R.id.attach_video_option);
                attachVideoOptionItem.setIntent(null);
                popup.show();
                popup.setOnMenuItemClickListener(EventDetailExtensionRecyclerViewAdapter.this);
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.attach_geolocation_option:
                Log.d(TAG, "id de extension es " + Integer.toString(item.getItemId()));
                if (item.getIntent() != null) {
                    Log.d(TAG, "ExtensionId = " + Integer
                            .toString(item.getIntent().getIntExtra("ExtensionId", -1)));
//                    mContext.startActivity(item.getIntent());
                    EventDetailsPresenter.showGeolocationAttachView(item.getIntent());
                }
                break;
            case R.id.attach_audio_option:
                // TODO implementar.
                break;
            case R.id.attach_image_option:
                // TODO implementar.
                break;
            case R.id.attach_video_option:
                // TODO implementar.
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public final int getItemCount() {
        return mExtensions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView idAndZoneTextView;
        private final TextView descriptionTextView;
        private final TextView dateTextView;
        private ExtensionDto item;
        private ImageButton attachmentsOptionMenu;

        public final View getView() {
            return view;
        }

        public final TextView getIdAndZoneTextView() {
            return idAndZoneTextView;
        }

        public final TextView getDescriptionTextView() {
            return descriptionTextView;
        }

        public final TextView getDateTextView() {
            return dateTextView;
        }

        public final ExtensionDto getItem() {
            return item;
        }

        public final void setItem(ExtensionDto item) {
            this.item = item;
        }

        public ImageButton getAttachmentsOptionMenu() {
            return attachmentsOptionMenu;
        }

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            idAndZoneTextView = (TextView) view.findViewById(R.id.label_id_and_zone);
            descriptionTextView = (TextView) view.findViewById(R.id.label_description);
            dateTextView = (TextView) view.findViewById(R.id.label_date);
            attachmentsOptionMenu = (ImageButton) view.findViewById(R.id.attachment_menu);
        }

        @Override
        public final String toString() {
            return super.toString() + " '" + idAndZoneTextView.getText() + "'";
        }
    }
}
