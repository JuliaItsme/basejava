package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.storage.ListStorage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {
    static final ListStorage LIST_STORAGE = new ListStorage();

    public static void main(String[] args) {
        Resume resume = new Resume("uuid1","Григорий Кислин");

        resume.setContacts(ContactType.PHONE_NUMBER, "+7(921)8550482");
        resume.setContacts(ContactType.SKYPE, "grigory.kislin");
        resume.setContacts(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.setContacts(ContactType.LINKEDIN, "grigory.kislin");
        resume.setContacts(ContactType.GITHUB, "https://github.com/gkislin");
        resume.setContacts(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        resume.setContacts(ContactType.HOMEPAGE, "http://gkislin.ru/");

        resume.setSections(SectionType.PERSONAL,
                new SectionText(
                        "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.setSections(SectionType.OBJECTIVE,
                new SectionText(
                        "Аналитический склад ума, сильная логика, креативность, инициативность. " +
                                "Пурист кода и архитектуры"));

        List<String> list1 = new ArrayList<>();
        list1.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                "Более 1000 выпускников.");
        list1.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike." +
                " Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        list1.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, " +
                "Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. " +
                "Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        list1.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, " +
                "GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        list1.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, " +
                "JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга " +
                "Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        list1.add("Реализация протоколов по приему платежей всех основных платежных системы России " +
                "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        resume.setSections(SectionType.ACHIEVEMENT, new SectionList(list1));

        List<String> list2 = new ArrayList<>();
        list2.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        list2.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        list2.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        list2.add("MySQL, SQLite, MS SQL, HSQLDB");
        list2.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        list2.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        list2.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security," +
                " Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, " +
                "Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        list2.add("Python: Django.");
        list2.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        list2.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        list2.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT," +
                " MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        list2.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        list2.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher," +
                " Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        list2.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, " +
                "архитектурных шаблонов, UML, функционального программирования");
        list2.add("Родной русский, английский \"upper intermediate\"");

        resume.setSections(SectionType.QUALIFICATIONS, new SectionList(list2));

        Organization o1 = new Organization(
                "Java Online Projects",
                "http://javaops.ru/",
                LocalDate.of(2013, 10, 1),
                LocalDate.now(),
                "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        Organization o2 = new Organization(
                "Wrike",
                "https://www.wrike.com/",
                LocalDate.of( 2014, 10, 1),
                LocalDate.of( 2016, 1, 1),
                "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike" +
                        " (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                        "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        Organization o3 = new Organization(
                "RIT Center",
                null,
                LocalDate.of( 2012, 4, 1),
                LocalDate.of( 2014, 10, 1),
                "Java архитектор",
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, " +
                        "версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway)," +
                        " конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и " +
                        "серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), " +
                        "сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN " +
                        "для online редактирование из браузера документов MS Office. Maven + plugin development, " +
                        "Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, " +
                        "Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        Organization o4 = new Organization(
                "Luxoft (Deutsche Bank)",
                "https://www.luxoft.com/",
                LocalDate.of( 2010, 12, 1),
                LocalDate.of( 2012, 4, 1),
                "Ведущий программист",
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, " +
                        "Jasper, Oracle). Реализация клиентской и серверной части CRM. " +
                        "Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области " +
                        "алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
        Organization o5 = new Organization(
                "Yota",
                "https://www.yota.ru/",
                LocalDate.of( 2008, 6, 1),
                LocalDate.of( 2010, 12, 1),
                "Ведущий специалист",
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" " +
                        "(GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). " +
                        "Реализация администрирования, статистики и мониторинга фреймворка. " +
                        "Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");
        Organization o6 = new Organization(
                "Enkata",
                "https://www.pega.com/products/pega-platform/robotic-automation",
                LocalDate.of( 2007, 3, 1),
                LocalDate.of( 2008, 6, 1),
                "Разработчик ПО",
                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS)" +
                        " частей кластерного J2EE приложения (OLAP, Data mining)");
        Organization o7 = new Organization(
                "Siemens AG",
                "https://new.siemens.com/ru/ru.html",
                LocalDate.of( 2005, 1, 1),
                LocalDate.of( 2007, 2, 1),
                "Разработчик ПО",
                "Разработка информационной модели, проектирование интерфейсов, реализация и " +
                        "отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
        Organization o8 = new Organization(
                "Alcatel",
                "http://www.alcatel.ru/",
                LocalDate.of( 1997, 9, 1),
                LocalDate.of( 2005, 1, 1),
                "Инженер по аппаратному и программному тестированию",
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");

        List<Organization> org1 = new ArrayList<>();
        org1.add(o1);
        org1.add(o2);
        org1.add(o3);
        org1.add(o4);
        org1.add(o5);
        org1.add(o6);
        org1.add(o7);
        org1.add(o8);

        resume.setSections(SectionType.EXPERIENCE, new SectionOrganization(org1));

        Organization or1 = new Organization(
                "Coursera",
                "https://www.coursera.org/learn/progfun1",
                LocalDate.of( 2013, 3, 1),
                LocalDate.of( 2013, 5, 1),
                "\t\"Functional Programming Principles in Scala\" by Martin Odersky", null);
        Organization or2 = new Organization(
                "Luxoft",
                "https://www.luxoft-training.ru/kurs/obektno-orientirovannyy_analiz_i_proektirovanie_na_uml.html",
                LocalDate.of( 2011, 3, 1),
                LocalDate.of( 2011, 4, 1),
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null);
        Organization or3 = new Organization(
                "Siemens AG",
                "https://new.siemens.com/ru/ru.html",
                LocalDate.of( 2005, 1, 1),
                LocalDate.of( 2005, 4, 1),
                "\t3 месяца обучения мобильным IN сетям (Берлин)", null);
        Organization or4 = new Organization(
                "Alcatel",
                "http://www.alcatel.ru/",
                LocalDate.of( 1997, 9, 1),
                LocalDate.of( 1998, 3, 1),
                "6 месяцев обучения цифровым телефонным сетям (Москва)", null);
        Organization or5 = new Organization(
                "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                "https://itmo.ru/ru/",
                LocalDate.of( 1993, 9, 1),
                LocalDate.of( 1996, 7, 1),
                "Аспирантура (программист С, С++)", null);
        Organization or6 = new Organization(
                "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                "https://itmo.ru/ru/",
                LocalDate.of( 1987, 9, 1),
                LocalDate.of( 1993, 7, 1),
                "Инженер (программист Fortran, C)", null);
        Organization or7 = new Organization(
                "Заочная физико-техническая школа при МФТИ",
                "http://www.school.mipt.ru/",
                LocalDate.of(1984,9,1),
                LocalDate.of(1987, 6,1),
                "Закончил с отличием", null);
        List<Organization> org2 = new ArrayList<>();
        org2.add(or1);
        org2.add(or2);
        org2.add(or3);
        org2.add(or4);
        org2.add(or5);
        org2.add(or6);
        org2.add(or7);

        resume.setSections(SectionType.EDUCATION, new SectionOrganization(org2));
        
        LIST_STORAGE.save(resume);
        printAll();
    }
    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : LIST_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
