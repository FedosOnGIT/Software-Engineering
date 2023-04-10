package service.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import persistence.common.Currency;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistryUserRequest {
    private String name;
    private Currency preferred;
}
