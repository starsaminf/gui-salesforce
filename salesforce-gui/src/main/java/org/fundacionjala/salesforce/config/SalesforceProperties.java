package org.fundacionjala.salesforce.config;

import org.fundacionjala.core.throwables.PropertiesReadingException;
import org.fundacionjala.core.utils.PropertiesFileReader;

/**
 * [MR] Class that read properties from salesforce.properties file.
 */
public final class SalesforceProperties {

    private static final String PROPERTIES_FILE_PATH = "../salesforce.properties";
    private static SalesforceProperties singleInstance;
    private static PropertiesFileReader propertiesFileReader;

    /**
     * If singleInstance was not instanced before it create a new one, otherwise return the created.
     *
     * @return singleInstance
     */
    public static SalesforceProperties getInstance() {
        if (singleInstance == null) {
            singleInstance = new SalesforceProperties();
        }
        return singleInstance;
    }

    private SalesforceProperties() {
        try {
            propertiesFileReader = new PropertiesFileReader(PROPERTIES_FILE_PATH);
        } catch (PropertiesReadingException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }


    /**
     * Gets the Login Url from the properties file.
     *
     * @return login Url.
     */
    public String getLoginUrl() {
        String baseUrl = null;
        try {
            baseUrl = propertiesFileReader.getProperty("loginUrl");
        } catch (PropertiesReadingException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            return baseUrl;
        }
    }

    /**
     * Gets the BaseLoginUrl from the properties file.
     *
     * @return base url.
     */
    public String getBaseApiUrl() {
        String baseApiUrl = null;
        try {
            baseApiUrl = propertiesFileReader.getProperty("baseApiUrl");
        } catch (PropertiesReadingException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            return baseApiUrl;
        }
    }

    /**
     * Gets the Api Login Url from the salesforce.properties file.
     *
     * @return base API Url
     */
    public String getApiLoginUrl() {
        String apiLoginUrl = null;
        try {
            apiLoginUrl = propertiesFileReader.getProperty("apiLoginUrl");
        } catch (PropertiesReadingException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            return apiLoginUrl;
        }
    }
}
