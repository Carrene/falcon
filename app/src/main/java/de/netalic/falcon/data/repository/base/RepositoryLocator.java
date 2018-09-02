package de.netalic.falcon.data.repository.base;

import java.util.HashMap;
import java.util.Map;

public class RepositoryLocator {

    private static volatile RepositoryLocator sRepositoryLocator;
    private Map<String, Object> map = new HashMap<>();

    private RepositoryLocator() {

        // To avoid reflection
        if (sRepositoryLocator != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");

        }
    }

    public static RepositoryLocator getInstance() {

        if (sRepositoryLocator == null) {
            synchronized (RepositoryLocator.class) {
                if (sRepositoryLocator == null) {
                    sRepositoryLocator = new RepositoryLocator();
                }
            }
        }
        return sRepositoryLocator;
    }

    public <T extends IRepository> void setRepository(T iRepository) {

        map.put(iRepository.getClass().getName(), iRepository);
    }

    public <T extends IRepository> T getRepository(Class<T> clazz) {

        return clazz.cast(map.get(clazz.getName()));
    }
}
