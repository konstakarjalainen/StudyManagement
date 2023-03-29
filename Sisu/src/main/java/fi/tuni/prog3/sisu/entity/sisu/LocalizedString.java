package fi.tuni.prog3.sisu.entity.sisu;

/**
 * Localized string used by Sisu.
 * Return finnish translation by default (toString).
 */
public class LocalizedString {
    String fi;
    String en;

    public String getFi() {
        return fi;
    }

    public void setFi(String fi) {
        this.fi = fi;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    @Override
    public String toString() {
        if(fi != null && !fi.isEmpty()) {
            return fi;
        } else if(en != null && !en.isEmpty()) {
            return en;
        } else {
            return "Missing translation!";
        }
    }
}
