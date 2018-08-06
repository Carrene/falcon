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
import de.netalic.falcon.presenter.ChargeCompletedContract;
import de.netalic.falcon.util.ScreenshotUtil;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChargeCompletedFragment extends Fragment implements ChargeCompletedContract.View {

    private static final String ARGUMENT_DEPOSIT = "DEPOSIT";
    private static final String ALPHA_PATH = "/Alpha";
    private static final String CHARGE_PATH = "/Charge";
    private Deposit mDeposit;
    private ChargeCompletedContract.Presenter mChargeCompletedPresenter;
    private View mRoot;
    private TextView mTextViewWalletName;
    private TextView mTextViewAmountWallet;
    private TextView mTextViewAmountBase;
    private TextView mTextViewPaymentGateway;
    private TextView mTextViewTransactionDate;
    private TextView mTextViewTrackingCode;
    private TextView mTextViewTransactionAmount;
    private ImageButton mButtonShare;
    private ImageButton mButtonDownload;
    private Button mButtonNavigationToDashboard;
    private static final int REQUEST_PERMISSIONS = 120;
    private static final int IMAGE_QUALITY = 100;
    private View mScreenshotView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_chargecompleted, null);
        checkNotNull(getArguments());
        mDeposit = getArguments().getParcelable(ARGUMENT_DEPOSIT);

        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponent();
        initListener();
        setPaymentInformation();
        requestPermission();
    }

    @Override
    public void setPresenter(ChargeCompletedContract.Presenter presenter) {

        mChargeCompletedPresenter = presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static ChargeCompletedFragment newInstance(Deposit deposit) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_DEPOSIT, deposit);
        ChargeCompletedFragment fragment = new ChargeCompletedFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void initUiComponent() {

        mTextViewWalletName = mRoot.findViewById(R.id.textview_chargecompleted_walletname);
        mTextViewAmountWallet = mRoot.findViewById(R.id.textview_chargecompleted_amountalpha);
        mTextViewAmountBase = mRoot.findViewById(R.id.textview_chargecompleted_amountdollar);
        mTextViewPaymentGateway = mRoot.findViewById(R.id.textview_chargecompleted_paymentgateway);
        mTextViewTransactionDate = mRoot.findViewById(R.id.textview_chargecompleted_transactiondate);
        mTextViewTrackingCode = mRoot.findViewById(R.id.textview_chargecompleted_trackingcode);
        mTextViewTransactionAmount = mRoot.findViewById(R.id.textview_chargecompleted_transactionamount);
        mButtonShare = mRoot.findViewById(R.id.imagebutton_chargecompleted_sharebutton);
        mButtonDownload = mRoot.findViewById(R.id.imagebutton_chargecompleted_downloadbutton);
        mButtonNavigationToDashboard = mRoot.findViewById(R.id.button_chargecompleted_dashborad);
        mScreenshotView = mRoot.findViewById(R.id.linearlayout_chargecompleted_main);
    }

    public void initListener() {

        mButtonNavigationToDashboard.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        mButtonShare.setOnClickListener(v -> {

            File file = ScreenshotUtil.saveScreenshot(ScreenshotUtil.takeScreenshot(mScreenshotView), IMAGE_QUALITY, ALPHA_PATH + CHARGE_PATH);
            ScreenshotUtil.shareScreenshot(file, checkNotNull(getContext()));

        });

        mButtonDownload.setOnClickListener(v -> {

            ScreenshotUtil.saveScreenshot(ScreenshotUtil.takeScreenshot(mScreenshotView), IMAGE_QUALITY, ALPHA_PATH + CHARGE_PATH);
            SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.chargecompleted_imagesaved),getContext());

        });
    }


    public void setPaymentInformation() {

        mTextViewWalletName.setText(mDeposit.getWalletName());
        mTextViewAmountWallet.setText(String.valueOf(mDeposit.getChargeAmount()));
        mTextViewAmountBase.setText(String.valueOf(mDeposit.getPaidAmount()));
        mTextViewPaymentGateway.setText(mDeposit.getPaymentGatewayName());
        mTextViewTransactionDate.setText(mDeposit.getModifiedAt());
        mTextViewTrackingCode.setText(mDeposit.getRetrievalReferenceNumber());
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
