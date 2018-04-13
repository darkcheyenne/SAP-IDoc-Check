package me.eberli;

import com.sap.conn.jco.ext.DestinationDataProvider;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Andreas
 */
public class JCon_Util {
    static String DESTINATION_NAME1 = "ABAP_AS_WITHOUT_POOL";
    static String DESTINATION_NAME2 = "ABAP_AS_WITH_POOL";

    public static void anlegen() throws IOException {
        Properties connectProperties = new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST,
                "service");
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,
                "00");
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT,
                "800");
        connectProperties.setProperty(DestinationDataProvider.JCO_USER,
                "ADMIN");
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD,
                "welcome");
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG,
                "en");
        Util.createDestinationDataFile(DESTINATION_NAME1,
                connectProperties);

        Util.createDestinationDataFile(DESTINATION_NAME2, connectProperties);
    }
}
