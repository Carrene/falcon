package de.netalic.falcon.data.repository.authentication;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import de.netalic.falcon.InsensitiveDatabase;
import de.netalic.falcon.data.model.Authentication;
import de.netalic.falcon.data.repository.base.Deal;

public class AuthenticationRealmRepository implements IAuthenticationRepository {

    private InsensitiveDatabase mInsensitiveDatabase;
    private Context mContext;

    public AuthenticationRealmRepository(Context context) {

        mContext=context;
    }

    @Override
    public void update(Authentication authentication, CallRepository<Authentication> callRepository) {


        AsyncTask.execute(() -> {

            mInsensitiveDatabase.authenticationDao().updateAuthentication(authentication);
            callRepository.onDone(new Deal<>(authentication,null,null));
        });
    }

    @Override
    public void insert(Authentication authentication, CallRepository<Authentication> callRepository) {

        AsyncTask.execute(() -> {

            mInsensitiveDatabase =InsensitiveDatabase.getInsensitiveDatabase(mContext);
            mInsensitiveDatabase.authenticationDao().insertAuthentication(authentication);

            callRepository.onDone(new Deal<>(authentication,null,null));
        });
    }

    @Override
    public void get(Integer identifier, CallRepository<Authentication> callRepository) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void getAll(CallRepository<List<Authentication>> callRepository) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void get(CallRepository<Authentication> callRepository) {


        AsyncTask.execute(() -> {

            mInsensitiveDatabase =InsensitiveDatabase.getInsensitiveDatabase(mContext);
            Authentication authentication= mInsensitiveDatabase.authenticationDao().findById(1);
            Deal deal;
            if (authentication==null){
                deal=new Deal<>(null,null,null);

            }
            else {

                deal=new Deal<>(authentication,null,null);
            }
            callRepository.onDone(deal);


        });
    }
}