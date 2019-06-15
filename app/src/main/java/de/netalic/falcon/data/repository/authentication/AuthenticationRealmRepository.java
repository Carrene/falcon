package de.netalic.falcon.data.repository.authentication;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import de.AppDatabase;
import de.netalic.falcon.data.model.Authentication;
import de.netalic.falcon.data.repository.base.Deal;

public class AuthenticationRealmRepository implements IAuthenticationRepository {

    private AppDatabase mAppDatabase;
    private Context mContext;

    public AuthenticationRealmRepository(Context context) {

        mContext=context;
    }

    @Override
    public void update(Authentication authentication, CallRepository<Authentication> callRepository) {


        AsyncTask.execute(() -> {

            mAppDatabase.authenticationDao().updateAuthentication(authentication);

            callRepository.onDone(new Deal<>(authentication,null,null));
        });

//
//
//        mRealm = Realm.getInstance(MyApp.sInsensitiveRealmConfiguration.build());
//        mRealm.beginTransaction();
//        mRealm.copyToRealmOrUpdate(authentication);
//        mRealm.commitTransaction();
//        mRealm.close();
//
//        try {
//            MyApp.sSensitiveRealmConfiguration.encryptionKey(Converter.hexStringToBytes(authentication.getCredential())).build();
//        } catch (Exception e) {
//            e.printStackTrace();
//            callRepository.onDone(new Deal<>(null, null, e));
//        }
//
//        callRepository.onDone(new Deal<>(authentication, null, null));
    }

    @Override
    public void insert(Authentication authentication, CallRepository<Authentication> callRepository) {

        AsyncTask.execute(() -> {

            mAppDatabase =AppDatabase.getAppDatabase(mContext);
            mAppDatabase.authenticationDao().insertAuthentication(authentication);

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

            mAppDatabase =AppDatabase.getAppDatabase(mContext);
            Authentication authentication= mAppDatabase.authenticationDao().findById(1);
            Deal deal;
            if (authentication==null){
                deal=new Deal<>(null,null,null);

            }
            else {

                deal=new Deal<>(authentication,null,null);
            }
            callRepository.onDone(deal);

        });

//        mRealm = Realm.getInstance(MyApp.sInsensitiveRealmConfiguration.build());
//        Authentication authentication1 = mRealm.where(Authentication.class).equalTo("mId", 1).findFirst();
//        Deal deal;
//        if (authentication1 == null) {
//            deal = new Deal<>(null, null, null);
//        } else {
//            Authentication returnAuthentication = mRealm.copyFromRealm(authentication1);
//            deal = new Deal<>(returnAuthentication, null, null);
//        }
//        mRealm.close();
//        callRepository.onDone(deal);

    }

}