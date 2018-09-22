package de.netalic.falcon.data.repository.deposit;

import java.util.List;
import java.util.Map;

import de.netalic.falcon.data.model.Deposit;
import de.netalic.falcon.data.repository.base.IRepository;

public interface IDepositRepository extends IRepository<Deposit, Integer> {

    void getAll(CallRepository<List<Deposit>> callRepository, Map<String, String> options, int take, int skip);
}
