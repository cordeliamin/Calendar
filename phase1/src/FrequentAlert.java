import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class FrequentAlert extends Alert{
    private Date frequency;

    public FrequentAlert(Date et, Date t){
        frequency = t;
        eventTime = et;
    }

    @Override
    public List<Date> appearAt(){
        List<Date> l = new ArrayList<>();
        // for every date that the alert should appear
        /*
        for (long i = eventDate.getTime(); i > Calendar.getCurrTime(); i = i + frequency.getTime()){
            l.add(new Date(i));
        }
        */
        return l;
    }
}
