package org.example;

import org.apache.commons.mail.util.MimeMessageParser;

import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Java Mail client
 * https://stackoverflow.com/questions/1748183/download-attachments-using-java-mail
 * Apache Commons Mail API is also mentioned there at the very bottom.
 * Here's MimeMessageParser example (I only really forgot to call parse() =))
 * https://stackoverflow.com/questions/38874376/javamail-also-extract-attachments-of-encapsulated-message-content-type-message/48879912
 */

public class Client {

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();

        properties.put("mail.pop3.host", "pop.mail.ru");
        properties.put("mail.pop3.port", "995");
        properties.put("mail.pop3.starttls.enable", "true");

        Session emailSession = Session.getDefaultInstance(properties);

        //create the POP3 store object and connect with the pop server
        Store store = emailSession.getStore("pop3s");

        //store.connect("pop.mail.ru","officelinespb-tech@mail.ru", "S3HJU7KSv3");
        store.connect("pop.mail.ru","officelinespb-tech@mail.ru", "qi8R03YwR5z4GrxPpiFa");

        //create the folder object and open it
        Folder emailFolder = store.getFolder("INBOX");
        emailFolder.open(Folder.READ_ONLY);

        //store.getDefaultFolder();
        //store.getPersonalNamespaces();
        //store.getUserNamespaces();
//        Folder[] namespaces = store.getSharedNamespaces();
//        for (Folder namespace : namespaces) {
//            System.out.println(namespace.getFullName());
//        }
//        System.out.println("---");

        // retrieve the messages from the folder in an array and print it
        Message[] messages = emailFolder.getMessages();
        System.out.println("messages.length=" + messages.length);
        int count = 0;
        //for (int i = 0, n = messages.length; i < n; i++) {
        for (int i = messages.length - 1; i >= 0; i--) {
            Message message = messages[i];

            System.out.println("---------------------------------");
            System.out.println("Email Number " + (i + 1));
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + message.getFrom()[0]);
            //System.out.println("Text: " + message.getContent().toString());
            System.out.println("Size: " + Util.formatSize(message.getSize()));

            if (message instanceof MimeMessage) {
                final MimeMessageParser messageParser = new MimeMessageParser((MimeMessage) message);
                messageParser.parse(); // This is important :-)
                //System.out.println("Text: " + messageParser.getPlainContent()); // Works
                //System.out.println("messageParser.hasAttachments()=" + messageParser.hasAttachments());
                //System.out.println("Attachments: " + messageParser.getAttachmentList().size());
                for (DataSource dataSource : messageParser.getAttachmentList()) {
                    System.out.println("File: " + dataSource.getName());
                }
            }

//            Multipart multipart = (Multipart) message.getContent();
//
//            System.out.println("multipart.getCount()=" + multipart.getCount());
//
//            for (int f = 0; f < multipart.getCount(); f++) {
//                BodyPart part = multipart.getBodyPart(f);
//                if (Part.ATTACHMENT.equals(part.getDisposition()) && !isBlank(part.getFileName())) {
//                    System.out.println("File: " + part.getFileName());
//                }
//            }

            if (++count > 3) {
                break;
            }
        }

        System.out.println("============");
        System.out.println("Memory: " + Util.formatSize(Util.usedMemory()));
    }
}
