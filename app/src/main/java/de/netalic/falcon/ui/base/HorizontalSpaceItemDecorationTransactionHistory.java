package de.netalic.falcon.ui.base;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

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

