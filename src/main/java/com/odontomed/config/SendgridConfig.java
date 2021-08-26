package com.odontomed.config;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class SendgridConfig {


    public void emailBienvenida(String receptor, String nombre, String apellido){
        Mail mail = new Mail();
        String name = apellido + " " + nombre;
        Email from = new Email("eliaass95@gmail.com");
        Email to = new Email(receptor);
        mail.setFrom(from);

        Personalization pers = new Personalization();

        pers.addTo(to);
        pers.addDynamicTemplateData("name", new String[] {name});
        mail.addPersonalization(pers);
        mail.setTemplateId("");

        enviarEmail(mail);


    }

    public void emailTurno(String receptor, String nombre, String apellido, LocalDate dia, LocalTime hs){
        Mail mail = new Mail();
        String name = apellido + " " + nombre;
        Email from = new Email("eliaass95@gmail.com");
        Email to = new Email(receptor);
        mail.setFrom(from);

        Personalization pers = new Personalization();

        pers.addTo(to);
        pers.addDynamicTemplateData("name", new String[] {name});
        pers.addDynamicTemplateData("fecha", new String[] {dia.toString()});
        pers.addDynamicTemplateData("horario", new String[] {hs.toString()});
        mail.addPersonalization(pers);
        mail.setTemplateId("");

        enviarEmail(mail);
    }

    public void enviarEmail(Mail mail) {

        SendGrid sendGrid = new SendGrid("");
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


