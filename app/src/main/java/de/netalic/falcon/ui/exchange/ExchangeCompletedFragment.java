package de.netalic.falcon.ui.exchange;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.util.ScreenshotUtil;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class ExchangeCompletedFragment extends Fragment implements ExchangeCompletedContract.View {


    private static final String ALPHA_PATH = "/Alpha";
    private static final String SEND_PATH = "/Send";
    private ExchangeCompletedContract.Presenter mExchangeCompletedPresenter;
    private View mRoot;
    private TextView mTextViewExchangeAmount;
    private TextView mTextViewPaidAmount;
    private TextView mTextViewExchangeDate;
    private TextView mTextViewExchangeTime;
    private TextView mTextViewRrn;
    private Button mButtonNavigationToDashboard;
    private static final int REQUEST_PERMISSIONS = 1;
    private static final int IMAGE_QUALITY = 100;
    private View mScreenshotView;
    private Transaction mTransaction;
    private String mPaidAmount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_exchangecompleted, null);
        checkNotNull(getArguments());
        mTransaction = getArguments().getParcelable(ExchangeFragment.ARGUMENT_TRANSACTION);
        mPaidAmount = getArguments().getString(ExchangeFragment.ARGUMENT_PAID_AMOUNT);
        setHasOptionsMenu(true);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponent();
        initListener();
        setTransactionInformation();

    }


    @Override
    public void setPresenter(ExchangeCompletedContract.Presenter presenter) {

        mExchangeCompletedPresenter = presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static ExchangeCompletedFragment newInstance(Transaction transaction, String amount) {

        ExchangeCompletedFragment exchangeCompletedFragment = new ExchangeCompletedFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ExchangeFragment.ARGUMENT_TRANSACTION, transaction);
        bundle.putString(ExchangeFragment.ARGUMENT_PAID_AMOUNT, amount);
        exchangeCompletedFragment.setArguments(bundle);
        return exchangeCompletedFragment;
    }

    public void initUiComponent() {


        mTextViewExchangeAmount = mRoot.findViewById(R.id.textview_exchangecompleted_exchangeamountalpha);
        mTextViewPaidAmount = mRoot.findViewById(R.id.textview_exchangecompleted_paidamount);
        mTextViewExchangeDate = mRoot.findViewById(R.id.textview_exchangecompleted_exchangedate);
        mTextViewExchangeTime = mRoot.findViewById(R.id.textview_exchangecompleted_exchangetime);
        mTextViewRrn = mRoot.findViewById(R.id.textview_exchangecompleted_rrn);
        mButtonNavigationToDashboard = mRoot.findViewById(R.id.button_exchangecompleted_dashborad);
        mScreenshotView = mRoot.findViewById(R.id.linearlayout_exchangecompleted_main);
    }

    public void initListener() {

        mButtonNavigationToDashboard.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

    }

    public void setTransactionInformation() {

        mTextViewExchangeAmount.setText(mTransaction.getActionList().get(1).getCurrencySymbol() + Math.abs(mTransaction.getActionList().get(1).getAmount()));
        mTextViewRrn.setText(mTransaction.getRetrievalReferenceNumber());
        mTextViewPaidAmount.setText(mPaidAmount);
        mTextViewExchangeDate.setText(mTransaction.getDate());
        mTextViewExchangeTime.setText(mTransaction.getTime());

    }

    private void requestPermissionShare() {

        int checkPermission = ContextCompat.checkSelfPermission(checkNotNull(getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        } else {

            File file = new File(String.valueOf(ScreenshotUtil.saveScreenshot(ScreenshotUtil.takeScreenshot(mScreenshotView), IMAGE_QUALITY, ALPHA_PATH + SEND_PATH)));
            ScreenshotUtil.shareScreenshot(file, checkNotNull(getContext()));

        }
    }

    private void requestPermissionSave() {


        int checkPermission = ContextCompat.checkSelfPermission(checkNotNull(getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        } else {

            ScreenshotUtil.saveScreenshot(ScreenshotUtil.takeScreenshot(mScreenshotView), IMAGE_QUALITY, ALPHA_PATH + SEND_PATH);
            SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.everywhere_imagesaved), getContext());

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


            SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.everywhere_permissionallowed), getContext());
        } else {

            SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.everywhere_permissiondenied), getContext());

        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_exchangecompleted_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item_exchangecompletedmenu_download: {

                requestPermissionSave();
                break;

            }

            case R.id.item_exchangecompletedmenu_share: {

                requestPermissionShare();
                break;
            }

        }

        return true;
    }


}
