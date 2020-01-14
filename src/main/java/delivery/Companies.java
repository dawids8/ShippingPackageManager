package delivery;

import java.util.ArrayList;
import java.util.List;

public enum Companies {

    DHL, DPD, FED_EX, GLS, POCZTEX;

    public static List<String> getCompaniesList() {
        final List<String> list = new ArrayList<>();

        for (Companies company : Companies.values()) {
            list.add(company.toString());
        }

        return list;
    }
}
