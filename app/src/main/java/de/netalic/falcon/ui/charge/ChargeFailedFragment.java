package de.netalic.falcon.ui.charge;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.netalic.falcon.R;
import de.netalic.falcon.model.Deposit;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.util.ScreenshotUtil;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChargeFailedFragment extends Fragment implements ChargeFailedContract.View {

    private ChargeFailedContract.Presenter mChargeFailedPresenter;
    private static final String ALPHA_PATH = "/Alpha";
    private static final String CHARGE_PATH = "/Charge";
    private View mRoot;
    private static final String ARGUMENT_DEPOSIT = "DEPOSIT";
    private Deposit mDeposit;
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

    @Override
    public void setPresenter(ChargeFailedContract.Presenter presenter) {

        mChargeFailedPresenter = checkNotNull(presenter);
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


        mRoot = inflater.inflate(R.layout.fragment_chargefailed, null);
        checkNotNull(getArguments());
        setHasOptionsMenu(true);
        mDeposit = getArguments().getParcelable(ARGUMENT_DEPOSIT);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponent();
        setPaymentInformation();
        initListener();
    }

    public static ChargeFailedFragment newInstance(Deposit deposit) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_DEPOSIT, deposit);
        ChargeFailedFragment fragment = new ChargeFailedFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void initUiComponent() {

        mTextViewWalletName = mRoot.findViewById(R.id.textview_chargefailed_walletname);
        mTextViewChargeAmount = mRoot.findViewById(R.id.textview_chargefailed_amountalpha);
        mTextViewPaidAmount = mRoot.findViewById(R.id.textview_chargefailed_amountdollar);
        mTextViewPaymentGateway = mRoot.findViewById(R.id.textview_chargefailed_paymentgateway);
        mTextViewTransactionDate = mRoot.findViewById(R.id.textview_chargefailed_transactiondate);
        mTextViewTransactionTime = mRoot.findViewById(R.id.textview_chargefailed_transactiontime);
        mTextViewRrn = mRoot.findViewById(R.id.textview_chargefailed_rrn);
        mButtonNavigationDashboard = mRoot.findViewById(R.id.button_chargefailed_dashborad);
        mScreenshotView = mRoot.findViewById(R.id.linearlayout_chargefailed_main);

    }

    public void setPaymentInformation() {

        mTextViewWalletName.setText(mDeposit.getWalletName());
        mTextViewChargeAmount.setText(String.valueOf(mDeposit.getChargeAmount()));
        mTextViewPaidAmount.setText(String.valueOf(mDeposit.getPaidAmount()));
        mTextViewPaymentGateway.setText(mDeposit.getPaymentGatewayName());

        try {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
            Date baseDate = dateFormat.parse(mDeposit.getModifiedAt());
            DateFormat date = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat time = new SimpleDateFormat("h:mm a");
            mTextViewTransactionDate.setText(date.format(baseDate));
            mTextViewTransactionTime.setText(time.format(baseDate));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        mTextViewRrn.setText(mDeposit.getRetrievalReferenceNumber());
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

            requestPermissions( new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
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
            SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.chargefailed_imagesaved), getContext());
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
