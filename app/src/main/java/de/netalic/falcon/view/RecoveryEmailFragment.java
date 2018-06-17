package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import br.com.sapereaude.maskedEditText.MaskedEditText;
import de.netalic.falcon.R;
import de.netalic.falcon.presenter.RecoveryEmailContract;

public class RecoveryEmailFragment extends Fragment implements RecoveryEmailContract.View {

    private View mRoot;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_recoveryemail, container, false);
        setHasOptionsMenu(true);
        return mRoot;
    }

    public static RecoveryEmailFragment newInstance() {

        return new RecoveryEmailFragment();
    }

    @Override
    public void setPresenter(RecoveryEmailContract.Presenter presenter) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_recoveryemail_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.menu_recoveryemail_done: {


            }
        }
        return true;
    }
}
