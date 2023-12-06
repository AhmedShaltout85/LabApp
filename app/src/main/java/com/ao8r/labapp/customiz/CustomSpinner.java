package com.ao8r.labapp.customiz;

//import static java.security.AccessController.getContext;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//import java.util.List;
//
//public class CustomSpinner<T> extends ArrayAdapter<T> {
//
//    private Context context;
//    private List<T> items;
//
//    public CustomSpinner(Context context, int resource, List<T> items) {
//        super(context, resource, items);
//        this.context = context;
//        this.items = items;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = View.inflate(context, android.R.layout.simple_spinner_item, null);
//        }
//
//        if (position >= 0 && position < items.size()) {
//            T item = items.get(position);
//            if (item instanceof CharSequence) {
//                ((TextView) convertView).setText((CharSequence) item);
//            } else {
//                ((TextView) convertView).setText(item.toString());
//            }
//        }
//
//        return convertView;
//    }
//
//    @Override
//    public View getDropDownView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = View.inflate(context, android.R.layout.simple_spinner_dropdown_item, null);
//        }
//
//        if (position >= 0 && position < items.size()) {
//            T item = items.get(position);
//            if (item instanceof CharSequence) {
//                ((TextView) convertView).setText((CharSequence) item);
//            } else {
//                ((TextView) convertView).setText(item.toString());
//            }
//        }
//
//        return convertView;
//    }
//
//    public static <T> void setupSpinner(Context context, Spinner spinner, List<T> items) {
//        CustomSpinner<T> adapter = new CustomSpinner<>(context, android.R.layout.simple_spinner_item, items);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//    }
//}


//    To use this class, you can create an instance of CustomSpinner and set it as the adapter
//        for your Spinner in your activity or fragment:
//
//    List<String> spinnerItems = Arrays.asList("Item 1", "Item 2", "Item 3");
//
//    Spinner mySpinner = findViewById(R.id.my_spinner); // Replace with your Spinner ID
//    CustomSpinner.setupSpinner(this,mySpinner,spinnerItems);


import android.content.Context;
        import android.util.AttributeSet;
        import android.widget.ArrayAdapter;
        import androidx.appcompat.widget.AppCompatSpinner;

        import java.util.List;

public class CustomSpinner<T> extends AppCompatSpinner {

    private List<T> dataList;
    private OnItemSelectedListener<T> onItemSelectedListener;

    public CustomSpinner(Context context) {
        super(context);
        initialize();
    }

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        // Set any additional configurations here
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
        setupAdapter();
    }

    public void setOnItemSelectedListener(OnItemSelectedListener<T> listener) {
        this.onItemSelectedListener = listener;
        setOnItemSelectedListener(new InternalOnItemSelectedListener());
    }

    private void setupAdapter() {
        ArrayAdapter<T> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, dataList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setAdapter(adapter);
    }

    public interface OnItemSelectedListener<T> {
        void onItemSelected(T item);
    }

    private class InternalOnItemSelectedListener implements OnItemSelectedListener<T> {
        @Override
        public void onItemSelected(T item) {
            if (onItemSelectedListener != null) {
                onItemSelectedListener.onItemSelected(item);
            }
        }
    }
}

//public class YourActivity extends AppCompatActivity {
//
//    private ReusableDropdownSpinner<String> dropdownSpinner;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Assume dataList is a list of strings you want to show in the spinner
//        List<String> dataList = Arrays.asList("Item 1", "Item 2", "Item 3");
//
//        dropdownSpinner = findViewById(R.id.your_spinner_id);
//        dropdownSpinner.setDataList(dataList);
//        dropdownSpinner.setOnItemSelectedListener(new ReusableDropdownSpinner.OnItemSelectedListener<String>() {
//            @Override
//            public void onItemSelected(String item) {
//                // Handle item selection here
//                Toast.makeText(YourActivity.this, "Selected: " + item, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}
