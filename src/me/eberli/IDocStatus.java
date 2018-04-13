package me.eberli;

/**
 * Datenklasse für den IDoc-Versandstatus pro System
 * @author Andreas
 */
public class IDocStatus {

    private String Systemname;
    private Integer status53 = 0;
    private Integer status64 = 0;

    
    
    // GETTER und SETTER für Systemname
    public String getSystemname() {
        return Systemname;
    }

    public void setSystemname(String Systemname) {
        this.Systemname = Systemname;
    }

    // GETTER und SETTER für Anzahl der IDocs mit Status 53
    public Integer getStatus53() {
        return status53;
    }

    public void setStatus53(Integer NrOfIdocs) {
        this.status53 = NrOfIdocs;
    }

    // GETTER und SETTER für Anzahl der IDocs mit Status 64
    public Integer getStatus64() {
        return status64;
    }

    public void setStatus64(Integer NrOfOpenIdocs) {
        this.status64 = NrOfOpenIdocs;
    }
    
    // GETTER für Differenz zwischen IDocs mit Status 53 und 64
    public Integer getPendingIDocs(){
        return status53 - status64;
    }
}
