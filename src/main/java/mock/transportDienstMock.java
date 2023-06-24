package mock;

import domein.TransportDienst;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Arrays;
import java.util.List;

public class transportDienstMock {
    public static final ObservableList<TransportDienst> transportDiensten;
    public static final List<String> phoneNumbers = Arrays.asList("0612345678", "0687654321", "0612345678", "0687654321", "06859874354", "0889654321");
    public static final List<String> emailAddresses = Arrays.asList("voorbeeld1@voorbeeld.nl", "voorbeeld2@voorbeeld.nl");

    static {
        transportDiensten = FXCollections.observableArrayList(
                new TransportDienst(
                        "Naam",
                        "0612345678, 0687654321, 0612345678, 0687654321, 06859874354",
                        "voorbeeld1@voorbeeld.nl, voorbeeld2@voorbeeld.nl, voorbeeld3@voorbeeld.nl",
                        4,
                        true,
                        "prefixVb",
                        true
                ),
                new TransportDienst(
                        "Naam",
                        "0612345678, 0687654321, 0612345678, 0687654321, 06859874354",
                        "voorbeeld1@voorbeeld.nl, voorbeeld2@voorbeeld.nl, voorbeeld3@voorbeeld.nl",
                        4,
                        true,
                        "prefixVb",
                        true
                )
        );
    }

}
