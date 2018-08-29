package de.netalic.falcon.ui.charge;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class OffsetItemDecoration extends RecyclerView.ItemDecoration {

    private Context ctx;

    public OffsetItemDecoration(Context ctx) {

        this.ctx = ctx;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        super.getItemOffsets(outRect, view, parent, state);
        int nCols = parent.getAdapter().getItemCount() + 1;
        int colWidth = (int) (getScreenWidth() / (float) (nCols));

        if (parent.getChildAdapterPosition(view) == 0) {
            int offset = Math.round(getScreenWidth() / 3f - colWidth / 3f);
            setupOutRect(outRect, offset, true);
        } else if (parent.getChildAdapterPosition(view) == state.getItemCount() - 1) {
            int offset = Math.round(getScreenWidth() / 3f - colWidth / 3f);
            setupOutRect(outRect, offset, false);
        }
    }

    private void setupOutRect(Rect rect, int offset, boolean start) {

        if (start) rect.left = offset;
        else rect.right = offset;
    }

    private int getScreenWidth() {

        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
}