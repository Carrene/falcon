package de.netalic.falcon;

import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import de.netalic.falcon.data.model.Receipt;
import de.netalic.falcon.data.remote.ApiClient;
import de.netalic.falcon.data.repository.base.Deal;
import de.netalic.falcon.data.repository.base.IRepository;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.receipt.ReceiptRepository;
import de.netalic.falcon.util.FileUtil;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(AndroidJUnit4.class)
public class ReceiptRepositoryAndroidTest {

    private static MockWebServer sMockWebServer;

    @Before
    public void setupServer() {

        sMockWebServer = new MockWebServer();

        try {
            sMockWebServer.start(8586);
            ApiClient.setService(ApiClient.getMockUpClient("http://" + sMockWebServer.getHostName() + ":" + sMockWebServer.getPort()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @After
    public void shutdownServer() {

        try {
            sMockWebServer.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getReceiptsTest() {

        String json = null;
        try {
            json = FileUtil.readTextFromStream(getClass().getResourceAsStream("transaction_history.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        sMockWebServer.enqueue(new MockResponse().setBody(json).setResponseCode(200));

        CountDownLatch countDownLatch = new CountDownLatch(1);
        ReceiptRepository receiptRepository = RepositoryLocator.getInstance().getRepository(ReceiptRepository.class);
        receiptRepository.getAll(new IRepository.CallRepository<List<Receipt>>() {
            @Override
            public void onDone(Deal<List<Receipt>> deal) {

                System.out.println("da");
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void updateReceiptsTest() {

        String json = null;
        try {
            json = FileUtil.readTextFromStream(getClass().getResourceAsStream("transaction_history.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        sMockWebServer.enqueue(new MockResponse().setBody(json).setResponseCode(200));

        CountDownLatch countDownLatch = new CountDownLatch(1);
        ReceiptRepository receiptRepository = RepositoryLocator.getInstance().getRepository(ReceiptRepository.class);
        receiptRepository.getAll(new IRepository.CallRepository<List<Receipt>>() {
            @Override
            public void onDone(Deal<List<Receipt>> deal) {

                System.out.println("da");

                receiptRepository.update(deal.getModel().get(0), new IRepository.CallRepository<Receipt>() {
                    @Override
                    public void onDone(Deal<Receipt> deal) {

                        System.out.println("da");

                    }
                });
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
