package de.netalic.falcon.ui.setting;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import de.netalic.falcon.R;

public class AvatarTextBehavior extends CoordinatorLayout.Behavior<LinearLayout> {


    private int mStartXPositionText;
    private int mStartYPositionText;
    private int mStartHeight;
    private int mStartToolbarHeight;

    private boolean mInitialised = false;

    private float mAmountOfToolbarToMove;
    private float mAmountOfTextToReduce;
    private float mAmountToMoveXPosition;
    private float mAmountToMoveYPosition;

    private float mFinalToolbarHeight;
    private float mFinalXPosition;
    private float mFinalYPosition;
    private float mFinalHeight;

    public AvatarTextBehavior(final Context context, final AttributeSet attrs) {

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AvatarImageBehavior);
            mFinalXPosition = typedArray.getDimension(R.styleable.AvatarImageBehavior_finalXPosition, 0);
            mFinalYPosition = typedArray.getDimension(R.styleable.AvatarImageBehavior_finalYPosition, 0);
            mFinalHeight = typedArray.getDimension(R.styleable.AvatarImageBehavior_finalHeight, 0);
            mFinalToolbarHeight = typedArray.getDimension(R.styleable.AvatarImageBehavior_finalToolbarHeight, 0);
            typedArray.recycle();
        }
    }

    @Override
    public boolean layoutDependsOn(final CoordinatorLayout parent, final LinearLayout child, final View dependency) {

        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(final CoordinatorLayout parent, final LinearLayout child, final View dependency) {


        initProperties(child, dependency);


        float currentToolbarHeight = mStartToolbarHeight + dependency.getY();

        currentToolbarHeight = currentToolbarHeight < mFinalToolbarHeight ? mFinalToolbarHeight : currentToolbarHeight;
        final float amountAlreadyMoved = mStartToolbarHeight - currentToolbarHeight;
        final float progress = 100 * amountAlreadyMoved / mAmountOfToolbarToMove;


        final float heightToSubtract = progress * mAmountOfTextToReduce / 100;
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        layoutParams.width = (int) (mStartHeight - heightToSubtract);
        layoutParams.height = (int) (mStartHeight - heightToSubtract);
        child.setLayoutParams(layoutParams);


        final float distanceXToSubtract = progress * mAmountToMoveXPosition / 100;
        final float distanceYToSubtract = progress * mAmountToMoveYPosition / 100;
        float newXPosition = mStartXPositionText - distanceXToSubtract;

        child.setX(newXPosition);
        child.setY(mStartYPositionText - distanceYToSubtract);

        return true;
    }

    private void initProperties(final LinearLayout child, final View dependency) {

        if (!mInitialised) {

            mStartHeight = child.getHeight();
            mStartXPositionText = (int) child.getX();
            mStartYPositionText = (int) child.getY();
            mStartToolbarHeight = dependency.getHeight();

            mAmountOfToolbarToMove = mStartToolbarHeight - mFinalToolbarHeight;
            mAmountOfTextToReduce = mStartHeight - mFinalHeight;
            mAmountToMoveXPosition = mStartXPositionText - mFinalXPosition;
            mAmountToMoveYPosition = mStartYPositionText - mFinalYPosition;
            mInitialised = true;
        }
    }


}
