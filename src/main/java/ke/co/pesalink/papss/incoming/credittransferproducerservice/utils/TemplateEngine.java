package ke.co.pesalink.papss.incoming.credittransferproducerservice.utils;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;


/**
 * Class used to define a template engine that shall be used to build messages for papss
 */
public class TemplateEngine {

    private MustacheFactory mustacheFactory;
    private final String templates_folder = "templates/";

    public TemplateEngine() {
        this.mustacheFactory = new DefaultMustacheFactory();
    }

    public String render(String template, Map<String, String> templateData) {
        Mustache mustache = mustacheFactory.compile(templates_folder + template);

        Writer writer = mustache.execute(new StringWriter(), templateData);
        return writer.toString();
    }
}
