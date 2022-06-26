package jandy3.DesignSeller.domain.embed;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private String bank;
    private String accountNumber;
}
