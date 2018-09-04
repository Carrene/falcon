package de.netalic.falcon.ui.addresses;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import de.netalic.falcon.R;

public class QrCodeAddressesFragment extends Fragment implements QrCodeAddressesContract.View {


    private QrCodeAddressesContract.Presenter mQrCodePresenter;
    private TextView mTextViewWalletType;
    private ImageView mImageViewQrCode;
    private Bitmap mBitmapQrCode;
    private View mRoot;
    private String mCurrencyCode;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot=inflater.inflate(R.layout.fragment_qrcodeaddresses,null);
        mBitmapQrCode = getArguments().getParcelable("qr");
        mCurrencyCode=getArguments().getString("currencyCode");
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initUiComponent();
        setImageQr();
        setWalletAddress();
    }

    @Override
    public void setPresenter(QrCodeAddressesContract.Presenter presenter) {

        mQrCodePresenter=presenter;

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static QrCodeAddressesFragment newInstance(Bitmap bitmap,String walletAddress) {

        QrCodeAddressesFragment fragment = new QrCodeAddressesFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("qr", bitmap);
        bundle.putString("currencyCode",walletAddress);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initUiComponent(){

        mTextViewWalletType=mRoot.findViewById(R.id.textview_qrcodeaddresses_wallettype);
        mImageViewQrCode=mRoot.findViewById(R.id.imageview_qrcodeaddresses_qr);
    }

    public void setImageQr() {

        mImageViewQrCode.setImageBitmap(mBitmapQrCode);
    }

    private void setWalletAddress(){

        mTextViewWalletType.setText(mCurrencyCode+" "+"wallet");
    }

}
