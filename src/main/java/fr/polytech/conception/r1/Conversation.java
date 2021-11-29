package fr.polytech.conception.r1;

import java.time.ZonedDateTime;
import java.util.LinkedList;

import fr.polytech.conception.r1.profile.User;

public class Conversation
{
    private final User user1;
    private final User user2;
    private final LinkedList<Message> messages;

    public Conversation(User user1, User user2, String content)
    {
        if (user1 == user2)
            throw new IllegalArgumentException("Cannot create a conversation between two identical user instances");

        this.user1 = user1;
        this.user2 = user2;
        messages = new LinkedList<>();
        messages.add(new Message(user1, ZonedDateTime.now(), content));
    }

    public User getUser1()
    {
        return user1;
    }

    public User getUser2()
    {
        return user2;
    }

    public LinkedList<Message> getMessages()
    {
        return this.messages;
    }

    public int numberMessages()
    {
        return this.messages.size();
    }

    public void send(User sender, String content) throws InvalidMessageException
    {
        if (!sender.equals(user1) && !sender.equals(user2))
            throw new InvalidMessageException("User sending a message not in conversation");
        messages.add(new Message(sender, ZonedDateTime.now(), content));
    }

    public void send(Boolean sender1, String content)
    {
        messages.add(new Message((sender1 ? user1 : user2), ZonedDateTime.now(), content));
    }

    public class Message
    {
        private final User sender;
        private final ZonedDateTime sendingTime;
        private final String content;

        private Message(User sender, ZonedDateTime sendingTime, String content)
        {
            this.sender = sender;
            this.sendingTime = sendingTime;
            this.content = content;
        }

        public User getSender()
        {
            return sender;
        }

        public User getReceiver()
        {
            return (sender.equals(user1) ? user2 : user1);
        }

        public ZonedDateTime getSendingTime()
        {
            return sendingTime;
        }

        public String getContent()
        {
            return content;
        }
    }
}
