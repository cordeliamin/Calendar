import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class IndividualAlert extends Alert{
    private LocalDateTime time;

    public IndividualAlert(LocalDateTime evT, String name, LocalDateTime t){
        super(evT, name);
        time = t;
        addtoTimes(time);
    }
}
