package utility;

import java.util.ArrayList;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

public class Emitter implements Publisher<Object> {

    ArrayList<Subscriber<? super Object>> subscribers = new ArrayList<>();

    @Override
    public void subscribe(Subscriber<? super Object> subscriber) {
        subscribers.add(subscriber);
    }   
}
