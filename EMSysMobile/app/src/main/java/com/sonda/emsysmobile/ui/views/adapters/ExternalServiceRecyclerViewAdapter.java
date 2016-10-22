package com.sonda.emsysmobile.ui.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.logic.model.core.ExternalServiceItemDto;
import com.sonda.emsysmobile.ui.activities.ExternalServiceDetailActivity;
import com.sonda.emsysmobile.utils.constants.ExternalService;

import java.util.List;

public class ExternalServiceRecyclerViewAdapter extends RecyclerView.Adapter<ExternalServiceRecyclerViewAdapter.ViewHolder> {

    private final List<ExternalServiceItemDto> mValues;
    private Context mContext;

    public ExternalServiceRecyclerViewAdapter(Context context, List<ExternalServiceItemDto> extensions) {
        mValues = extensions;
        mContext = context;
    }

    @Override
    public final ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_external_service_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(final ViewHolder holder, int position) {
        final ExternalServiceItemDto externalServiceItemDto = mValues.get(position);
        holder.setItem(externalServiceItemDto);
        holder.getFirstField().setText(externalServiceItemDto.getField1());
        holder.getSecondField().setText(externalServiceItemDto.getField2());
        holder.getThirdField().setText(externalServiceItemDto.getField3());
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO esto podría llegar a ir en un presenter (?) ... si bien nunca lo hice así je
                Intent i = new Intent(mContext, ExternalServiceDetailActivity.class);
                i.putExtra(ExternalService.DETAIL_KEY_PARAM, externalServiceItemDto);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public final int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView firstField;
        private final TextView secondField;
        private final TextView thirdField;
        private ExternalServiceItemDto item;

        public final View getView() {
            return view;
        }

        public final ExternalServiceItemDto getItem() {
            return item;
        }

        public final void setItem(ExternalServiceItemDto item) {
            this.item = item;
        }

        public final TextView getFirstField() {
            return firstField;
        }

        public final TextView getSecondField() {
            return secondField;
        }

        public final TextView getThirdField() {
            return thirdField;
        }

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            firstField = (TextView) view.findViewById(R.id.first_field_tv);
            secondField = (TextView) view.findViewById(R.id.second_field_tv);
            thirdField = (TextView)view.findViewById(R.id.third_field_tv);
        }

        @Override
        public final String toString() {
            return super.toString() + " '" + firstField.getText() + "'";
        }
    }
}
