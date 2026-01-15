package dto;

import lombok.Data;
import java.util.List;

@Data
public class HuobiResponse {
    private List<HuobiTickerDto> data;
}


