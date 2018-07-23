package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.adapter.OffsetItemDecoration;
import de.netalic.falcon.adapter.WalletRecyclerViewAdapter;

public class ChargeFragment extends Fragment {

    private View mRoot;
    private RecyclerView mRecyclerViewWallets;
    private WalletRecyclerViewAdapter mWalletRecyclerViewAdapter;
    View mLastSnappedView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_charge, null);
        return mRoot;
    }

    public static ChargeFragment newInstance() {

        return new ChargeFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mRecyclerViewWallets = mRoot.findViewById(R.id.recyclerViewWallets);
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("4");
        list.add("4");
        list.add("4");
        list.add("4");
        list.add("4");

        mWalletRecyclerViewAdapter = new WalletRecyclerViewAdapter(list);
        mRecyclerViewWallets.setAdapter(mWalletRecyclerViewAdapter);
        mRecyclerViewWallets.addItemDecoration(new OffsetItemDecoration(getContext()));
        mRecyclerViewWallets.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerViewWallets);


        mRecyclerViewWallets.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View view = snapHelper.findSnapView(recyclerView.getLayoutManager());
                    view.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    View centerView = snapHelper.findSnapView(recyclerView.getLayoutManager());
                    int pos = recyclerView.getLayoutManager().getPosition(centerView);
                    System.out.println(pos);
                    mLastSnappedView = view;
                } else if (mLastSnappedView != null) {

                    mLastSnappedView.setBackgroundColor(getResources().getColor(android.R.color.white));
                    mLastSnappedView = null;

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {


            }
        });
    }
}
