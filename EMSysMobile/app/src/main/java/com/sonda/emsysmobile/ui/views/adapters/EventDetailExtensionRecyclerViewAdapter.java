package com.sonda.emsysmobile.ui.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.logic.model.core.DescriptionDto;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.logic.model.core.ResourceAssignationDto;
import com.sonda.emsysmobile.ui.eventdetail.multimedia.ImageGalleryPresenter;
import com.sonda.emsysmobile.ui.interfaces.OnListFragmentInteractionListener;

import java.util.ArrayList;
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
        if ((extension.getDispatcherDescription() != null) && (!extension.getDispatcherDescription().isEmpty())) {
            for (DescriptionDto desc: extension.getDispatcherDescription()) {
                TextView view = (TextView) TextView.inflate(mContext,R.layout.event_details_description_row,null);
                view.setText(desc.toString());
                holder.getDispatcherDescriptionLinearLayout().addView(view);
            }
        } else {
            TextView view = (TextView) TextView.inflate(mContext,R.layout.event_details_description_row,null);
            view.setText(R.string.event_details_missing_value);
            holder.getDispatcherDescriptionLinearLayout().addView(view);
        }

        List<String> resourceDescriptions = getResourceDescriptions(extension.getResourceAssignations());
        if ((resourceDescriptions != null) && (!resourceDescriptions.isEmpty())) {
            for (String desc: resourceDescriptions) {
                TextView view = (TextView) TextView.inflate(mContext,R.layout.event_details_description_row,null);
                view.setText(desc);
                holder.getResourceDescriptionLinearLayout().addView(view);
            }
        } else {
            TextView view = (TextView) TextView.inflate(mContext,R.layout.event_details_description_row,null);
            view.setText(R.string.event_details_missing_value);
            holder.getResourceDescriptionLinearLayout().addView(view);
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
        // Initializes the resources list
        ArrayAdapter<String> resourcesAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,
                android.R.id.text1, extension.getResources());
        if (extension.getResources().size() > 0){
            holder.getResourcesLabel().setVisibility(View.GONE);
            holder.getResourcesListView().setAdapter(resourcesAdapter);
            setListViewHeightBasedOnChildren(holder.getResourcesListView());
        }else{
            holder.getResourcesListView().setVisibility(View.GONE);
        }
    }

    // This sets the height of the list view to show all resources
    private static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private List<String> getResourceDescriptions(List<ResourceAssignationDto> resourceAssignationList) {
        List<String> result = new ArrayList<>();
        if (resourceAssignationList != null){
            for (ResourceAssignationDto resourceAssignation: resourceAssignationList) {
                List<DescriptionDto> descriptions = resourceAssignation.getDescriptions();
                for (DescriptionDto description: descriptions){
                    if(description.getUser() == null)
                        description.setUser(resourceAssignation.getResource());
                    result.add(description.toString());
                }

            }
        }
        return result;
    }

    @Override
    public final int getItemCount() {
        return mExtensions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView idAndZoneTextView;
        private final TextView currentExtension;
        private final LinearLayout dispatcherDescriptionLinearLayout;
        private final LinearLayout resourceDescriptionLinearLayout;
        private final TextView dispatcherTextView;
        private ImageButton imagesButton;
        private ImageButton videosButton;
        private ImageButton audioButton;
        private ExtensionDto item;
        private final ListView resourcesListView;
        private final TextView resourcesLabel;

        public final View getView() {
            return view;
        }

        public final TextView getIdAndZoneTextView() {
            return idAndZoneTextView;
        }

        public final TextView getCurrentExtensionTextView() {
            return currentExtension;
        }


        public final LinearLayout getDispatcherDescriptionLinearLayout() {
            return dispatcherDescriptionLinearLayout;
        }

        public final LinearLayout getResourceDescriptionLinearLayout() {
            return resourceDescriptionLinearLayout;
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

        public ListView getResourcesListView(){return resourcesListView;}

        public TextView getResourcesLabel(){return resourcesLabel;}

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            idAndZoneTextView = (TextView) view.findViewById(R.id.label_id_and_zone);
            currentExtension = (TextView) view.findViewById(R.id.current_extension);
            dispatcherDescriptionLinearLayout = (LinearLayout) view.findViewById(R.id.dispatcher_description);
            resourceDescriptionLinearLayout = (LinearLayout) view.findViewById(R.id.resource_description);
            dispatcherTextView = (TextView) view.findViewById(R.id.label_dispatcher);
            imagesButton = (ImageButton) view.findViewById(R.id.button_images);
            videosButton = (ImageButton) view.findViewById(R.id.button_video);
            audioButton = (ImageButton) view.findViewById(R.id.button_audio);
            resourcesListView = (ListView) view.findViewById(R.id.list_resources);
            resourcesLabel = (TextView) view.findViewById(R.id.label_resources);
        }

        @Override
        public final String toString() {
            return super.toString() + " '" + idAndZoneTextView.getText() + "'";
        }
    }
}
