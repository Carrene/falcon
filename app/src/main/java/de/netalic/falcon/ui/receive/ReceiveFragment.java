package de.netalic.falcon.ui.receive;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.zxing.WriterException;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.common.ListCurrencySpinnerAdapter;
import de.netalic.falcon.data.model.Purchase;
import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.dashboard.DashboardFragment;
import de.netalic.falcon.ui.util.DecimalDigitsInputFilter;
import de.netalic.falcon.util.QrCodeUtil;
import de.netalic.falcon.util.ScreenshotUtil;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class ReceiveFragment extends Fragment implements ReceiveContract.View {

    private View mRoot;
    private ReceiveContract.Presenter mReceivePresenter;
    private static final int REQUEST_PERMISSIONS = 1;
    private static final String ALPHA_PATH = "/Alpha";
    private static final String RECEIVE_PATH = "/Receive";
    private static final int IMAGE_QUALITY = 100;
    private View mScreenShotView;
    private ImageView mImageViewGenerateQrCode;
    private Bitmap mBitmapQrCode;
    private DecimalFormat mDecimalFormat;
    private TextInputEditText mTextInputEditTextFirstAmount;
    private TextInputEditText mTextInputEditTextSecondAmount;
    private int mSelectedPosition;
    private TextInputLayout mTextInputLayoutFirstAmount;
    private Spinner mSpinnerCurrencyList;
    private Wallet mSelectedWallet;
    private List<Rate> mRateList;
    private ListCurrencySpinnerAdapter mListCurrencySpinnerAdapter;
    private double mRateCurrencySelectedWallet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_receive, null);
        mDecimalFormat = new DecimalFormat("#0.00");
        mSelectedWallet = getArguments().getParcelable(DashboardFragment.SELECTED_WALLET);
        return mRoot;
    }

    public static ReceiveFragment newInstance(Wallet selectedWallet) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(DashboardFragment.SELECTED_WALLET, selectedWallet);
        ReceiveFragment fragment = new ReceiveFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        getListCurrency();
        initUiComponent();
        mTextInputLayoutFirstAmount.setHint(mSelectedWallet.getCurrencyCode());
        initListener();
        generateQrCodeWithWalletAddress(mSelectedWallet.getAddress());
    }

    private void getListCurrency() {

        mReceivePresenter.listExchangeRate();
    }

    @Override
    public void setPresenter(ReceiveContract.Presenter presenter) {

        mReceivePresenter = presenter;
    }

    @Override
    public void showProgressBar() {

        checkNotNull(getContext());
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showMaterialDialog();
        }
    }

    @Override
    public void dismissProgressBar() {

        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).dismissMaterialDialog();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_everywhere_sharedownloadtoolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item_everywhere_share: {

                requestPermissionShare();
                break;
            }
            case R.id.item_everywhere_download: {

                requestPermissionSave();
                break;
            }
        }
        return true;
    }

    private void requestPermissionSave() {

        int checkPermission = ContextCompat.checkSelfPermission(checkNotNull(getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);

        } else {

            ScreenshotUtil.saveScreenshot(ScreenshotUtil.takeScreenshot(mScreenShotView), IMAGE_QUALITY, ALPHA_PATH, RECEIVE_PATH);
            SnackbarUtil.showSnackbar(mRoot, getString(R.string.everywhere_imagesaved), getContext());
        }

    }

    private void requestPermissionShare() {

        int checkPermission = ContextCompat.checkSelfPermission(checkNotNull(getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        } else {

            File file = new File(String.valueOf(ScreenshotUtil.saveScreenshot(ScreenshotUtil.takeScreenshot(mScreenShotView), IMAGE_QUALITY, ALPHA_PATH, RECEIVE_PATH)));
            ScreenshotUtil.shareScreenshot(file, getContext());

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

    private void initUiComponent() {

        mScreenShotView = mRoot.findViewById(R.id.relativelayout_receive_forscreenshot);
        mSpinnerCurrencyList = mRoot.findViewById(R.id.spinner_receive_spinner);
        mTextInputEditTextFirstAmount = mRoot.findViewById(R.id.edittext_receive_firstamount);
        mTextInputEditTextSecondAmount = mRoot.findViewById(R.id.edittext_receive_secondeamount);
        mTextInputLayoutFirstAmount = mRoot.findViewById(R.id.textinputlayout_receive_firstamount);
        mImageViewGenerateQrCode = mRoot.findViewById(R.id.imageview_receive_createqrcode);

    }

    private void initListener() {

        mSpinnerCurrencyList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mSelectedPosition = position;
                if (mTextInputEditTextSecondAmount.getText().toString().equals("") && mTextInputEditTextFirstAmount.getText().toString().equals("")) {


                } else if (mTextInputEditTextSecondAmount.getText().toString().equals("")) {
                    mTextInputEditTextSecondAmount.setText((String.valueOf(mDecimalFormat.format(Double.valueOf(mTextInputEditTextFirstAmount.getText().toString()) * (mRateCurrencySelectedWallet / mRateList.get(position).getBuy())))));
                } else if (mTextInputEditTextFirstAmount.getText().toString().equals("")) {

                    mTextInputEditTextFirstAmount.setText((String.valueOf(mDecimalFormat.format(Double.valueOf(mTextInputEditTextSecondAmount.getText().toString()) * (mRateList.get(position).getBuy() / mRateCurrencySelectedWallet)))));
                } else {

                    mTextInputEditTextSecondAmount.clearComposingText();
                    mTextInputEditTextSecondAmount.setText((String.valueOf(mDecimalFormat.format(Double.valueOf(mTextInputEditTextFirstAmount.getText().toString()) * (mRateCurrencySelectedWallet / mRateList.get(position).getBuy())))));

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                mSelectedPosition = 0;
            }
        });

        mTextInputEditTextSecondAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});
        mTextInputEditTextSecondAmount.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (mTextInputEditTextSecondAmount.isFocused()) {

                    if (s.toString().length() == 1 && s.toString().equals(".")) {
                        s.clear();
                    }
                    if (s.toString().equals("")) {

                        mTextInputEditTextFirstAmount.setText("");

                    } else {
                        mTextInputEditTextFirstAmount.setText(String.valueOf(mDecimalFormat.format(Double.valueOf(s.toString()) * ((mRateList.get(mSelectedPosition).getBuy()) / mRateCurrencySelectedWallet))));
                    }
                }
            }
        });

        mTextInputEditTextFirstAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});
        mTextInputEditTextFirstAmount.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (mTextInputEditTextFirstAmount.isFocused()) {

                    if (s.toString().length() == 1 && s.toString().equals(".")) {

                        s.clear();
                    }

                    if (s.toString().equals("")) {

                        mTextInputEditTextSecondAmount.setText("");

                    } else {

                        try {
                            Gson gson = new Gson();
                            Purchase purchase = new Purchase(Float.valueOf(s.toString()), mSelectedWallet.getAddress());
                            String purchaseJson = gson.toJson(purchase);

                            mBitmapQrCode = QrCodeUtil.generateQrCode(purchaseJson, 300, 300);
                            mImageViewGenerateQrCode.setImageBitmap(mBitmapQrCode);

                        } catch (WriterException e) {
                            e.printStackTrace();
                        }

                        mTextInputEditTextSecondAmount.setText(String.valueOf(mDecimalFormat.format(Double.valueOf(s.toString()) * (mRateCurrencySelectedWallet / mRateList.get(mSelectedPosition).getBuy()))));
                    }
                }
            }
        });
    }

    private void generateQrCodeWithWalletAddress(String walletAddress) {

        try {
            mBitmapQrCode = QrCodeUtil.generateQrCode(walletAddress, 300, 300);
            mImageViewGenerateQrCode.setImageBitmap(mBitmapQrCode);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private double getSelectedRate(String currencyCode) {

        for (Rate rate : mRateList) {
            if (rate.getCurrencyCode().equals(currencyCode)) {
                mRateCurrencySelectedWallet = rate.getBuy();
            }
        }
        return mRateCurrencySelectedWallet;
    }

    @Override
    public void setRateList(List<Rate> rateList) {
        mRateList = rateList;
        mListCurrencySpinnerAdapter = new ListCurrencySpinnerAdapter(getContext(), mRateList);
        mSpinnerCurrencyList.setAdapter(mListCurrencySpinnerAdapter);
        getSelectedRate(mSelectedWallet.getCurrencyCode());
    }
}
