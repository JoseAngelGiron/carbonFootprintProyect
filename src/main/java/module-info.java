module com.github.JoseAngelGiron {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires java.naming;
    requires org.hibernate.orm.core;


    opens com.github.JoseAngelGiron to javafx.fxml;
    exports com.github.JoseAngelGiron;
    exports com.github.JoseAngelGiron.view;
    opens com.github.JoseAngelGiron.view to javafx.fxml;

    opens com.github.JoseAngelGiron.model.entity to org.hibernate.orm.core;
    opens com.github.JoseAngelGiron.model.dao to org.hibernate.orm.core;
}
