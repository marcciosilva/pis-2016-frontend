package com.sonda.emsysmobile.ui.views.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.logic.model.core.CategoryPriority;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.ui.interfaces.OnListFragmentInteractionListener;
import com.sonda.emsysmobile.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class ExtensionRecyclerViewAdapter extends RecyclerView
        .Adapter<ExtensionRecyclerViewAdapter.ViewHolder> implements
        PopupMenu.OnMenuItemClickListener {

    private final List<ExtensionDto> mExtensions;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;

    public ExtensionRecyclerViewAdapter(Context context, List<ExtensionDto> extensions,
                                        OnListFragmentInteractionListener listener) {
        mExtensions = extensions;
        mListener = listener;
        mContext = context;
    }

    public ExtensionRecyclerViewAdapter(Context context,
                                        OnListFragmentInteractionListener listener) {
        mExtensions = new ArrayList<>();
        mListener = listener;
        mContext = context;
    }

    public final List<ExtensionDto> getExtensions() {
        return mExtensions;
    }

    public final void setExtensions(List<ExtensionDto> mValues) {
        mExtensions.clear();
        mExtensions.addAll(mValues);
    }

    @Override
    public final ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_extension, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(final ViewHolder holder, int position) {
        ExtensionDto extension = mExtensions.get(position);

        String idAndZoneString =
                extension.getEvent().getIdentifier() + " - " + extension.getZone().getName();
        CategoryPriority priority = extension.getPriority();
        holder.setItem(extension);
        holder.getIdAndZoneTextView().setText(idAndZoneString);
        holder.getDescriptionTextView().setText(extension.getDescription());
        holder.getDateTextView().setText(DateUtils.dateToString(extension.getTimeStamp()));
        holder.getMenu().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Genero menu para la extension.
                PopupMenu popup = new PopupMenu(view.getContext(), view);
                MenuInflater inflater = popup.getMenuInflater();
                Menu menu = popup.getMenu();
                inflater.inflate(R.menu.menu_extensions_actions, menu);
                // Agrego item de ejemplo.
                MenuItem attachAudioOptionItem = menu.findItem(R.id.menu_dummy_option);
                attachAudioOptionItem.setIntent(null);
                popup.show();
                popup.setOnMenuItemClickListener(ExtensionRecyclerViewAdapter.this);
            }
        });
        if (priority == CategoryPriority.HIGH) {
            holder.getPriorityView()
                    .setBackgroundColor(ContextCompat.getColor(mContext, R.color.priority_high));
        } else if (priority == CategoryPriority.MEDIUM) {
            holder.getPriorityView()
                    .setBackgroundColor(ContextCompat.getColor(mContext, R.color.priority_medium));
        } else if (priority == CategoryPriority.LOW) {
            holder.getPriorityView()
                    .setBackgroundColor(ContextCompat.getColor(mContext, R.color.priority_low));
        }
        if (extension.isModified()) {
            holder.modifiedIndicator.setVisibility(View.VISIBLE);
        } else {
            holder.modifiedIndicator.setVisibility(View.GONE);
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

    @Override
    public final boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_dummy_option:
                break;
            default:
                return false;
        }
        return true;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView idAndZoneTextView;
        private final TextView descriptionTextView;
        private final TextView dateTextView;
        private final View priorityView;
        private final View modifiedIndicator;
        private ExtensionDto item;
        private ImageButton menu;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            idAndZoneTextView = (TextView) view.findViewById(R.id.label_id_and_zone);
            descriptionTextView = (TextView) view.findViewById(R.id.label_description);
            priorityView = view.findViewById(R.id.view_priority_mark);
            dateTextView = (TextView) view.findViewById(R.id.label_date);
            modifiedIndicator = view.findViewById(R.id.modified_indicator);
            menu = (ImageButton) view.findViewById(R.id.menu_extensions);
        }

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

        public final View getPriorityView() {
            return priorityView;
        }

        public final ExtensionDto getItem() {
            return item;
        }

        public final void setItem(ExtensionDto item) {
            this.item = item;
        }

        public final ImageButton getMenu() {
            return menu;
        }

        public final void setMenu(ImageButton menu) {
            this.menu = menu;
        }

        @Override
        public final String toString() {
            return super.toString() + " '" + idAndZoneTextView.getText() + "'";
        }
    }
}
