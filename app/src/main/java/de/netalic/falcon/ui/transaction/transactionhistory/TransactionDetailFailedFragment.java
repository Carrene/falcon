package de.netalic.falcon.ui.transaction.transactionhistory;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.File;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Receipt;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.util.ScreenshotUtil;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransactionDetailFailedFragment extends Fragment implements TransactionDetailFailedContract.View {
    private TransactionDetailFailedContract.Presenter mTransactionDetailFailedPresenter;
    private static final String ALPHA_PATH = "/Alpha";
    private static final String CHARGE_PATH = "/Transaction Detail";
    private View mRoot;
    private static final String ARGUMENT_RECEIPT = "receipt";
    private Receipt mReceipt;
    private TextView mTextViewWalletName;
    private TextView mTextViewChargeAmount;
    private TextView mTextViewPaidAmount;
    private TextView mTextViewPaymentGateway;
    private TextView mTextViewTransactionDate;
    private TextView mTextViewTransactionTime;
    private TextView mTextViewRrn;
    private Button mButtonNavigationDashboard;
    private static final int REQUEST_PERMISSIONS = 1;
    private static final int IMAGE_QUALITY = 100;
    private View mScreenshotView;
    private RelativeLayout mRelativeLayoutRrnBox;
    private View mViewRrnLine;
    private View mViewPaymentGatewayLine;
    private RelativeLayout mRelativeLayoutPaymentGatewayBox;
    private String mTransactionType;

    @Override
    public void setPresenter(TransactionDetailFailedContract.Presenter presenter) {
        mTransactionDetailFailedPresenter = checkNotNull(presenter);
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mRoot = inflater.inflate(R.layout.fragment_transactiondetailfailed, null);
        checkNotNull(getArguments());
        setHasOptionsMenu(true);
        mReceipt = getArguments().getParcelable(ARGUMENT_RECEIPT);
        mTransactionType = getArguments().getString(TransactionHistoryRecyclerViewAdapter.TRANSACTION_TYPE);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponent();

        setPaymentInformation();
        initListener();
    }

    public static TransactionDetailFailedFragment newInstance(Receipt receipt, String transactionType) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_RECEIPT, receipt);
        bundle.putString(TransactionHistoryRecyclerViewAdapter.TRANSACTION_TYPE, transactionType);
        TransactionDetailFailedFragment fragment = new TransactionDetailFailedFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void initUiComponent() {

        mTextViewWalletName = mRoot.findViewById(R.id.textview_transactiondetailfailed_walletname);
        mTextViewChargeAmount = mRoot.findViewById(R.id.textview_transactiondetailfailed_amountalpha);
        mTextViewPaidAmount = mRoot.findViewById(R.id.textview_transactiondetailfailed_amountdollar);
        mTextViewPaymentGateway = mRoot.findViewById(R.id.textview_transactiondetailfailed_paymentgateway);
        mTextViewTransactionDate = mRoot.findViewById(R.id.textview_transactiondetailfailed_transactiondate);
        mTextViewTransactionTime = mRoot.findViewById(R.id.textview_transactiondetailfailed_transactiontime);
        mTextViewRrn = mRoot.findViewById(R.id.textview_transactiondetailfailed_rrn);
        mButtonNavigationDashboard = mRoot.findViewById(R.id.button_transactiondetailfailed_dashborad);
        mScreenshotView = mRoot.findViewById(R.id.linearlayout_transactiondetailfailed_main);
        mRelativeLayoutRrnBox = mRoot.findViewById(R.id.relativelayout_transactiondetailfailed_rrnbox);
        mViewRrnLine = mRoot.findViewById(R.id.view_transactiondetailfailed_rrnlineview);
        mRelativeLayoutPaymentGatewayBox = mRoot.findViewById(R.id.relativelayout_transactiondetailfailed_paymentgatewaybox);
        mViewPaymentGatewayLine = mRoot.findViewById(R.id.view_transactiondetailfailed_paymentgatewaylineview);

        mRelativeLayoutRrnBox.setVisibility(mRoot.GONE);
        mViewRrnLine.setVisibility(mRoot.GONE);

        if (mTransactionType.equals(getContext().getString(R.string.transactiondetail_forchecktransferorcharge))) {

            mRelativeLayoutPaymentGatewayBox.setVisibility(mRoot.GONE);
            mViewPaymentGatewayLine.setVisibility(mRoot.GONE);
        }


    }

    public void setPaymentInformation() {
        mTextViewWalletName.setText(mReceipt.getRecipientWalletName());
        mTextViewChargeAmount.setText(mReceipt.getBaseCurrencySymbol() + " " + String.valueOf(Math.abs(mReceipt.getBaseAmount())));
        mTextViewPaidAmount.setText(mReceipt.getQuoteCurrencySymbol() + " " + String.valueOf(mReceipt.getQuoteAmount()));
        mTextViewPaymentGateway.setText(mReceipt.getPaymentGatewayName());
        mTextViewTransactionDate.setText(mReceipt.getDate());
        mTextViewTransactionTime.setText(mReceipt.getTime());
        mTextViewRrn.setText(mReceipt.getRetrievalReferenceNumber());
    }

    public void initListener() {

        mButtonNavigationDashboard.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), DashboardActivity.class);
            startActivity(intent);
        });

    }

    private void requestPermissionShare() {


        int checkPermission = ContextCompat.checkSelfPermission(checkNotNull(getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        } else {

            File file = ScreenshotUtil.saveScreenshot(ScreenshotUtil.takeScreenshot(mScreenshotView), IMAGE_QUALITY, ALPHA_PATH + CHARGE_PATH);
            ScreenshotUtil.shareScreenshot(file, checkNotNull(getContext()));
        }
    }

    private void requestPermissionSave() {


        int checkPermission = ContextCompat.checkSelfPermission(checkNotNull(getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        } else {


            ScreenshotUtil.saveScreenshot(ScreenshotUtil.takeScreenshot(mScreenshotView), IMAGE_QUALITY, ALPHA_PATH + CHARGE_PATH);
            SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.loadfailed_imagesaved), getContext());
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

        inflater.inflate(R.menu.menu_chargefailed_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.item_chargefailed_download: {

                requestPermissionSave();
                break;
            }

            case R.id.item_chargefailed_share: {

                requestPermissionShare();

                break;
            }

        }
        return true;
    }
}
