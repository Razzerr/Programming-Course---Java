import java.util.EventListener;

public interface colorChosenListener extends EventListener {
    public void colorWasChosen(colorChosenEvent evt);
}
