package de.netalic.falcon.ui.authentication.registration;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.netalic.falcon.data.repository.UserRepository;
import de.netalic.falcon.data.repository.base.IRepository;

import static org.mockito.Mockito.verify;

public class RegistrationPresenterTest {

    @Mock
    private UserRepository mUserRepository;

    @Mock
    private RegistrationContract.View mRegistrationView;

    private RegistrationPresenter mRegistrationPresenter;

    @Captor
    private ArgumentCaptor<IRepository.CallRepository> mRepositoryCall;

    @Before
    public void setupStatisticsPresenter() {

        MockitoAnnotations.initMocks(this);
        mRegistrationPresenter = new RegistrationPresenter(mRegistrationView);
    }

    @Test
    public void createPresenter_setsThePresenterToView() {

        mRegistrationPresenter = new RegistrationPresenter(mRegistrationView);
        verify(mRegistrationView).setPresenter(mRegistrationPresenter);
    }


}
