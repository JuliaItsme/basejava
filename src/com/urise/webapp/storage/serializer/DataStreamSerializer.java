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
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                writeWithException(dos, ((ListSection) section).getItems(), dos::writeUTF);
                break;
            case EXPERIENCE:
            case EDUCATION:
                writeWithException(dos, ((OrganizationSection) section).getOrganizations(), organization -> {
                    dos.writeUTF(organization.getHomePage().getName());
                    writeCheckNull(dos, organization.getHomePage().getUrl());
                    writeWithException(dos, organization.getPositions(), position -> {
                        dateWrite(dos, position.getStartDate());
                        dateWrite(dos, position.getEndDate());
                        dos.writeUTF(position.getTitle());
                        writeCheckNull(dos, position.getDescription());
                    });
                });
                break;
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

    private void dateWrite(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonthValue());
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
                return new ListSection(readWithException(dis, (ItemRead<String>) dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new OrganizationSection(readWithException(dis, () -> new Organization(
                        new Link(
                                dis.readUTF(),
                                readCheckNull(dis)),
                        readWithException(dis, () -> new Organization.Position(
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

    private <KV> List<KV> readWithException(DataInputStream dis, ItemRead<KV> item) throws IOException {
        int size = dis.readInt();
        List<KV> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(item.itemRead());
        }
        return list;
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