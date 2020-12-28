package com.itdose.googletranslate;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.google.cloud.translate.Language;


import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<Object> {
    private final List<Object> values;

    public SpinnerAdapter(Context context, int textViewResourceId, List<Object> values) {
        super(context, textViewResourceId, values);
        this.values = values;
    }


    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setPadding(2, label.getPaddingTop(), label.getPaddingRight(), label.getPaddingBottom());
        //label.setTextSize(TypedValue.COMPLEX_UNIT_PX, parent.getContext().getResources().getDimension(R.dimen.mediumTextSize));

        label.setTextColor(Color.BLACK);
        if (values.get(position) instanceof String) {
            String value = (String) values.get(position);
            label.setText(value);
        } else if (values.get(position) instanceof Language) {
            Language title = ((Language) values.get(position));
            label.setText(title.getName());
        }
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        if (values.get(position) instanceof String) {
            String value = (String) values.get(position);
            label.setText(value);
        } else if (values.get(position) instanceof Language) {
            Language title = ((Language) values.get(position));
            label.setText(title.getName());
        } 


        return label;
    }

    public static float dipToPixels(Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, context.getResources().getDisplayMetrics());
    }
}
