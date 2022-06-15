package jandy3.DesignSeller.domain.embed;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String street;
    private String zipcode;
    private String detail;
}
