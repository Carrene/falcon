package de.netalic.falcon.ui.send;

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

public class SendCompletedFragment extends Fragment implements SendCompletedContract.View {


    private static final String ALPHA_PATH = "/Alpha";
    private static final String SEND_PATH = "/Send";
    private SendCompletedContract.Presenter mSendCompletedPresenter;
    private View mRoot;
    private TextView mTextViewWalletName;
    private TextView mTextViewPayeeAddress;
    private TextView mTextViewPaidAmount;
    private TextView mTextViewReceivedAmount;
    private TextView mTextViewTransactionFee;
    private TextView mTextViewTotalPaidAmount;
    private TextView mTextViewPaidAmountSymbol;
    private TextView mTextViewReceivedAmountSymbol;
    private TextView mTextViewTransactionFeeSymbol;
    private TextView mTextViewTotalPaidAmountSymbol;
    private TextView mTextViewTransactionDate;
    private TextView mTextViewTransactionTime;
    private TextView mTextViewTransactionId;
    private Button mButtonNavigationToDashboard;
    private static final int REQUEST_PERMISSIONS = 1;
    private static final int IMAGE_QUALITY = 100;
    private View mScreenshotView;
    private Transaction mTransaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_sendcompleted, null);
        checkNotNull(getArguments());
        mTransaction = getArguments().getParcelable(SendFragment.ARGUMENT_TRANSACTION);
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
    public void setPresenter(SendCompletedContract.Presenter presenter) {

        mSendCompletedPresenter = presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static SendCompletedFragment newInstance(Transaction transaction) {

        SendCompletedFragment sendCompletedFragment = new SendCompletedFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(SendFragment.ARGUMENT_TRANSACTION, transaction);
        sendCompletedFragment.setArguments(bundle);
        return sendCompletedFragment;
    }

    public void initUiComponent() {

        mTextViewWalletName = mRoot.findViewById(R.id.textview_transfercompleted_walletname);
        mTextViewPayeeAddress = mRoot.findViewById(R.id.textview_transfercompleted_payeeaddress);
        mTextViewPaidAmount = mRoot.findViewById(R.id.textview_transfercompleted_paidamount);
        mTextViewTotalPaidAmount = mRoot.findViewById(R.id.textview_transfercompleted_totalpaidamount);
        mTextViewReceivedAmount = mRoot.findViewById(R.id.textview_transfercompleted_receivedamount);
        mTextViewTransactionFee = mRoot.findViewById(R.id.textview_transfercompleted_transactionfee);
        mTextViewTransactionDate = mRoot.findViewById(R.id.textview_transfercompleted_transactiondate);
        mTextViewTransactionTime = mRoot.findViewById(R.id.textview_transfercompleted_transactiontime);
        mTextViewTransactionId = mRoot.findViewById(R.id.textview_transfercompleted_rrn);

        mTextViewPaidAmountSymbol = mRoot.findViewById(R.id.textview_transfercompleted_paidamountsymbol);
        mTextViewReceivedAmountSymbol = mRoot.findViewById(R.id.textview_transfercompleted_receivedamountsymbol);
        mTextViewTransactionFeeSymbol = mRoot.findViewById(R.id.textview_transfercompleted_transactionfeesymbol);
        mTextViewTotalPaidAmountSymbol = mRoot.findViewById(R.id.textview_transfercompleted_totalpaidamountsymbol);

        mButtonNavigationToDashboard = mRoot.findViewById(R.id.button_transfercompleted_dashborad);
        mScreenshotView = mRoot.findViewById(R.id.linearlayout_transfercompleted_main);
    }

    public void initListener() {

        mButtonNavigationToDashboard.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

    }

    public void setTransactionInformation() {

        mTextViewWalletName.setText(mTransaction.getActionList().get(0).getWalletName());
        mTextViewTransactionId.setText(mTransaction.getRetrievalReferenceNumber());
        mTextViewTransactionDate.setText(mTransaction.getDate());
        mTextViewTransactionTime.setText(mTransaction.getTime());

        mTextViewPayeeAddress.setText(mTransaction.getActionList().get(1).getWalletAddress());
        mTextViewPaidAmount.setText(String.valueOf(Math.abs(mTransaction.getActionList().get(0).getAmount())));
       // mTextViewTotalPaidAmount.setText(String.valueOf(Math.abs(mTransaction.getActionList().get(1).getAmount())));
        mTextViewReceivedAmount.setText(String.valueOf(Math.abs(mTransaction.getActionList().get(1).getAmount())));
       // mTextViewTransactionFee.setText(String.valueOf(Math.abs(mTransaction.getActionList().get(1).getAmount()) - mTransaction.getActionList().get(0).getAmount()));

        mTextViewPaidAmountSymbol.setText(mTransaction.getActionList().get(0).getCurrencySymbol());
        mTextViewReceivedAmountSymbol.setText(mTransaction.getActionList().get(1).getCurrencySymbol());
        mTextViewTransactionFeeSymbol.setText(mTransaction.getActionList().get(0).getCurrencySymbol());
        mTextViewTotalPaidAmountSymbol.setText(mTransaction.getActionList().get(0).getCurrencySymbol());
    }

    private void requestPermissionShare() {

        int checkPermission = ContextCompat.checkSelfPermission(checkNotNull(getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        } else {

            File file = new File(String.valueOf(ScreenshotUtil.saveScreenshot("Send",ScreenshotUtil.takeScreenshot(mScreenshotView), IMAGE_QUALITY, ALPHA_PATH + SEND_PATH)));
            ScreenshotUtil.shareScreenshot(file, checkNotNull(getContext()));

        }
    }

    private void requestPermissionSave() {

        int checkPermission = ContextCompat.checkSelfPermission(checkNotNull(getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        } else {

            ScreenshotUtil.saveScreenshot("Send",ScreenshotUtil.takeScreenshot(mScreenshotView), IMAGE_QUALITY, ALPHA_PATH + SEND_PATH);
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
