package pick.com.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import java.util.Arrays;
import java.util.List;


public class HintSpinnerAdapter<T> extends ArrayAdapter<T> {
    private static final String TAG = com.jaiselrahman.hintspinner.HintSpinnerAdapter.class.getSimpleName();
    private List<T> objects;
    private String hint;
    private float textsize=15f;
    private Context context;

    private HintSpinnerAdapter() {
        super(null, 0);
    }

    public HintSpinnerAdapter(Context context, T[] objects) {
        this(context, Arrays.asList(objects));
    }

    public HintSpinnerAdapter(Context context, List<T> objects) {
        this(context, objects, null,null);
    }

    public HintSpinnerAdapter(Context context, T[] objects, @StringRes int hint,Float textsize) {
        this(context, Arrays.asList(objects), hint,textsize);
    }

    public HintSpinnerAdapter(Context context, T[] objects, String hint,Float textsize) {
        this(context, Arrays.asList(objects), hint,textsize);
    }

    public HintSpinnerAdapter(Context context, List<T> objects, @StringRes int hint,Float textsize) {
        this(context, objects, context.getResources().getString(hint),textsize);
    }

    public HintSpinnerAdapter(Context context, List<T> objects, String hint,Float textsize) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.context = context;
        this.objects = objects;
        this.hint = hint;
        this.textsize = textsize;
    }

    public boolean hasHint() {
        return hint != null;
    }

    @Override
    @CallSuper
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        textView = (TextView) convertView;

        textView.setTextSize(textsize);
        if (hint != null && position == 0) {
            textView.setText(hint);
            textView.setTextColor(ContextCompat.getColor(context, android.R.color.tertiary_text_light));
        } else {
            textView.setText(getLabelFor(objects.get(position - 1)));
            textView.setTextColor(ContextCompat.getColor(context, android.R.color.primary_text_light));
        }
        return convertView;
    }

    @NonNull
    @Override
    @CallSuper
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            textView = (TextView) convertView;
            textView.setCompoundDrawables(null, null,
                    ContextCompat.getDrawable(context, android.R.drawable.spinner_background), null);
        } else {
            textView = (TextView) convertView;
        }

        if (hint != null && position == 0) {
            textView.setText(hint);
        } else {
            textView.setText(getLabelFor(objects.get(position - 1)));
        }

        textView.cancelLongPress();
        textView.callOnClick();
        textView.setOnTouchListener(null);

        return convertView;
    }




    public String getLabelFor(T object) {
        return object.toString();
    }

    @Override
    public int getCount() {
        if (hint != null) {
            return super.getCount() + 1;
        } else {
            return super.getCount();
        }
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
    public void setTextsize(Float textsize) {
        this.textsize = textsize;
    }
}