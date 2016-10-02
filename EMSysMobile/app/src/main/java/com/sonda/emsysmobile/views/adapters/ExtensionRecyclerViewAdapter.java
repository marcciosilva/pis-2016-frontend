package com.sonda.emsysmobile.views.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sonda.emsysmobile.fragments.ExtensionsFragment.OnListFragmentInteractionListener;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.model.core.CategoryPriority;
import com.sonda.emsysmobile.model.core.ExtensionDto;
import com.sonda.emsysmobile.utils.DateUtils;

import java.util.List;

public class ExtensionRecyclerViewAdapter extends RecyclerView.Adapter<ExtensionRecyclerViewAdapter.ViewHolder> {

    private final List<ExtensionDto> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;

    public ExtensionRecyclerViewAdapter(Context context, List<ExtensionDto> extensions, OnListFragmentInteractionListener listener) {
        mValues = extensions;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_extension, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ExtensionDto extension = mValues.get(position);
        String idAndZoneString = "#" + extension.getIdentifier() + " - " + extension.getZone().getName();
        CategoryPriority priority = extension.getCategory().getPriority();
        holder.mItem = extension;
        holder.mIdAndZoneTextView.setText(idAndZoneString);
        holder.mDescriptionTextView.setText(extension.getDescription());
        holder.mDateTextView.setText(DateUtils.dateToString(extension.getTimeStamp()));
        if (priority == CategoryPriority.HIGH) {
            holder.mPriorityView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.priority_high));
        } else if (priority == CategoryPriority.MEDIUM) {
            holder.mPriorityView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.priority_medium));
        } else if (priority == CategoryPriority.LOW) {
            holder.mPriorityView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.priority_low));
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdAndZoneTextView;
        public final TextView mDescriptionTextView;
        public final TextView mDateTextView;
        public final View mPriorityView;
        public ExtensionDto mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdAndZoneTextView = (TextView) view.findViewById(R.id.label_id_and_zone);
            mDescriptionTextView = (TextView) view.findViewById(R.id.label_description);
            mPriorityView = view.findViewById(R.id.view_priority_mark);
            mDateTextView = (TextView) view.findViewById(R.id.label_date);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdAndZoneTextView.getText() + "'";
        }
    }
}
