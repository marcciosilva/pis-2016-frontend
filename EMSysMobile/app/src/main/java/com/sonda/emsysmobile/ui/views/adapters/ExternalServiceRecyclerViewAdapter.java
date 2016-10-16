package com.sonda.emsysmobile.ui.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.logic.model.core.ExternalServiceItemDto;
import com.sonda.emsysmobile.ui.fragments.ExternalServiceFragment.OnListFragmentInteractionListener;

import java.util.List;

public class ExternalServiceRecyclerViewAdapter extends RecyclerView.Adapter<ExternalServiceRecyclerViewAdapter.ViewHolder> {

    private final List<ExternalServiceItemDto> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;

    public ExternalServiceRecyclerViewAdapter(Context context, List<ExternalServiceItemDto> extensions, OnListFragmentInteractionListener listener) {
        mValues = extensions;
        mListener = listener;
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
        ExternalServiceItemDto externalServiceItemDto = mValues.get(position);
        holder.setItem(externalServiceItemDto);
        holder.getFirstField().setText(externalServiceItemDto.getField1());
        holder.getSecondField().setText(externalServiceItemDto.getField2());
        holder.getThirdField().setText(externalServiceItemDto.getField3());
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Envía el llamado a la interfaz que lo implemente
                    // Se va a implementar en el Fragment de servicio externo
                    mListener.onListFragmentInteraction(holder.getItem());
                }
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

        public TextView getFirstField() {
            return firstField;
        }

        public TextView getSecondField() {
            return secondField;
        }

        public TextView getThirdField() {
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
