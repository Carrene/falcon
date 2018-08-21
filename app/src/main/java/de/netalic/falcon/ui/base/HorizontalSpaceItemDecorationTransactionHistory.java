package de.netalic.falcon.ui.base;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import de.netalic.falcon.R;

public class HorizontalSpaceItemDecorationTransactionHistory extends RecyclerView.ItemDecoration {

    private final int mHorizontalSpaceHeight;

    public HorizontalSpaceItemDecorationTransactionHistory(int horizontalSpaceHeight) {
        mHorizontalSpaceHeight = horizontalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {


            outRect.bottom = mHorizontalSpaceHeight;

    }
    }

