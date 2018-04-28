package me.eberli;

/**
 * Datenklasse für den IDoc-Versandstatus pro System
 * @author Andreas
 */
public class IDocStatus {

    private String Systemname;
    private Integer Status53 = 0;
    private Integer Status64 = 0;
    private Integer PendingIDocs; //Dummy-Variable, wird nicht verwendet sondern aus der Differenz zwische Status 53 und 64 gebildet
    
    // GETTER und SETTER für Systemname
    public String getSystemname() {
        return Systemname;
    }

    public void setSystemname(String Systemname) {
        this.Systemname = Systemname;
    }

    // GETTER und SETTER für Anzahl der IDocs mit Status 53
    public Integer getStatus53() {
        return Status53;
    }

    public void setStatus53(Integer NrOfIdocs) {
        this.Status53 = NrOfIdocs;
    }

    // GETTER und SETTER für Anzahl der IDocs mit Status 64
    public Integer getStatus64() {
        return Status64;
    }

    public void setStatus64(Integer NrOfOpenIdocs) {
        this.Status64 = NrOfOpenIdocs;
    }
    
    // GETTER für Differenz zwischen IDocs mit Status 53 und 64
    public Integer getPendingIDocs(){
        return Status53 - Status64;
    }
}
