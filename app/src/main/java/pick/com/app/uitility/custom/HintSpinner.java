package pick.com.app.uitility.custom;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import pick.com.app.adapter.HintSpinnerAdapter;

@SuppressWarnings("unused")
public class HintSpinner extends AppCompatSpinner {
    private boolean hasHint = false;

    public HintSpinner(Context context) {
        this(context, null, android.R.attr.spinnerStyle);
    }

    public HintSpinner(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.spinnerStyle);
    }

    public HintSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setClickable(false);
    }

    public void setAdapter(HintSpinnerAdapter adapter) {
        super.setAdapter(adapter);
        hasHint = adapter.hasHint();
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void setOnItemSelectedListener(@Nullable final OnItemSelectedListener listener) {
        super.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    if (!hasHint) {
                        listener.onItemSelected(parent, view, position, id);
                    } else if (position > 0) {
                        listener.onItemSelected(parent, view, position - 1, id);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (listener != null) {
                    listener.onNothingSelected(parent);
                }
            }
        });
    }
}