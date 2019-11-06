package pick.com.app.uitility.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import pick.com.app.R;

import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Objects;

/*
 * Created by ${Raja} on 25-Dec-17.
 */

public class CustomEditText extends AppCompatEditText {

    private final static int TYPE_TEXT_VARIATION_PASSWORD = 129;
    private final static int TYPE_NUMBER_VARIATION_PASSWORD = 18;
    private final static int DEFAULT_PADDING = 15;
    private final int DEFAULT_COLOR = Color.parseColor("#1DA1F2");
    private int mBackgroundColor, clearIconTint, hideShowIconTint, prefixTextColor;
    private int padding, paddingLeft, paddingTop, paddingRight, paddingBottom;
    private float mCornerRadius, mStrokeWidth = 1;
    private float mOriginalLeftPadding = -1;
    private boolean isClearIconVisible;
    private boolean isPassword = false;
    private boolean isShowingPassword = false;
    private Drawable imgCloseButton, drawableEnd;
    private String fontName, mPrefix,mPostfix;

    public CustomEditText(Context context) {
        super(context);
        init(context, null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }




    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText);
        imgCloseButton = ContextCompat.getDrawable(getContext(), android.R.drawable.ic_menu_close_clear_cancel);
        padding = a.getDimensionPixelSize(R.styleable.CustomEditText_android_padding, -1);
        paddingLeft = a.getDimensionPixelSize(R.styleable.CustomEditText_android_paddingLeft, DEFAULT_PADDING);
        paddingTop = a.getDimensionPixelSize(R.styleable.CustomEditText_android_paddingTop, DEFAULT_PADDING);
        paddingRight = a.getDimensionPixelSize(R.styleable.CustomEditText_android_paddingRight, DEFAULT_PADDING);
        paddingBottom = a.getDimensionPixelSize(R.styleable.CustomEditText_android_paddingBottom, DEFAULT_PADDING);
        isClearIconVisible = a.getBoolean(R.styleable.CustomEditText_libIZO_setClearIconVisible, false);
        boolean isBorderView = a.getBoolean(R.styleable.CustomEditText_libIZO_setBorderView, false);
        int mNormalColor = a.getColor(R.styleable.CustomEditText_libIZO_setBorderColor, DEFAULT_COLOR);
        mBackgroundColor = a.getColor(R.styleable.CustomEditText_libIZO_setBackgroundColor, Color.TRANSPARENT);
        mStrokeWidth = a.getDimension(R.styleable.CustomEditText_libIZO_setStrokeWidth, mStrokeWidth);
        hideShowIconTint = a.getColor(R.styleable.CustomEditText_libIZO_hideShowPasswordIconTint, DEFAULT_COLOR);
        clearIconTint = a.getColor(R.styleable.CustomEditText_libIZO_clearIconTint, DEFAULT_COLOR);
        this.fontName = a.getString(R.styleable.CustomEditText_libIZO_setFont);
        mPrefix = a.getString(R.styleable.CustomEditText_libIZO_setPrefix);
        prefixTextColor = a.getColor(R.styleable.CustomEditText_libIZO_setPrefixTextColor, 0);
        mCornerRadius = a.getDimension(R.styleable.CustomEditText_libIZO_setCornerRadius, 1);
        setCursorColor(Color.parseColor("#1DA1F2"));
        if (isBorderView) {
            setBackGroundOfLayout(getShapeBackground(mNormalColor));
            setCursorColor(Color.parseColor("#1DA1F2"));
        } else {
            padding(false);
        }
        if (getInputType() == TYPE_TEXT_VARIATION_PASSWORD || getInputType() == TYPE_NUMBER_VARIATION_PASSWORD) {
            isPassword = false;
            this.setMaxLines(1);
        } else if (getInputType() == EditorInfo.TYPE_CLASS_PHONE) {
            this.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        }
        if (!isPassword && isClearIconVisible) {
            handleClearButton();
        }

        if (mPrefix != null && mPrefix.length() > 0) {
            calculatePrefix();
        }

        if (isPassword)
            if (!TextUtils.isEmpty(getText())) {
                showPasswordVisibilityIndicator(true);
            } else {
                showPasswordVisibilityIndicator(false);
            }
        setOnTouchListener(new OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                CustomEditText editText = CustomEditText.this;
                if (editText.getCompoundDrawables()[2] == null)
                    return false;
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (isPassword) {
                    int width = drawableEnd == null ? 0 : drawableEnd.getIntrinsicWidth();
                    if (event.getX() > editText.getWidth() - editText.getPaddingRight() - width) {
                        togglePasswordVisibility();
                        event.setAction(MotionEvent.ACTION_CANCEL);
                    }
                } else if (isClearIconVisible) {
                    int width = imgCloseButton == null ? 0 : imgCloseButton.getIntrinsicWidth();
                    if (event.getX() > editText.getWidth() - editText.getPaddingRight() - width) {
                        editText.setText("");
                        CustomEditText.this.handleClearButton();
                    }
                }
                return false;
            }
        });
        setFont();
        a.recycle();
    }

    public void setCursorColor(@ColorInt int color) {
        try {
            // Get the cursor resource id
            Field field = TextView.class.getDeclaredField("mCursorDrawableRes");
            field.setAccessible(true);
            int drawableResId = field.getInt(this);

            // Get the editor
            field = TextView.class.getDeclaredField("mEditor");
            field.setAccessible(true);
            Object editor = field.get(this);

            // Get the drawable and set a color filter
            Drawable drawable = ContextCompat.getDrawable(this.getContext(), drawableResId);
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            Drawable[] drawables = {drawable, drawable};

            // Set the drawables
            field = editor.getClass().getDeclaredField("mCursorDrawable");
            field.setAccessible(true);
            field.set(editor, drawables);
        } catch (Exception ignored) {
        }
    }


    private void setFont() {
        if (fontName != null) {
            try {
                setTypeface(get(getContext(), fontName));
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * This method is used to set the rectangle box on EditText
     */
    public void setBackGroundOfLayout(Drawable shape) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(shape);
        } else {
            setBackgroundDrawable(shape);
        }
        padding(true);
    }

    public void setBorderColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(getShapeBackground(color));
        } else {
            setBackgroundDrawable(getShapeBackground(color));
        }
        padding(true);
    }



    private void padding(boolean isRound) {
        int extraPadding;
        int extrapad;
        if (isRound) {
            extraPadding = 5;
            extrapad = 0;
        } else {
            extrapad = 5;
            extraPadding = 0;
        }
        if (padding != -1) {
            super.setPadding(padding + extraPadding, padding, padding, padding + extrapad);
        } else {
            super.setPadding(paddingLeft + extraPadding, paddingTop, paddingRight, paddingBottom + extrapad);
        }
    }


    /**
     * This method is used to draw the rectangle border view with color
     */
    @SuppressLint("WrongConstant")
    public Drawable getShapeBackground(@ColorInt int color) {

        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(mCornerRadius);
        shape.setColor(mBackgroundColor);
        shape.setStroke((int) mStrokeWidth, color);
        return shape;
    }


    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPrefix != null) {
            String prefix = mPrefix;
            Paint myPaint = null;
            if (prefixTextColor != 0) {
                myPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
                myPaint.setColor(prefixTextColor);

              /*  if (getTextLocale().getLanguage().endsWith("ar")){
                    myPaint.setTextAlign(Paint.Align.RIGHT);
                }else {
                    myPaint.setTextAlign(Paint.Align.LEFT);
                }*/

                myPaint.setTextSize(getTextSize());
            }
            canvas.drawText(prefix, mOriginalLeftPadding, getLineBounds(0, null), myPaint == null ? getPaint() : myPaint);
        }
    }


    @SuppressLint("NewApi")
    private void handleClearButton() {
        if (isClearIconVisible) {
            DrawableCompat.setTint(imgCloseButton, clearIconTint);
            imgCloseButton.setBounds(0, 0, 43, 43);
            if (Objects.requireNonNull(this.getText()).length() == 0) {
                this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], null, this.getCompoundDrawables()[3]);
            } else {
                this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], imgCloseButton, this.getCompoundDrawables()[3]);
            }
        }
    }


    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        try {
            if (isPassword) {
                if (s.length() > 0) {
                    showPasswordVisibilityIndicator(true);
                } else {
                    isShowingPassword = false;
                    maskPassword();
                    showPasswordVisibilityIndicator(false);
                }
            } else if (isClearIconVisible)
                CustomEditText.this.handleClearButton();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPasswordVisibilityIndicator(boolean show) {
        if (show) {
            Drawable original = isShowingPassword ?
                    ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_on) :
                    ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_off);
            if (original != null) {
                original.mutate();
                DrawableCompat.setTint(original, hideShowIconTint);
                String locale=getTextLocale().getLanguage();


                    original.setBounds(0, 0, 43, 43);


                drawableEnd = original;


                if (locale.endsWith("ar")){
                    this.setCompoundDrawables(original, this.getCompoundDrawables()[1], this.getCompoundDrawables()[2], this.getCompoundDrawables()[3]);

                }else {
                    this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], original, this.getCompoundDrawables()[3]);

                }

            }
        } else {
            this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], null, this.getCompoundDrawables()[3]);
        }
    }

    //make it visible
    private void unmaskPassword() {
        setTransformationMethod(null);
    }

    //hide it
    private void maskPassword() {
        setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    public void togglePasswordVisibility() {
        // Store the selection
        int selectionStart = this.getSelectionStart();
        int selectionEnd = this.getSelectionEnd();
        // Set transformation method to show/hide password
        if (isShowingPassword) {
            maskPassword();
        } else {
            unmaskPassword();
        }
        // Restore selection
        this.setSelection(selectionStart, selectionEnd);
        // Toggle flag and show indicator
        isShowingPassword = !isShowingPassword;
        showPasswordVisibilityIndicator(true);
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
        setFont();
    }

    public String getFont() {
        return this.fontName;
    }


    public void setPrefix(String prefix) {
        this.mPrefix = prefix;
        calculatePrefix();
        invalidate();
    }

    public String getPrefix() {
        return this.mPrefix;
    }



    private void calculatePrefix() {
        if (mOriginalLeftPadding == -1) {
            String prefix = mPrefix;
            float[] widths = new float[prefix.length()];
            getPaint().getTextWidths(prefix, widths);
            float textWidth = 0;
            for (float w : widths) {
                textWidth += w;
            }



            String locale=getTextLocale().getLanguage();




            if (locale.endsWith("ar")){


                mOriginalLeftPadding = getCompoundPaddingRight();

                setPadding(getPaddingLeft(),
                        getPaddingRight(), (int) (textWidth + getCompoundPaddingRight()),
                        getPaddingBottom());
            }else {

                mOriginalLeftPadding = getCompoundPaddingLeft();

                setPadding((int) (textWidth + mOriginalLeftPadding),
                        getPaddingRight(), getPaddingTop(),
                        getPaddingBottom());
            }


        }
    }

    private void setPrefixTextColor(int prefixTextColor) {
        this.prefixTextColor = prefixTextColor;
        invalidate();
    }



    private  final Hashtable<String, Typeface> cache = new Hashtable<>();

    public  Typeface get(Context c, String name) {
        synchronized (cache) {
            if (!cache.containsKey(name)) {
                Typeface t = Typeface.createFromAsset(c.getAssets(), name);
                cache.put(name, t);
            }
            return cache.get(name);
        }
    }
}