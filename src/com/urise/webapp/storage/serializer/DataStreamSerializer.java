package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            writeWithException(dos, contacts.entrySet(), (entry) -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, Section> sections = resume.getSections();
            writeWithException(dos, sections.entrySet(), (entry) -> {
                dos.writeUTF(entry.getKey().name());
                sectionWrite(dos, entry.getKey(), entry.getValue());
            });
        }
    }

    private void sectionWrite(DataOutputStream dos, SectionType sectionType, Section section) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                dos.writeUTF(((TextSection) section).getContent());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                if (section instanceof ListSection) {
                    writeWithException(dos, ((ListSection) section).getItems());
                }
            case EXPERIENCE:
            case EDUCATION:
                if (section instanceof OrganizationSection) {
                    writeWithException(dos, ((OrganizationSection) section).getOrganizations(), organization -> {
                        dos.writeUTF(organization.getHomePage().getName());
                        writeCheckNull(dos, organization.getHomePage().getUrl());
                        writeWithException(dos, organization.getPositions(), position -> {
                            dateStartWrite(dos, position);
                            dos.writeUTF(position.getTitle());
                            writeCheckNull(dos, position.getDescription());
                        });
                    });
                }
        }
    }

    private <KV> void writeWithException(DataOutputStream dos, Collection<KV> collection) throws IOException {
        dos.writeInt(collection.size());
        for (KV kv : collection) {
            dos.writeUTF((String) kv);
        }
    }

    private <KV> void writeWithException(DataOutputStream dos, Collection<KV> collection, ItemWrite<KV> item) throws IOException {
        dos.writeInt(collection.size());
        for (KV kv : collection) {
            item.itemWrite(kv);
        }
    }

    @FunctionalInterface
    private interface ItemWrite<KV> {
        void itemWrite(KV kv) throws IOException;
    }

    private void writeCheckNull(DataOutputStream dos, String s) throws IOException {
        dos.writeUTF(s == null ? "" : s);
    }

    private void dateStartWrite(DataOutputStream dos, Organization.Position p) throws IOException {
        dos.writeInt(p.getStartDate().getYear());
        dos.writeInt(p.getStartDate().getMonthValue());
        dos.writeInt(p.getEndDate().getYear());
        dos.writeInt(p.getEndDate().getMonthValue());
    }


    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readWithException(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readWithException(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, sectionRead(dis, sectionType));
            });
            return resume;
        }
    }

    private Section sectionRead(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection((List<String>) readWithException(dis));
            case EXPERIENCE:
            case EDUCATION:
                return new OrganizationSection((List<Organization>) readWithException(dis, () -> new Organization(
                        new Link(
                                dis.readUTF(),
                                readCheckNull(dis)),
                        (List<Organization.Position>) readWithException(dis, () -> new Organization.Position(
                                dateRead(dis),
                                dateRead(dis),
                                dis.readUTF(),
                                readCheckNull(dis))))));
        }
        return null;
    }

    private void readWithException(DataInputStream dis, ItemReads item) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            item.itemReads();
        }
    }

    @FunctionalInterface
    private interface ItemReads {
        void itemReads() throws IOException;
    }

    private Collection<String> readWithException(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        Collection<String> collection = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            collection.add(dis.readUTF());
        }
        return collection;
    }

    private <KV> Collection<KV> readWithException(DataInputStream dis, ItemRead<KV> item) throws IOException {
        int size = dis.readInt();
        Collection<KV> collection = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            collection.add(item.itemRead());
        }
        return collection;
    }

    @FunctionalInterface
    private interface ItemRead<KV> {
        KV itemRead() throws IOException;
    }

    private String readCheckNull(DataInputStream dis) throws IOException {
        String str = dis.readUTF();
        return str.equals("") ? null : str;
    }

    private LocalDate dateRead(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), Month.of(dis.readInt()), 1);
    }
}

//читать / писать лучше через switch (со сквозными case) в зависимости от SectionType
//вынеси запись / чтение даты в отд методы

/*  теперь надо зарефакторить запись всех коллекций (т.е. все for в doWrite) через функц интерфейс

  посмотри на реализацию forEach
      default void forEach(Consumer<? super T> action) {
          Objects.requireNonNull(action);
              for (T t : this) {
                  action.accept(t);
              }
          }
       }

  надо сделать что-то подобное, т.к. использование готового forEach в нашей ситуации не подходит,
  нам нужен метод который прокидывает IOException дальше, и свой кастомный функциональный интерфейс
  (как записывать каждый отд элемент коллекции) который тоже прокидывает IOException
  т.е. должен получится некий метод writeWithExeption (...) throws IOException, который как
  параметры принимает коллекцию, DataOutputStream и твой функциональный интерфейс*/