package de.netalic.falcon.data.repository.deposit;

import java.util.List;
import java.util.Map;

import de.netalic.falcon.data.repository.base.IRepository;
import de.netalic.falcon.model.Deposit;

public interface IDepositRepository extends IRepository<Deposit, Integer> {

    void getAll(CallRepository<List<Deposit>> callRepository, Map<String, ?> options, int take, int skip);
}
