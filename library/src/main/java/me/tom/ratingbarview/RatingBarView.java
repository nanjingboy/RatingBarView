package me.tom.ratingbarview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

public class RatingBarView extends FrameLayout {

    private float mScore;
    private int mMaxScore;
    private int mStarSize;
    private int mStarSpaceSize;
    private int mStarColor;

    private StarsView mFillStarsView;
    private StarsView mStrokeStarsView;

    public RatingBarView(Context context) {
        this(context, null);
    }

    public RatingBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mFillStarsView = new StarsView(context);
        mStrokeStarsView = new StarsView(context);

        Resources resources = getResources();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBarView);
        mScore = typedArray.getFloat(R.styleable.RatingBarView_score, 0);
        mMaxScore = typedArray.getInt(R.styleable.RatingBarView_maxScore, 5);
        mStarSize = typedArray.getDimensionPixelSize(
                R.styleable.RatingBarView_starSize,
                resources.getDimensionPixelSize(R.dimen.rating_bar_star_size)
        );
        mStarSpaceSize = typedArray.getDimensionPixelSize(
                R.styleable.RatingBarView_starSpaceSize,
                resources.getDimensionPixelSize(R.dimen.rating_bar_star_space_size)
        );
        mStarColor = typedArray.getColor(
                R.styleable.RatingBarView_starColor,
                ContextCompat.getColor(context, R.color.colorRatingBarStarView)
        );
        typedArray.recycle();
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                drawStarViews();
            }
        });
    }

    public void setScore(float score) {
        mScore = score;
        drawStarViews();
    }

    public void setMaxScore(int maxScore) {
        mMaxScore = maxScore;
        drawStarViews();
    }

    public void setStarSize(int starSize) {
        mStarSize = starSize;
        drawStarViews();
    }

    public void setStarSpaceSize(int starSpaceSize) {
        mStarSpaceSize = starSpaceSize;
        drawStarViews();
    }

    public void setStarColor(int starColor) {
        mStarColor = starColor;
        drawStarViews();
    }

    private void drawStarViews() {
        removeAllViews();
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        if (width <= 0 || height <= 0) {
            return;
        }

        int startsWidth = (mStarSize + mStarSpaceSize) * mMaxScore - mStarSpaceSize;
        int centerX = (width - startsWidth) / 2;
        if (centerX < 0) {
            centerX = 0;
        }

        if (mScore == 0) {
            drawStrokeStarsView(centerX, startsWidth);
            return;
        }

        if (mScore >= mMaxScore) {
            drawFillStarsView(centerX, startsWidth);
            return;
        }

        drawStrokeStarsView(centerX, startsWidth);
        drawFillStarsView(centerX, Double.valueOf(Math.ceil(mScore / mMaxScore * startsWidth)).intValue());
    }

    private void drawStrokeStarsView(int centerX, int width) {
        mStrokeStarsView.setNumberOfStars(mMaxScore);
        mStrokeStarsView.setStarColor(mStarColor);
        mStrokeStarsView.setStarSize(mStarSize);
        mStrokeStarsView.setStarSpaceSize(mStarSpaceSize);
        mStrokeStarsView.setIsOnlyDrawStroke(true);
        LayoutParams params = new LayoutParams(width, mStarSize);
        params.leftMargin = centerX;
        params.gravity = Gravity.CENTER_VERTICAL;
        mStrokeStarsView.setLayoutParams(params);
        addView(mStrokeStarsView);
    }

    private void drawFillStarsView(int centerX, int width) {
        mFillStarsView.setNumberOfStars(mMaxScore);
        mFillStarsView.setStarColor(mStarColor);
        mFillStarsView.setStarSize(mStarSize);
        mFillStarsView.setStarSpaceSize(mStarSpaceSize);
        mFillStarsView.setIsOnlyDrawStroke(false);
        LayoutParams params = new LayoutParams(width, mStarSize);
        params.leftMargin = centerX;
        params.gravity = Gravity.CENTER_VERTICAL;
        mFillStarsView.setLayoutParams(params);
        addView(mFillStarsView);
    }
}