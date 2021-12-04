package com.viniciusduartelopes.triotry.util;

import com.viniciusduartelopes.triotry.dto.ContactDTO;
import com.viniciusduartelopes.triotry.dto.MemberDTO;
import com.viniciusduartelopes.triotry.dto.MergeFieldsDTO;
import java.util.ArrayList;
import java.util.List;

public class ContactsMembersUtil {

    public static List<MemberDTO> contactsToMembers(List<ContactDTO> contacts) {
        List<MemberDTO> members = new ArrayList<>();

        contacts.forEach(contact -> {
            MergeFieldsDTO mergeFields = new MergeFieldsDTO(contact.getFirstName(), contact.getLastName());
            members.add(new MemberDTO(contact.getEmail(), "subscribed", mergeFields));
        });

        return members;
    }

    public static List<ContactDTO> membersToContacts(List<MemberDTO> members) {
        List<ContactDTO> contacts = new ArrayList<>();

        members.forEach(member -> {
            contacts.add(new ContactDTO(member.getEmailAddress(), member.getMergeFields().getFirstName(), member.getMergeFields().getLastName()));
        });

        return contacts;
    }
}
