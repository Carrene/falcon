package de.netalic.falcon.ui.transaction.transactionhistory;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Receipt;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.util.ScreenshotUtil;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransactionDetailCompletedFragment extends Fragment implements TransactionDetailCompletedContract.View {

    public static final String ARGUMENT_RECEIPT = "receipt";
    private static final String ALPHA_PATH = "/Alpha";
    private static final String CHARGE_PATH = "/Transaction Detail";
    private Receipt mReceipt;
    private View mRoot;
    private TextView mTextViewWalletName;
    private TextView mTextViewPaidAmount;
    private TextView mTextViewDestinationwalletaddress;
    private TextView mTextViewPaymentGateway;
    private TextView mTextViewTransactionDate;
    private TextView mTextViewTransactionTime;
    private TextView mTextViewRrn;
    private Button mButtonNavigationToDashboard;
    private static final int REQUEST_PERMISSIONS = 1;
    private static final int IMAGE_QUALITY = 100;
    private View mScreenshotView;
    private String mTransactionType;
    private RelativeLayout mRelativeLayoutRrnBox;
    private View mViewRrnLine;
    private TextView mTextViewTypeOfTransaction;
    private TextView mTextViewTypeOfTransactionAddress;
    private TextView mTextViewTransactionId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_transactiondetail, null);
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
        initListener();
        setPaymentInformation();
    }

    @Override
    public void setPresenter(TransactionDetailCompletedContract.Presenter presenter) {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static TransactionDetailCompletedFragment newInstance(Receipt receipt, String transactionType) {

        Bundle bundle = new Bundle();
        bundle.putString(TransactionHistoryRecyclerViewAdapter.TRANSACTION_TYPE, transactionType);
        bundle.putParcelable(ARGUMENT_RECEIPT, receipt);
        TransactionDetailCompletedFragment fragment = new TransactionDetailCompletedFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void initUiComponent() {

        mTextViewWalletName = mRoot.findViewById(R.id.textview_transactiondetail_walletname);
        mTextViewPaidAmount = mRoot.findViewById(R.id.textview_transactiondetail_paidamount);
        mTextViewDestinationwalletaddress = mRoot.findViewById(R.id.textview_transactiondetail_destinationwalletaddress);
        mTextViewPaymentGateway = mRoot.findViewById(R.id.textview_transactiondetail_paymentgateway);
        mTextViewTransactionDate = mRoot.findViewById(R.id.textview_transactiondetail_transactiondate);
        mTextViewTransactionTime = mRoot.findViewById(R.id.textview_transactiondetail_transactiontime);
        mTextViewRrn = mRoot.findViewById(R.id.textview_transactiondetail_rrn);
        mButtonNavigationToDashboard = mRoot.findViewById(R.id.button_transactiondetail_dashborad);
        mScreenshotView = mRoot.findViewById(R.id.linearlayout_transactiondetail_main);
        mRelativeLayoutRrnBox = mRoot.findViewById(R.id.relativelayout_transactiondetailcompleted_rrnbox);
        mViewRrnLine = mRoot.findViewById(R.id.view_transactiondetailcompleted_rrnlineview);
        mTextViewTypeOfTransaction=mRoot.findViewById(R.id.textview_transactiondetail_typeoftransaction);
        mTextViewTypeOfTransactionAddress=mRoot.findViewById(R.id.textview_transactiondetail_typeoftransactionaddress);
        mTextViewTransactionId=mRoot.findViewById(R.id.textview_transactiondetail_transactionid);

        if (mTransactionType.equals(getContext().getString(R.string.transactiondetail_forchecktransferorcharge))) {

            mRelativeLayoutRrnBox.setVisibility(mRoot.GONE);
            mViewRrnLine.setVisibility(mRoot.GONE);
        }
    }

    public void initListener() {

        mButtonNavigationToDashboard.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    public void setPaymentInformation() {

        mTextViewWalletName.setText(mReceipt.getRecipientWalletName());
        mTextViewPaidAmount.setText(mReceipt.getQuoteCurrencySymbol() + " " + String.valueOf(mReceipt.getQuoteAmount()));
        mTextViewDestinationwalletaddress.setText(mReceipt.getRecipientWalletAddress());
        mTextViewPaymentGateway.setText(mReceipt.getPaymentGatewayName());
        mTextViewTransactionDate.setText(mReceipt.getDate());
        mTextViewTransactionTime.setText(mReceipt.getTime());
        mTextViewRrn.setText(mReceipt.getRetrievalReferenceNumber());
        mTextViewTransactionId.setText(mReceipt.getRetrievalReferenceNumber());
        mTextViewTypeOfTransaction.setText(mTransactionType);
        mTextViewTypeOfTransactionAddress.setText(mTransactionType);
    }

    private void requestPermissionShare() {

        int checkPermission = ContextCompat.checkSelfPermission(checkNotNull(getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        } else {

            File file = new File(String.valueOf(ScreenshotUtil.saveScreenshot(ScreenshotUtil.takeScreenshot(mScreenshotView), IMAGE_QUALITY, ALPHA_PATH + CHARGE_PATH)));
            ScreenshotUtil.shareScreenshot(file, checkNotNull(getContext()));
        }
    }

    private void requestPermissionSave() {


        int checkPermission = ContextCompat.checkSelfPermission(checkNotNull(getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        } else {

            ScreenshotUtil.saveScreenshot(ScreenshotUtil.takeScreenshot(mScreenshotView), IMAGE_QUALITY, ALPHA_PATH + CHARGE_PATH);
            SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transactiondetail_imagesaved), getContext());
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

        inflater.inflate(R.menu.menu_chargetransfercompleted_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item_chargetransfercompletedmenu_download: {

                requestPermissionSave();
                break;
            }

            case R.id.item_chargetransfercompletedmenu_share: {

                requestPermissionShare();
                break;
            }
        }
        return true;
    }
}