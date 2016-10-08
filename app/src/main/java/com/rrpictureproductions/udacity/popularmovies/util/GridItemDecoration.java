package com.rrpictureproductions.udacity.popularmovies.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * A simple {@link android.support.v7.widget.RecyclerView.ItemDecoration} that assigns an even
 * spacing to items in a grid, making the spacing between the items the same as the spacing on the
 * outside of the grid.
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {

    private int cols;
    private int spacing;

    public GridItemDecoration(int cols, int spacing) {
        this.cols = cols;
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        outRect.bottom = spacing;
        if (position <= cols-1) {
            outRect.top = spacing;
        } else {
            outRect.top = 0;
        }
        outRect.left = spacing;
        outRect.right = spacing;
        if (position % cols == 0) {
            outRect.right /= 2;
        } else if(position % cols == cols - 1) {
            outRect.left /= 2;
        }
    }
}
