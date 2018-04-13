package me.eberli;

/**
 * Datenklasse für den IDoc-Versandstatus pro System
 * @author Andreas
 */
public class IDocStatus {

    private String Systemname;
    private Integer status53 = 0;
    private Integer status64 = 0;

    public String getSystemname() {
        return Systemname;
    }

    public void setSystemname(String Systemname) {
        this.Systemname = Systemname;
    }

    public Integer getStatus53() {
        return status53;
    }

    public void setStatus53(Integer NrOfIdocs) {
        this.status53 = NrOfIdocs;
    }

    public Integer getStatus64() {
        return status64;
    }

    public void setStatus64(Integer NrOfOpenIdocs) {
        this.status64 = NrOfOpenIdocs;
    }
    
    public Integer getPendingIDocs(){
        return status53 - status64;
    }
}
