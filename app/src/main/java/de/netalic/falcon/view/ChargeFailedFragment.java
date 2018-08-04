package de.netalic.falcon.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;

import de.netalic.falcon.R;
import de.netalic.falcon.model.Deposit;
import de.netalic.falcon.presenter.ChargeFailedContract;
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
    private TextView mTextViewTransactionAmount;
    private ImageButton mButtonShare;
    private ImageButton mButtonDownload;
    private Button mButtonNavigationDashboard;
    private static final int REQUEST_PERMISSIONS = 120;
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
        mDeposit = getArguments().getParcelable(ARGUMENT_DEPOSIT);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponent();
        setPaymentInformation();
        initListener();
        requestPermission();
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
        mTextViewTransactionAmount = mRoot.findViewById(R.id.textview_chargefailed_transactionamount);
        mButtonShare = mRoot.findViewById(R.id.imagebutton_chargefailed_sharebutton);
        mButtonDownload = mRoot.findViewById(R.id.imagebutton_chargefailed_downloadbutton);
        mButtonNavigationDashboard = mRoot.findViewById(R.id.button_chargefailed_dashborad);
        mScreenshotView = mRoot.findViewById(R.id.linearlayout_chargefailed_main);

    }

    public void setPaymentInformation() {

        mTextViewWalletName.setText(mDeposit.getWalletName());
        mTextViewChargeAmount.setText(String.valueOf(mDeposit.getChargeAmount()));
        mTextViewPaidAmount.setText(String.valueOf(mDeposit.getPaidAmount()));
        mTextViewPaymentGateway.setText(mDeposit.getPaymentGatewayName());
        mTextViewTransactionDate.setText(mDeposit.getModifiedAt());
    }

    public void initListener() {

        mButtonNavigationDashboard.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), DashboardActivity.class);
            startActivity(intent);
        });

        mButtonShare.setOnClickListener(v -> {

            File file = ScreenshotUtil.saveScreenshot(ScreenshotUtil.takeScreenshot(mScreenshotView), IMAGE_QUALITY, ALPHA_PATH + CHARGE_PATH);
            ScreenshotUtil.shareScreenshot(file, checkNotNull(getContext()));

        });

        mButtonDownload.setOnClickListener(v -> {

            ScreenshotUtil.saveScreenshot(ScreenshotUtil.takeScreenshot(mScreenshotView), IMAGE_QUALITY, ALPHA_PATH + CHARGE_PATH);
            SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.chargefailed_imagesaved),getContext());

        });

    }

    private void requestPermission() {


        int regEX = ContextCompat.checkSelfPermission(checkNotNull(getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (regEX != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(checkNotNull(getActivity()), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            SnackbarUtil.showSnackbar(mRoot, "Permission Ok", getContext());
        } else {

            SnackbarUtil.showSnackbar(mRoot, "Permission Failed", getContext());

        }
    }



}
