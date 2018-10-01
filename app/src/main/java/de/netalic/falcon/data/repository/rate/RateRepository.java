package de.netalic.falcon.data.repository.rate;

import java.util.List;

import de.netalic.falcon.data.model.Rate;

public class RateRepository implements IRateRepository {

    private IRateRepository mRateRestRepository;
    private IRateRepository mRateRealmRepository;

    public RateRepository(IRateRepository restRepository, IRateRepository realmRepository) {

        mRateRestRepository = restRepository;
        mRateRealmRepository = realmRepository;
    }

    private RateRepository() {

    }

    @Override
    public void update(Rate rate, CallRepository<Rate> callRepository) {

        mRateRestRepository.update(rate, callRepository);
    }

    @Override
    public void get(String identifier, CallRepository<Rate> callRepository) {

        mRateRestRepository.get(identifier, callRepository);
    }

    @Override
    public void getAll(CallRepository<List<Rate>> callRepository) {

        mRateRestRepository.getAll(callRepository);
    }
}