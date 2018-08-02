package de.netalic.falcon.view;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaBrowserCompatUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import de.netalic.falcon.R;
import de.netalic.falcon.model.Deposit;
import de.netalic.falcon.presenter.ChargeCompletedContract;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChargeCompletedFragment extends Fragment implements ChargeCompletedContract.View {

    private static final String ARGUMENT_DEPOSIT = "DEPOSIT";
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
    }

    public void initListener() {

        mButtonNavigationToDashboard.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), DashboardActivity.class);
            startActivity(intent);
        });

        mButtonShare.setOnClickListener(v -> {


        });

        mButtonDownload.setOnClickListener(v -> {

            requestPermission();
            saveImage(takeScreenshot());

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


    public Bitmap takeScreenshot() {

        mRoot.setDrawingCacheEnabled(true);
        return mRoot.getDrawingCache();
    }


    private void requestPermission() {


        int regEX = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (regEX != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            SnackbarUtil.showSnackbar(mRoot, "permission ok", getContext());
        } else {

            SnackbarUtil.showSnackbar(mRoot, "permission failed", getContext());

        }
    }


    public void saveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        File myDir = new File(root + "/Alpha");
        myDir.mkdirs();
        String fileName = "Image-" + now + ".PNG";
        File file = new File(myDir, fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
