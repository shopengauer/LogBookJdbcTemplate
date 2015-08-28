package ru.matritca.jdbctemplate.repository.users.organization;

import ru.matritca.jdbctemplate.domain.users.Jobtitle;
import ru.matritca.jdbctemplate.domain.users.Organization;

import java.util.List;

/**
 * Created by Vasiliy on 23.08.2015.
 */
public interface OrganizationDao {

    int addOrganization(Organization organization);
    int[] addListOfOrganization(List<Organization> organizationList);
    int addOrganizationIfNotExists(Organization jobtitle);
    boolean isOrganizationExists(String organizationName);
    Organization findOrganizationByName(String organizationName);
    Organization findOrganizationById(long id);
    List<Organization> findAllOrganizations();
    int deleteOrganizationByOrganizationName(String organizationName);
    long findOrganizationIdByOrganizationName(String organizationName);
    void deleteAllOrganizations();
    void updateOrganization(Organization newOrganization);

}
