import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class Price {
    private long id;
    private String productCode;
    private int number;
    private int depart;
    private Date begin;
    private Date end;
    private long value;
}