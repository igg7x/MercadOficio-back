package com.example.demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

@Service
public class EmailService {

    private final Resend emailResend;

    public EmailService(@Value("${spring.emailApi.key}") String apiKey) {
        this.emailResend = new Resend(apiKey);

    }

    private String createEmailHtml(String message) {

        String htmlFormat = "<div className=\"font-sans text-gray-900 bg-gray-100 min-h-screen flex items-center justify-center p-4\">\n"
                + //
                "      <div className=\"bg-white w-full max-w-md p-8 rounded-lg shadow-md\">\n" + //
                "        <header className=\"text-center mb-8\">\n" + //
                "          <h1 className=\"text-2xl font-bold text-gray-800 mb-2\">Mercado Oficio</h1>\n" + //
                "        </header>\n" + //
                "        \n" + //
                "          <h3 className=\"text-lg font-medium antialiased mb-4\">" + message + "</h3>\n" + //
                // " <Button className=\"w-full bg-amber-600 hover:bg-amber-700 text-white\">\n"
                // + //
                // " Explore Marketplace\n" + //
                // " </Button>\n" + //

                "        </main>\n" + //
                "        \n" + //
                "          <p className=\"text-gray-700 mb-6\">\n" + //
                "            Gracias por ser parte de Mercado Oficio.\n"
                + //
                "          </p>\n" + //
                "        <footer className=\"text-center text-sm text-gray-600\">\n" + //
                "          <p className=\"mb-2\">&copy; 2024 Mercado Oficio.</p>\n" + //
                "          <div className=\"flex justify-center space-x-4\">\n" + //
                "            <a href=\"#\" className=\"hover:text-amber-600\">Privacy Policy</a>\n" + //
                "            <a href=\"#\" className=\"hover:text-amber-600\">Terms of Service</a>\n" + //
                "            <a href=\"#\" className=\"hover:text-amber-600\">Unsubscribe</a>\n" + //
                "          </div>\n" + //
                "        </footer>\n" + //
                "      </div>\n" + //
                "    </div>";
        return htmlFormat;
    }

    public void sendEmail(String email, String subject, String message) {
        CreateEmailOptions emailParams = CreateEmailOptions.builder()
                .to(email)
                // .to("delivered@resend.dev")
                .from("Mercado Oficio <onboarding@resend.dev>")
                .subject(subject)
                .html(createEmailHtml(message))
                .build();

        try {
            CreateEmailResponse response = emailResend.emails().send(emailParams);
            System.out.println(response);
        } catch (ResendException e) {
            e.printStackTrace();
        }

    }

}
