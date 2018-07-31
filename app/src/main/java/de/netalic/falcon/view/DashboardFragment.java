package de.netalic.falcon.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.adapter.DashboardWalletSpinnerAdapter;
import de.netalic.falcon.model.Currency;
import de.netalic.falcon.model.Rate;
import de.netalic.falcon.model.UsdCurrency;
import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.presenter.DashboardContract;
import de.netalic.falcon.util.MaterialDialogUtil;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class DashboardFragment extends Fragment implements DashboardContract.View {

    private DashboardContract.Presenter mPresenter;
    private View mRoot;
    private Spinner mSpinnerWalletList;
    private static final String ARGUMENT_USER = "USER";
    private TextView mTextViewRate;
    private Currency mUsd;
    private Rate mRate;
    private TextView mTextViewBalance;
    private List<Wallet> mWalletList;
    private DashboardWalletSpinnerAdapter mDashboardWalletSpinnerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_dashboard, null);
        setHasOptionsMenu(true);
        mUsd = new UsdCurrency();
        mRate = new Rate(mUsd);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponents();
        getRate();
        getWalletList();
        initListener();

    }

    public static DashboardFragment newInstance() {

        DashboardFragment dashboardFragment = new DashboardFragment();
        return dashboardFragment;
    }

    @Override
    public void onResume() {

        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(DashboardContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_dashboard_toolbar, menu);
    }

    public void initUiComponents() {

        mTextViewRate = mRoot.findViewById(R.id.textview_dashboard_ratecurrency);
        mSpinnerWalletList = mRoot.findViewById(R.id.spinner_dashboard_spinner);
        mTextViewBalance = mRoot.findViewById(R.id.textview_dashboard_balance);
    }

    @Override
    public void showErrorInvalidCurrency() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.dashboard_invalidcurrency), getContext());
    }

    @Override
    public void showErrorRatesDoesNotExists() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.dashboard_ratedosenotexists), getContext());
    }

    @Override
    public void updateExchangeRateCurrency(Rate rate) {

        mRate = rate;
        mTextViewRate.setText(String.valueOf(rate));
    }

    @Override
    public void showProgressBar() {

        checkNotNull(getContext());
        MaterialDialogUtil.getInstance().showMaterialDialog(getContext());
    }

    @Override
    public void dismissProgressBar() {

        MaterialDialogUtil.getInstance().dismissMaterialDialog();
    }


    @Override
    public void setListWallet(List<Wallet> walletList) {

        mWalletList = walletList;
        mDashboardWalletSpinnerAdapter = new DashboardWalletSpinnerAdapter(getContext(), mWalletList);
        mSpinnerWalletList.setAdapter(mDashboardWalletSpinnerAdapter);
    }

    public void getRate() {

        mPresenter.exchangeRate(mRate);

    }

    public void getWalletList() {

        mPresenter.getWalletList();
    }

    public void initListener() {

        mSpinnerWalletList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mTextViewBalance.setText(String.valueOf(mWalletList.get(position).getBalance()));
                mTextViewRate.setText("" + mWalletList.get(position).getBalance() * Double.parseDouble(mRate.getSell()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenshot();
            }
        });
    }

    private void takeScreenshot() {
        try {

//            Date now = new Date();
//            android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
            mRoot.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(mRoot.getDrawingCache());
            mRoot.setDrawingCacheEnabled(false);

            String mPath = getActivity().getFilesDir().getAbsolutePath() + "/" + "ehsan" + ".jpg";

            File imageFile = new File(getContext().getCacheDir(), "images");
            imageFile.createNewFile();




            File newFile = new File(imageFile, "image.png");



            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            shareIt(newFile);



        } catch (Throwable e) {
            e.printStackTrace();
        }


    }

    public void shareIt(File file) {
        boolean isRead = file.canRead();

//        File file1=file;
//        Intent shareIntent = new Intent();
//        shareIntent.setAction(Intent.ACTION_SEND);
//        shareIntent.setType("image/jpg");
//        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
//        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        startActivityForResult(Intent.createChooser(shareIntent, "Share to"),1);




        Uri contentUri = FileProvider.getUriForFile(getContext(), "de.netalic.falcon.fileprovider", file);

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri,getActivity().getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            startActivity(Intent.createChooser(shareIntent, "Choose an app"));
        }


    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }
}
