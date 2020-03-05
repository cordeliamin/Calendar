import java.time.LocalDateTime;

public class IndividualAlert extends Alert{
    private LocalDateTime time;

    public IndividualAlert(LocalDateTime evT, String name, String message, LocalDateTime t){
        super(evT, message, name);
        time = t;
        addtoTimes(time);
    }
}
