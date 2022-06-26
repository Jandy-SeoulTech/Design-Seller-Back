package jandy3.DesignSeller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Schema
public class AccountDto {
    @Schema(name = "은행명")
    @NotEmpty
    private String bank;
    @Schema(name = "계좌번호")
    @NotEmpty
    private String accountNumber;
}
