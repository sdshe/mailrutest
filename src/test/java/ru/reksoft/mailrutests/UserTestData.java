package ru.reksoft.mailrutests;

import java.util.List;

class UserTestData {

    User user;

    @Override
    public String toString() {
        return "UserTestData{" +
                "user=" + user +
                ", message=" + message +
                '}';
    }

    Message message;
    UserTestData() {

    }

}
class Message {
    List<String> to;
    String subject;
    String textMessage;
    List<Attachment> attachments;

    @Override
    public String toString() {
        return "Message{" +
                "to=" + to +
                ", subject='" + subject + '\'' +
                ", textMessage='" + textMessage + '\'' +
                ", attachments=" + attachments +
                '}';
    }
}
enum AttachmentType {
    cloud, fromHardDrive;
}
class Attachment {
    AttachmentType attachmentType;
    String source;

    @Override
    public String toString() {
        return "Attachment{" +
                "attachmentType=" + attachmentType +
                ", source='" + source + '\'' +
                '}';
    }
}
class User {
    String name;
    String password;


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}