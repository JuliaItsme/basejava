package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                if (entry.getKey().equals(SectionType.PERSONAL) || entry.getKey().equals(SectionType.OBJECTIVE)) {
                    dos.writeUTF(((TextSection) entry.getValue()).getContent());
                }

                if (entry.getKey().equals(SectionType.ACHIEVEMENT) || entry.getKey().equals(SectionType.QUALIFICATIONS)) {
                    List<String> str = ((ListSection) entry.getValue()).getItems();
                    dos.writeInt(str.size());
                    for (String s : str) {
                        dos.writeUTF(s);
                    }
                }

                if (entry.getKey().equals(SectionType.EXPERIENCE) || entry.getKey().equals(SectionType.EDUCATION)) {
                    List<Organization> org = ((OrganizationSection) entry.getValue()).getOrganizations();
                    dos.writeInt(org.size());
                    for (Organization o : org) {
                        Link link = o.getHomePage();
                        dos.writeUTF(link.getName());
                        dos.writeUTF(link.getUrl() == null ? "" : link.getUrl());

                        List<Organization.Position> pos = o.getPositions();
                        dos.writeInt(pos.size());
                        for (Organization.Position p : pos) {
                            dos.writeInt(p.getStartDate().getYear());
                            dos.writeInt(p.getStartDate().getMonthValue());
                            dos.writeInt(p.getEndDate().getYear());
                            dos.writeInt(p.getEndDate().getMonthValue());
                            dos.writeUTF(p.getTitle());
                            dos.writeUTF(p.getDescription() == null ? "" : p.getDescription());
                        }
                    }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            for (int i = 0; i < dis.readInt(); i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            for (int i = 0; i < dis.readInt(); i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, section(dis, sectionType));
            }
            return resume;
        }
    }

    private Section section(DataInputStream dis, SectionType sectionType) throws IOException {
        if (sectionType.equals(SectionType.PERSONAL) || sectionType.equals(SectionType.OBJECTIVE)) {
            return new TextSection(dis.readUTF());
        }
        if (sectionType.equals(SectionType.ACHIEVEMENT) || sectionType.equals(SectionType.QUALIFICATIONS)) {
            List<String> str = new ArrayList<>();
            for (int i = 0; i < dis.readInt(); i++) {
                str.add(dis.readUTF());
            }
            return new ListSection(str);
        }
        if (sectionType.equals(SectionType.EXPERIENCE) || sectionType.equals(SectionType.EDUCATION)) {
            List<Organization> org = new ArrayList<>(dis.readInt());
            List<Organization.Position> pos = new ArrayList<>();
            Organization.Position position = null;
            for (int i = 0; i < dis.readInt(); i++) {
                String name = dis.readUTF();
                String url = dis.readUTF();
                position.setStartDate(LocalDate.of(dis.readInt(), Month.of(dis.readInt()), 1));
                position.setEndDate(LocalDate.of(dis.readInt(), Month.of(dis.readInt()), 1));
                position.setTitle(dis.readUTF());
                position.setDescription(dis.readUTF());
                pos.add(new Organization.Position(position.getStartDate(), position.getEndDate(), position.getTitle(), position.getDescription()));
                org.add(new Organization(new Link(name, url), pos));
            }
            return new OrganizationSection(org);
        }
        return null;
    }
}