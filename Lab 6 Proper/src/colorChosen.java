/*import javax.swing.event.EventListenerList;

class colorChosen {
    private EventListenerList listenerList = new EventListenerList();

    public void addColorChosenListener(colorChosenListener listener) {
        listenerList.add(colorChosenListener.class, listener);
    }
    public void removeColorChosenListener(colorChosenListener listener) {
        listenerList.remove(colorChosenListener.class, listener);
    }
    void fireMyEvent(colorChosenEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
            if (listeners[i] == colorChosenListener.class) {
                ((colorChosenListener) listeners[i+1]).colorWasChosen(evt);
            }
        }
    }
}*/