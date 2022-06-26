package jandy3.DesignSeller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Schema
public class ReturnAddressDto {
    @Schema(name = "반송인")
    @NotEmpty
    private String name;
    @Schema(name = "반송 주소")
    @NotEmpty
    private String address;
}