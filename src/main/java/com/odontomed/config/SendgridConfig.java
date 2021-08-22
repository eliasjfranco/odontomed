package com.odontomed.config;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SendgridConfig {


    public void enviarEmail(String receptor, String nombre, String apellido) {
        Email from = new Email("francoeliasjoel@gmail.com");
        String name = apellido + " " + nombre;
        Email to = new Email(receptor);
        String sub = "";
        Content content = new Content("", "");

        Personalization personalization = new Personalization();
        personalization.addSubstitution("name", name);

        Mail mail = new Mail(from, sub, to, content);


        SendGrid sendGrid = new SendGrid("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");

        mail.addPersonalization(personalization);
        mail.setTemplateId("d-a032668c52534f18881b63294f192e65");
        Request req = new Request();

        try {
            req.setMethod(Method.POST);
            req.setEndpoint("mail/send");
            req.setBody(mail.build());

            Response res = sendGrid.api(req);

            System.out.println(res.getStatusCode());
            System.out.println(res.getBody());
            System.out.println(res.getHeaders());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("email enviado correctamente;");

    }
}


