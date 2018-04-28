package me.eberli;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Funktionsklasse für das Auslesen von Daten via RFC_READ_TABLE
 * @author Andreas
 */
public class ReadTable {

    /**
     * Zähl, wie viele IDocs mit gewissen Kriterien auf einem System bekannt sind
     * @param ABAP_AS gibt den Namen der jcoDestination-Datei im Wurzelverzeichnis der Java-Anwendung an welche für den aktuellen Call verwendet werden soll.
     * @param query gibt die Filter-Query an welche in das OPTIONS-Feld des RFC-Bausteins geschreiben werden soll
     * @return Gibt einen Integer-Wert zurück wie viele Werte mit der Query selektiert wurden
     * @throws JCoException 
     */
    public static Integer rfcCountTableEDIDS_Stat(String ABAP_AS, String query) throws JCoException {
        // Deklaration der lokalen Variablen
        JCoDestination destination;
        JCoFunction function;
        JCoParameterList listParams, jcoParameterList;
        JCoTable jcoTableOptions,jcoTableFields;
        SimpleDateFormat sdf;
        Date now;
        String strDate,currentFilter;
        JCoTable codes;
        
        // Instanzieren der lokalen Variablen
        now = new Date();
        
        // Log-Output für das starten der Funktion
        ConsoleLog.printEvent("Aufruf der Funktion zur Zählung von Einträgen in EDIDS mit Query: " + query);
        
        destination = JCoDestinationManager.getDestination(ABAP_AS);
        function = destination.getRepository().getFunction("RFC_READ_TABLE");
        listParams = function.getImportParameterList();
        
        listParams.setValue("QUERY_TABLE", "EDIDS");

        jcoParameterList = function.getTableParameterList();
        jcoTableOptions = jcoParameterList.getTable("OPTIONS");
        jcoTableFields = jcoParameterList.getTable("FIELDS");

        jcoTableFields.appendRow();
        jcoTableFields.setValue("FIELDNAME", "STATYP");
        
        sdf = new SimpleDateFormat("yyyyMMdd");
        strDate = sdf.format(now);
        currentFilter = "LOGDAT LIKE '" + strDate + "'";
        
        jcoTableOptions.appendRow();
        jcoTableOptions.setValue("TEXT", query + " AND " + currentFilter);

        try {
            function.execute(destination);
             
            codes = function.getTableParameterList().getTable("DATA");

            ConsoleLog.printEvent("Die Tabelle EDIDS mit der Query " + query + " und LOGDAT LIKE " + strDate + " hat " + codes.getNumRows() + " Einträge");
            
            return codes.getNumRows();
        } catch (JCoException e) {
            ConsoleLog.printEvent("Die Funktion zur Zählung von Einträgen in EDIDS mit Query: " + query + " ist auf folgenden Fehler gelaufen: " + e.toString());
            return -1;
        }

    }
}
