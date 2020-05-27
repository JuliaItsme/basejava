package com.urise.webapp;

import com.urise.webapp.model.*;

import java.util.Arrays;
import java.util.UUID;

public class ResumeTestData {
    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final String UUID_4 = UUID.randomUUID().toString();

    private static Resume RESUME_1 = new Resume(UUID_1, "Григорий Кислин");
    private static Resume RESUME_2 = new Resume(UUID_2, "Федорова Федора");
    private static Resume RESUME_3 = new Resume(UUID_3, "Иванов Иван");
    private static Resume RESUME_4 = new Resume(UUID_4, "Петров Петр");

    static {
        RESUME_1.addContact(ContactType.PHONE_NUMBER, "+7(921)8550482");
        RESUME_1.addContact(ContactType.SKYPE, "grigory.kislin");
        RESUME_1.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        RESUME_1.addContact(ContactType.LINKEDIN, "grigory.kislin");
        RESUME_1.addContact(ContactType.GITHUB, "https://github.com/gkislin");
        RESUME_1.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        RESUME_1.addContact(ContactType.HOME_PAGE, "http://gkislin.ru/");

        RESUME_2.addContact(ContactType.PHONE_NUMBER, "+7(952)5671289");
        RESUME_2.addContact(ContactType.SKYPE, "fedora.fedorova");
        RESUME_2.addContact(ContactType.EMAIL, "fedorova@rambler.ru");
        RESUME_2.addContact(ContactType.LINKEDIN, "fedora.fedorova");
        RESUME_2.addContact(ContactType.GITHUB, "https://github.com/fedorova");
        RESUME_2.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/012345/fedora-fedorova");
        RESUME_2.addContact(ContactType.HOME_PAGE, "http://fedorova.ru/");

        RESUME_3.addContact(ContactType.PHONE_NUMBER, "+7(911)1234567");
        RESUME_3.addContact(ContactType.SKYPE, "ivan.ivanov");
        RESUME_3.addContact(ContactType.EMAIL, "ivanov@mail.ru");
        RESUME_3.addContact(ContactType.LINKEDIN, "ivan.ivanov");
        RESUME_3.addContact(ContactType.GITHUB, "https://github.com/ivanov");
        RESUME_3.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/012345/ivan-ivanov");
        RESUME_3.addContact(ContactType.HOME_PAGE, "http://ivanov.ru/");

        RESUME_4.addContact(ContactType.PHONE_NUMBER, "+7(965)9876543");
        RESUME_4.addContact(ContactType.SKYPE, "petr.petrov");
        RESUME_4.addContact(ContactType.EMAIL, "petrov@gmail.com");
        RESUME_4.addContact(ContactType.LINKEDIN, "petr.petrov");
        RESUME_4.addContact(ContactType.GITHUB, "https://github.com/petrov");
        RESUME_4.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/678901/petr-petrov");
        RESUME_4.addContact(ContactType.HOME_PAGE, "http://petrov.ru/");

        RESUME_1.addSection(SectionType.PERSONAL, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        RESUME_1.addSection(SectionType.OBJECTIVE, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры"));
        RESUME_1.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList(
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike.Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.")));
        RESUME_1.addSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,",
                "MySQL, SQLite, MS SQL, HSQLDB",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security,Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).",
                "Python: Django.",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
                "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
                "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.",
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix,",
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.",
                "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования",
                "Родной русский, английский \"upper intermediate\"")));

        RESUME_2.addSection(SectionType.PERSONAL, new TextSection("Студентка"));
        RESUME_2.addSection(SectionType.OBJECTIVE, new TextSection("Креативная"));
        RESUME_2.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("Разработка Web приложения", "Java Enterprise")));
        RESUME_2.addSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("JavaSE", "Tomcat")));

        RESUME_3.addSection(SectionType.PERSONAL, new TextSection("Студент"));
        RESUME_3.addSection(SectionType.OBJECTIVE, new TextSection("Умный"));
        RESUME_3.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("Многопоточность", "Налаживание процесса разработки")));
        RESUME_3.addSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("JavaEE ", "Spring")));

        RESUME_4.addSection(SectionType.PERSONAL, new TextSection("Инженер"));
        RESUME_4.addSection(SectionType.OBJECTIVE, new TextSection("Талантливый"));
        RESUME_4.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("Разработка Web приложения", "Spring")));
        RESUME_4.addSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("Scala", "XML/XSD/XSLT")));

/*        RESUME_1.addSection(SectionType.EXPERIENCE, new OrganizationSection(Arrays.asList(
                new Organization("Java Online Projects", "http://javaops.ru/",
                        new Organization.Position(of(2013, Month.OCTOBER), NOW, "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.")),
                new Organization("Wrike", "https://www.wrike.com/",
                        new Organization.Position(of(2014, Month.OCTOBER), of(2016, Month.JANUARY), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")),
                new Organization("RIT Center", null,
                        new Organization.Position(of(2012, Month.APRIL), of(2014, Month.OCTOBER), "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python")),
                new Organization("Luxoft (Deutsche Bank)", "https://www.luxoft.com/",
                        new Organization.Position(of(2010, Month.DECEMBER), of(2012, Month.APRIL), "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.")),
                new Organization("Yota", "https://www.yota.ru/",
                        new Organization.Position(of(2008, Month.JUNE), of(2010, Month.DECEMBER), "Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)")),
                new Organization("Enkata", "https://www.pega.com/products/pega-platform/robotic-automation",
                        new Organization.Position(of(2007, Month.MARCH), of(2008, Month.JUNE), "Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS)частей кластерного J2EE приложения (OLAP, Data mining)")),
                new Organization("Siemens AG", "https://new.siemens.com/ru/ru.html",
                        new Organization.Position(of(2005, Month.JANUARY), of(2007, Month.FEBRUARY), "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).")),
                new Organization("Alcatel", "http://www.alcatel.ru/",
                        new Organization.Position(of(1997, Month.SEPTEMBER), of(2005, Month.JANUARY), "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).")))));
        RESUME_1.addSection(SectionType.EDUCATION, new OrganizationSection(Arrays.asList(
                new Organization("Coursera", "https://www.coursera.org/learn/progfun1",
                        new Organization.Position(of(2013, Month.MARCH), of(2013, Month.MAY), "\t\"Functional Programming Principles in Scala\" by Martin Odersky", null)),
                new Organization("Luxoft", "https://www.luxoft-training.ru/kurs/obektno-orientirovannyy_analiz_i_proektirovanie_na_uml.html",
                        new Organization.Position(of(2011, Month.MARCH), of(2011, Month.APRIL), "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null)),
                new Organization("Siemens AG", "https://new.siemens.com/ru/ru.html",
                        new Organization.Position(of(2005, Month.JANUARY), of(2005, Month.APRIL), "\t3 месяца обучения мобильным IN сетям (Берлин)", null)),
                new Organization("Alcatel", "http://www.alcatel.ru/",
                        new Organization.Position(of(1997, Month.SEPTEMBER), of(1998, Month.MARCH), "6 месяцев обучения цифровым телефонным сетям (Москва)", null)),
                new Organization(new Link("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "https://itmo.ru/ru/"),
                        new ArrayList<>((Arrays.asList(
                                new Organization.Position(of(1993, Month.SEPTEMBER), of(1996, Month.JULY), "Аспирантура (программист С, С++)", null),
                                new Organization.Position(of(1987, Month.SEPTEMBER), of(1993, Month.JULY), "Инженер (программист Fortran, C)", null))))),
                new Organization("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/",
                        new Organization.Position(of(1984, Month.SEPTEMBER), of(1987, Month.JUNE), "Закончил с отличием", null)))));
  */
    }

    public static String getUuid1() {
        return UUID_1;
    }

    public static String getUuid2() {
        return UUID_2;
    }

    public static String getUuid3() {
        return UUID_3;
    }

    public static String getUuid4() {
        return UUID_4;
    }

    public static Resume getResume1() {
        return RESUME_1;
    }

    public static Resume getResume2() {
        return RESUME_2;
    }

    public static Resume getResume3() {
        return RESUME_3;
    }

    public static Resume getResume4() {
        return RESUME_4;
    }

    public static Section getSection(Resume resume, SectionType sectionType) {
        return resume.getSection(sectionType);
    }
}
