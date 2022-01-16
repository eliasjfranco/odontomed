package com.odontomed.config;

import com.odontomed.util.FormatDate;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class SendgridConfig {

    @Autowired
    FormatDate formatDate;

    public void emailBienvenida(String receptor, String nombre, String apellido){
        Mail mail = new Mail();
        String name = apellido + " " + nombre;
        Email from = new Email("odontomed.info@gmail.com");
        Email to = new Email(receptor);
        mail.setFrom(from);

        Personalization pers = new Personalization();

        pers.addTo(to);
        pers.addDynamicTemplateData("name", new String[] {name});
        mail.addPersonalization(pers);
        mail.setTemplateId("d-77ffa3a0bea44e5c9100d0bab889ed2b");

        enviarEmail(mail);


    }

    public void emailTurno(String receptor, String nombre, String apellido, LocalDate date, LocalTime hs){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dia = formatter.format(date);
        Mail mail = new Mail();
        String name = apellido + " " + nombre;
        Email from = new Email("odontomed.info@gmail.com");
        Email to = new Email(receptor);
        mail.setFrom(from);

        Personalization pers = new Personalization();

        pers.addTo(to);
        pers.addDynamicTemplateData("name", new String[] {name});
        pers.addDynamicTemplateData("fecha", new String[] {dia});
        pers.addDynamicTemplateData("horario", new String[] {hs.toString()});
        mail.addPersonalization(pers);
        mail.setTemplateId("d-fefa2c124e964de9ba9c434f65ecb213");

        enviarEmail(mail);
    }

    public void enviarEmail(Mail mail) {

        SendGrid sendGrid = new SendGrid("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");
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


