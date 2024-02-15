package com.ao8r.labapp.customiz;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import com.ao8r.labapp.models.ReferenceData;


import java.util.List;

public class CustomSpinner<T> extends ArrayAdapter<T> {

    private Context context;
    private List<T> items;

    public CustomSpinner(Context context, int resource, List<T> items) {
        super(context, resource, items);
        this.context = context;
        this.items = items;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, android.R.layout.simple_spinner_item, null);
        }

        if (position >= 0 && position < items.size()) {
            T item = items.get(position);
            if (item instanceof CharSequence) {
                ((TextView) convertView).setText((CharSequence) item);
            } else {
                ((TextView) convertView).setText(item.toString());
            }
        }
        ReferenceData.SELECTED_ITEM_SPINNER = items.get(position).toString();
        System.out.println("SELECTED ITEM FROM spinner =="+items.get(position).toString());


        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, android.R.layout.simple_spinner_dropdown_item, null);
        }

        if (position >= 0 && position < items.size()) {
            T item = items.get(position);
            if (item instanceof CharSequence) {
                ((TextView) convertView).setText((CharSequence) item);
            } else {
                ((TextView) convertView).setText(item.toString());
            }
        }


        return convertView;
    }

    public static <T> void setupSpinner(Context context, Spinner spinner, List<T> items) {
        CustomSpinner<T> adapter = new CustomSpinner<>(context, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}


