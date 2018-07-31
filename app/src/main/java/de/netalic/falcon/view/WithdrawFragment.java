package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.WithdrawPresenterContract;
import static com.google.common.base.Preconditions.checkNotNull;

public class WithdrawFragment extends Fragment implements WithdrawPresenterContract.View {


    private WithdrawPresenterContract.Presenter mPresenter;
    private View mRoot;

    @Override
    public void setPresenter(WithdrawPresenterContract.Presenter presenter) {

        mPresenter=checkNotNull(presenter);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mRoot=inflater.inflate(R.layout.fragment_withdraw,null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public static WithdrawFragment newInstance(){

        return new WithdrawFragment();

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }
}
