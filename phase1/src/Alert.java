import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public abstract class Alert {
    String name;
    Date eventTime;

    public abstract List<Date> appearAt();

    @Override
    public String toString() {
        return "Alert for Event" + name + "occurring at" + eventTime.toString();
    }
}
