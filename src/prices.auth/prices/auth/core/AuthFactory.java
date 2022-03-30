package prices.auth.core;

import java.lang.reflect.Constructor;
import java.util.logging.Logger;

public abstract class AuthFactory {
    private final Logger LOGGER = Logger.getLogger(AuthFactory.class.getName());
    public abstract Authorization createAuth();

    public StorageStrategy createStorageStrategyObject(String fullyQualifiedName) {
        StorageStrategy storageStrategy = null;
        try {
            Class<?> clz = Class.forName(fullyQualifiedName);
            Constructor<?> constructor = clz.getDeclaredConstructors()[0];
            storageStrategy = (StorageStrategy) constructor.newInstance();
        } catch (IllegalArgumentException e) {
            LOGGER.severe("Failed to create instance of StorageStrategy.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            LOGGER.severe("Failed to run: Check your constructor argument");
            System.exit(20);
        } catch (ClassCastException e) {
            LOGGER.severe("Failed to create instance of StorageStrategy.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            LOGGER.severe("Failed to cast the object");
            System.exit(30);
        } catch (ClassNotFoundException e) {
            LOGGER.severe("Failed to create instance of StorageStrategy.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            LOGGER.severe("Class not found");
            System.exit(40);
        } catch (Exception e) {
            LOGGER.severe("Failed to create instance of StorageStrategy.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            System.exit(50);
        }
        return storageStrategy;
    }

    public Verifier createVerifierObject(String fullyQualifiedName) {
        Verifier verifier = null;
        try {
            Class<?> clz = Class.forName(fullyQualifiedName);
            Constructor<?> constructor = clz.getDeclaredConstructors()[0];
            verifier = (Verifier) constructor.newInstance();
        } catch (IllegalArgumentException e) {
            LOGGER.severe("Failed to create instance of Verifier.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            LOGGER.severe("Failed to run: Check your constructor argument");
            System.exit(20);
        } catch (ClassCastException e) {
            LOGGER.severe("Failed to create instance of Verifier.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            LOGGER.severe("Failed to cast the object");
            System.exit(30);
        } catch (ClassNotFoundException e) {
            LOGGER.severe("Failed to create instance of Verifier.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            LOGGER.severe("Class not found");
            System.exit(40);
        } catch (Exception e) {
            LOGGER.severe("Failed to create instance of Verifier.");
            LOGGER.severe("Given FQN: " + fullyQualifiedName);
            System.exit(50);
        }
        return verifier;
    }
}
