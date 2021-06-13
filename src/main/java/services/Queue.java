package services;

import interfaces.ISubscriber;
import models.Message;
import models.Topic;
import models.TopicHandler;
import models.TopicSubscriber;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Queue {

    private final Map<String, TopicHandler> topicHandlers;

    public Queue() {
        this.topicHandlers = new HashMap<>();
    }

    public Topic createTopic(final String topicName) {
        final Topic topic = new Topic(topicName, UUID.randomUUID().toString());
        TopicHandler topicHandler = new TopicHandler(topic);
        topicHandlers.put(topic.getTopicId(), topicHandler);
        System.out.println("Created a topic :" + topicName);
        return topic;
    }

    public void subscribe(final ISubscriber subscriber, final Topic topic) {
        topic.addSubscriber(new TopicSubscriber(subscriber));
        System.out.println(subscriber.getId() + " subscribed to the topic " + topic.getTopicName());
    }

    public void publish(final Topic topic, final Message message) {
        topic.addMessage(message);
        System.out.println(message.getMsg() + " published into the topic "+topic.getTopicName());
        new Thread(() -> topicHandlers.get(topic.getTopicId()).publish()).start();
    }


    public void resetOffset(Topic topic1, SleepingSubscriber sub1, int i) {
    }
}
