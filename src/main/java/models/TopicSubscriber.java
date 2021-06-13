package models;

import interfaces.ISubscriber;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@AllArgsConstructor
public class TopicSubscriber {

    private final ISubscriber subscriber;
    private final AtomicInteger offset;

    public TopicSubscriber(final ISubscriber subscriber) {
        this.subscriber = subscriber;
        this.offset = new AtomicInteger(0);
    }

}
