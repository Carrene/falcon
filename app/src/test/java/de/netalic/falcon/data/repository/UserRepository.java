package de.netalic.falcon.data.repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import de.netalic.falcon.data.model.User;
import de.netalic.falcon.data.repository.base.IRepository;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.user.IUserRepository;

public class UserRepository {

    @Mock
    private IUserRepository mUserRepositoryRemote;

    @Mock
    private IUserRepository mUserRepositoryLocal;

    @Mock
    private IRepository.CallRepository<User> mCallRepository;

    @Mock
    private de.netalic.falcon.data.repository.user.UserRepository mUserRepository;


    @Captor
    private ArgumentCaptor<IRepository.CallRepository> mRepositoryCallbackCaptor;

    @Before
    public void setupUserRepository() {

        MockitoAnnotations.initMocks(this);
        RepositoryLocator.getInstance().setRepository(new de.netalic.falcon.data.repository.user.UserRepository(mUserRepositoryRemote, mUserRepositoryLocal));
        mUserRepository = RepositoryLocator.getInstance().getRepository(de.netalic.falcon.data.repository.user.UserRepository.class);
    }

    @Test
    public void getUser_fromLocal() {

        mUserRepository.get(1, mCallRepository);
        Mockito.verify(mUserRepositoryLocal).get(Mockito.eq(1), Mockito.any(IRepository.CallRepository.class));
    }
}
