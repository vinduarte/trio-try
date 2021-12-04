package com.viniciusduartelopes.triotry.rest.v1.util;

import com.viniciusduartelopes.triotry.rest.v1.model.ContactModel;
import com.viniciusduartelopes.triotry.rest.v1.model.MemberModel;
import com.viniciusduartelopes.triotry.rest.v1.model.MergeFieldsModel;
import java.util.ArrayList;
import java.util.List;

public class ContactsMembersUtil {

    public static List<MemberModel> contactsToMembers(List<ContactModel> contacts) {
        List<MemberModel> members = new ArrayList<>();

        contacts.forEach(contact -> {
            MergeFieldsModel mergeFields = new MergeFieldsModel(contact.getFirstName(), contact.getLastName());
            members.add(new MemberModel(contact.getEmail(), "subscribed", mergeFields));
        });

        return members;
    }

    public static List<ContactModel> membersToContacts(List<MemberModel> members) {
        List<ContactModel> contacts = new ArrayList<>();

        members.forEach(member -> {
            contacts.add(new ContactModel(member.getEmail_address(), member.getMerge_fields().getFNAME(), member.getMerge_fields().getLNAME()));
        });

        return contacts;
    }
}
