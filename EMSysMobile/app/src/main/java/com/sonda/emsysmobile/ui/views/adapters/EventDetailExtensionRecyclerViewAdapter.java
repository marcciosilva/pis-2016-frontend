package com.sonda.emsysmobile.ui.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.ui.eventdetail.multimedia.ImageGalleryPresenter;
import com.sonda.emsysmobile.ui.fragments.OnListFragmentInteractionListener;

import java.util.List;

public class EventDetailExtensionRecyclerViewAdapter extends RecyclerView
        .Adapter<EventDetailExtensionRecyclerViewAdapter.ViewHolder> {

    private final List<ExtensionDto> mExtensions;
    private final OnListFragmentInteractionListener mListFragmentInteractionListener;
    private Context mContext;
    private static final String TAG = EventDetailExtensionRecyclerViewAdapter.class.getName();

    public EventDetailExtensionRecyclerViewAdapter(Context context, List<ExtensionDto> extensions,
                                                   OnListFragmentInteractionListener
                                                           listFragmentInteractionListener) {
        mExtensions = extensions;
        mListFragmentInteractionListener = listFragmentInteractionListener;
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
        if ((extension.getDescription() != null) && (!extension.getDescription().equals(""))) {
            holder.getDescriptionTextView().setText(extension.getDescription());
        }
        holder.getImagesButton().setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageGalleryPresenter
                        .loadGallery(mContext, holder.getItem()
                                .getImageDescriptions());
            }
        });
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListFragmentInteractionListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListFragmentInteractionListener.onListFragmentInteraction(holder.getItem());
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
        private ImageButton imagesButton;
        private ImageButton videosButton;
        private ImageButton audioButton;
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

        public TextView getCurrentExtension() {
            return currentExtension;
        }

        public ImageButton getImagesButton() {
            return imagesButton;
        }

        public ImageButton getVideosButton() {
            return videosButton;
        }

        public ImageButton getAudioButton() {
            return audioButton;
        }

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            idAndZoneTextView = (TextView) view.findViewById(R.id.label_id_and_zone);
            currentExtension = (TextView) view.findViewById(R.id.current_extension);
            descriptionTextView = (TextView) view.findViewById(R.id.label_description);
            dispatcherTextView = (TextView) view.findViewById(R.id.label_dispatcher);
            imagesButton = (ImageButton) view.findViewById(R.id.button_images);
            videosButton = (ImageButton) view.findViewById(R.id.button_video);
            audioButton = (ImageButton) view.findViewById(R.id.button_audio);
        }

        @Override
        public final String toString() {
            return super.toString() + " '" + idAndZoneTextView.getText() + "'";
        }
    }
}
