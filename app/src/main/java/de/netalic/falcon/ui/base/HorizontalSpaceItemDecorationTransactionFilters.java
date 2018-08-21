package de.netalic.falcon.ui.base;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class HorizontalSpaceItemDecorationTransactionFilters extends RecyclerView.ItemDecoration {

    private final int mHorizontalSpaceHeight;

    public HorizontalSpaceItemDecorationTransactionFilters(int horizontalSpaceHeight) {

        mHorizontalSpaceHeight = horizontalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int itemPosition = parent.getChildPosition(view);
        if (itemPosition == 2 || itemPosition == 7) {
            outRect.bottom = mHorizontalSpaceHeight * 8;
        } else if (itemPosition == 0 || itemPosition == 3 || itemPosition == 8) {
            outRect.bottom = 0;
        } else {
            outRect.bottom = mHorizontalSpaceHeight;
        }
    }
}