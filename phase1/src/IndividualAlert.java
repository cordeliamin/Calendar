import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class IndividualAlert extends Alert{
    private Date appearTime;

    public IndividualAlert(Date t){
        appearTime = t;
    }

    @Override
    public List<Date> appearAt() {
        List <Date> l = new ArrayList<>();
        l.add(appearTime);
        return l;
    }
}
