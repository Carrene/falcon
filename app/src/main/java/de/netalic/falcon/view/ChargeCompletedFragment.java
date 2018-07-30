package de.netalic.falcon.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import de.netalic.falcon.R;
import de.netalic.falcon.model.Deposit;
import de.netalic.falcon.presenter.ChargeCompletedContract;

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

            Uri imageUri;

            imageUri = Uri.parse("android.resource://" + getActivity().getPackageName()
                    + "/drawable/" + "icon_alpha");

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, true);
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            shareIntent.setType("image/jpeg");
            startActivity(Intent.createChooser(shareIntent, "Share to"));


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


}
