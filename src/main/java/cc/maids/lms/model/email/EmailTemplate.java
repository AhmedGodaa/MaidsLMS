package cc.maids.lms.model.email;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "email_template")
public class EmailTemplate {
    @Id
    private String id;
    private String name;
    private String content;

    public EmailTemplate(String id, String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
    }

    public EmailTemplate() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
