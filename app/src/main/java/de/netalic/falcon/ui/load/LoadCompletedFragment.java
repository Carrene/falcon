package de.netalic.falcon.ui.load;

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
import de.netalic.falcon.data.model.Receipt;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.util.ScreenshotUtil;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoadCompletedFragment extends Fragment implements LoadCompletedContract.View {

    private static final String ARGUMENT_RECEIPT = "receipt";
    private static final String ALPHA_PATH = "/Alpha";
    private static final String CHARGE_PATH = "/Charge";
    private Receipt mReceipt;
    private LoadCompletedContract.Presenter mChargeCompletedPresenter;
    private View mRoot;
    private TextView mTextViewWalletName;
    private TextView mTextViewAmountWallet;
    private TextView mTextViewAmountBase;
    private TextView mTextViewPaymentGateway;
    private TextView mTextViewTransactionDate;
    private TextView mTextViewTransactionTime;
    private TextView mTextViewRrn;
    private TextView mTextViewLoadAmountSymbol;
    private TextView mTextViewPaidAmountSymbol;
    private Button mButtonNavigationToDashboard;
    private String mLoadSymbol;
    private String mPaidSymbol;
    private static final int REQUEST_PERMISSIONS = 1;
    private static final int IMAGE_QUALITY = 100;
    private View mScreenshotView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_loadcompleted, null);
        checkNotNull(getArguments());
        setHasOptionsMenu(true);
        mReceipt = getArguments().getParcelable(ARGUMENT_RECEIPT);
        mLoadSymbol = getArguments().getString(LoadConfirmationFragment.LOAD_AMOUNT);
        mPaidSymbol = getArguments().getString(LoadConfirmationFragment.PAID_AMOUNT);
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
    public void setPresenter(LoadCompletedContract.Presenter presenter) {

        mChargeCompletedPresenter = presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static LoadCompletedFragment newInstance(Receipt receipt, String loadSymbol, String paidSymbol) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_RECEIPT, receipt);
        bundle.putString(LoadConfirmationFragment.LOAD_AMOUNT, loadSymbol);
        bundle.putString(LoadConfirmationFragment.PAID_AMOUNT, paidSymbol);
        LoadCompletedFragment fragment = new LoadCompletedFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void initUiComponent() {

        mTextViewWalletName = mRoot.findViewById(R.id.textview_chargecompleted_walletname);
        mTextViewAmountWallet = mRoot.findViewById(R.id.textview_chargecompleted_amountalpha);
        mTextViewAmountBase = mRoot.findViewById(R.id.textview_chargecompleted_amountdollar);
        mTextViewPaymentGateway = mRoot.findViewById(R.id.textview_chargecompleted_paymentgateway);
        mTextViewTransactionDate = mRoot.findViewById(R.id.textview_chargecompleted_transactiondate);
        mTextViewTransactionTime = mRoot.findViewById(R.id.textview_chargecompleted_transactiontime);
        mTextViewRrn = mRoot.findViewById(R.id.textview_chargecompleted_rrn);
        mButtonNavigationToDashboard = mRoot.findViewById(R.id.button_chargecompleted_dashborad);
        mScreenshotView = mRoot.findViewById(R.id.linearlayout_chargecompleted_main);
        mTextViewLoadAmountSymbol = mRoot.findViewById(R.id.textview_loadcompleted_loadamountsymbol);
        mTextViewPaidAmountSymbol = mRoot.findViewById(R.id.textview_loadcompleted_paidamountsymbol);
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
        mTextViewAmountWallet.setText(String.valueOf(mReceipt.getQuoteAmount()));
        mTextViewAmountBase.setText(String.valueOf(Math.abs(mReceipt.getBaseAmount())));
        mTextViewPaymentGateway.setText(mReceipt.getPaymentGatewayName());
        mTextViewTransactionDate.setText(mReceipt.getDate());
        mTextViewTransactionTime.setText(mReceipt.getTime());
        mTextViewLoadAmountSymbol.setText(mLoadSymbol);
        mTextViewPaidAmountSymbol.setText(mPaidSymbol);
        mTextViewRrn.setText(mReceipt.getRetrievalReferenceNumber());
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