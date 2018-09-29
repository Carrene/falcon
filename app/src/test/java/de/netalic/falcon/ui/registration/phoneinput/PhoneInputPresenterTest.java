package de.netalic.falcon.ui.registration.phoneinput;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.netalic.falcon.data.repository.UserRepository;
import de.netalic.falcon.data.repository.base.IRepository;

import static org.mockito.Mockito.verify;

public class PhoneInputPresenterTest {

    @Mock
    private UserRepository mUserRepository;

    @Mock
    private PhoneInputContract.View mRegistrationView;

    private PhoneInputPresenter mPhoneInputPresenter;

    @Captor
    private ArgumentCaptor<IRepository.CallRepository> mRepositoryCall;

    @Before
    public void setupStatisticsPresenter() {

        MockitoAnnotations.initMocks(this);
        mPhoneInputPresenter = new PhoneInputPresenter(mRegistrationView);
    }

    @Test
    public void createPresenter_setsThePresenterToView() {

        mPhoneInputPresenter = new PhoneInputPresenter(mRegistrationView);
        verify(mRegistrationView).setPresenter(mPhoneInputPresenter);
    }


}
