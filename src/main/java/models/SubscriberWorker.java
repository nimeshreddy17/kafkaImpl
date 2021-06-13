package models;

import lombok.SneakyThrows;

public class SubscriberWorker implements Runnable {
    private final TopicSubscriber topicSubscriber;
    private final Topic topic;

    public SubscriberWorker(final Topic topic, final TopicSubscriber topicSubscriber) {
        this.topic = topic;
        this.topicSubscriber = topicSubscriber;
    }

    synchronized public void wakeUpIfNeeded() {
        synchronized (topicSubscriber) {
            topicSubscriber.notify();
        }
    }

    @SneakyThrows
    @Override
    public void run() {
        synchronized (topicSubscriber) {
            do {
                int currOffset = topicSubscriber.getOffset().get();
                while (currOffset >= topic.getMessages().size()) {
                    topicSubscriber.wait();
                }
                Message message = topic.getMessages().get(currOffset);
                topicSubscriber.getSubscriber().consume(message);

                topicSubscriber.getOffset().compareAndSet(currOffset, currOffset + 1);
            } while (true);
        }
    }

}
