package com.viniciusduartelopes.triotry.util;

import com.viniciusduartelopes.triotry.model.ContactDTO;
import com.viniciusduartelopes.triotry.model.MemberDTO;
import com.viniciusduartelopes.triotry.model.MergeFieldsDTO;
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
            contacts.add(new ContactDTO(member.getEmail_address(), member.getMerge_fields().getFNAME(), member.getMerge_fields().getLNAME()));
        });

        return contacts;
    }
}
