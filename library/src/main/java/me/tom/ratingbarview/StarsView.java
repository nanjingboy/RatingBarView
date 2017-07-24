package me.tom.ratingbarview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

class StarsView extends View {

    private int mNumberOfStars;
    private int mStarSize;
    private int mStarSpaceSize;
    private int mStarColor;
    private boolean mIsOnlyDrawStroke;

    private Path mPath;
    private Paint mPaint;

    StarsView(Context context) {
        this(context, null);
    }

    StarsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    StarsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPath = new Path();

        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1);
    }

    void setNumberOfStars(int numberOfStars) {
        mNumberOfStars = numberOfStars;
        invalidate();
    }

    void setStarSize(int starSize) {
        mStarSize = starSize;
        invalidate();
    }

    void setStarSpaceSize(int starSpaceSize) {
        mStarSpaceSize = starSpaceSize;
        invalidate();
    }

    void setStarColor(int starColor) {
        mStarColor = starColor;
        invalidate();
    }

    void setIsOnlyDrawStroke(boolean isOnlyDrawStroke) {
        mIsOnlyDrawStroke = isOnlyDrawStroke;
        invalidate();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        mPath.reset();
        if (mIsOnlyDrawStroke) {
            mPaint.setStyle(Paint.Style.STROKE);
        } else {
            mPaint.setStyle(Paint.Style.FILL);
        }
        mPaint.setColor(mStarColor);
        Point point = new Point(0, 0);
        for (int index = 0; index < mNumberOfStars; index++) {
            drawStar(canvas, new Rect(point.x, point.y, point.x + mStarSize, mStarSize));
            point.x += mStarSize + mStarSpaceSize;
        }
    }

    private void drawStar(Canvas canvas, Rect rect) {
        mPath.moveTo(rect.left + 0.50000f * rect.width(), rect.top + 0.00000f * rect.height());
        mPath.lineTo(rect.left + 0.60940f * rect.width(), rect.top + 0.34942f * rect.height());
        mPath.lineTo(rect.left + 0.97553f * rect.width(), rect.top + 0.34549f * rect.height());
        mPath.lineTo(rect.left + 0.67702f * rect.width(), rect.top + 0.55752f * rect.height());
        mPath.lineTo(rect.left + 0.79389f * rect.width(), rect.top + 0.90451f * rect.height());
        mPath.lineTo(rect.left + 0.50000f * rect.width(), rect.top + 0.68613f * rect.height());
        mPath.lineTo(rect.left + 0.20611f * rect.width(), rect.top + 0.90451f * rect.height());
        mPath.lineTo(rect.left + 0.32298f * rect.width(), rect.top + 0.55752f * rect.height());
        mPath.lineTo(rect.left + 0.02447f * rect.width(), rect.top + 0.34549f * rect.height());
        mPath.lineTo(rect.left + 0.39060f * rect.width(), rect.top + 0.34942f * rect.height());
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }
}
