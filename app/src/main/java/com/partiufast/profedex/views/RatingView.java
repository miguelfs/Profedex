package com.partiufast.profedex.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.partiufast.profedex.R;
import com.partiufast.profedex.data.Rating;

/**
 * TODO: document your custom view class.
 */
public class RatingView extends RelativeLayout implements RatingBar.OnRatingBarChangeListener {
    private String mRatingTypeName; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    View rootView;
    TextView mRatingName;
    RatingBar mRatingBar;
    Rating mRating;
    OnRatingSend ratingSender;

    public RatingView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public RatingView(Context context, Rating rating) {
        super(context);
        mRating = rating;
        init(context, null, 0);
    }

    public RatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public RatingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {

        rootView = inflate(context, R.layout.rating_view_big, this);
        mRatingName = (TextView) rootView.findViewById(R.id.rating_name);
        mRatingBar = (RatingBar) rootView.findViewById(R.id.rating_bar);
        mRatingBar.setOnRatingBarChangeListener(this);
        if (mRating != null) {
            Log.d("R", "NOT NULL" + mRating.getValue());
            mRatingBar.setRating(mRating.getValue());
            mRatingName.setText(mRating.getName());
        }
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.RatingView, defStyle, 0);

        mRatingTypeName = a.getString(
                R.styleable.RatingView_exampleString);
        mExampleColor = a.getColor(
                R.styleable.RatingView_exampleColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.RatingView_exampleDimension,
                mExampleDimension);
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(mExampleColor);
        mTextWidth = mTextPaint.measureText(mRatingTypeName);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        if (fromUser) {
            mRating.setUserRating(rating);
            Log.d("","Rating: "+rating);
            ratingSender.sendRatingData(mRating);
        }
    }
    public interface OnRatingSend {

        void sendRatingData(Rating rating);
    }
    public void setOnRatingSend(OnRatingSend obj) {
        ratingSender = obj;
    }
    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getRatingType() {
        return mRatingTypeName;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setRatingType(String exampleString) {
        mRatingTypeName = exampleString;
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
    }
}
