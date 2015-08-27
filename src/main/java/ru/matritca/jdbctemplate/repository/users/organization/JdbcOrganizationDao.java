package ru.matritca.jdbctemplate.repository.users.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;
import ru.matritca.jdbctemplate.domain.users.Organization;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Vasiliy on 23.08.2015.
 */
@Repository
public class JdbcOrganizationDao implements OrganizationDao{


    @Autowired
    private NamedParameterJdbcOperations namedParameterJdbcTemplate;

    private static final class OrganizationMapper implements RowMapper<Organization>{
        @Override
        public Organization mapRow(ResultSet resultSet, int i) throws SQLException {
            Organization organization = new Organization();
            organization.setId(resultSet.getLong("ORGANIZATION_ID"));
            organization.setOrganizationName(resultSet.getString("ORGANIZATION_NAME"));
            organization.setOrganizationDesc(resultSet.getString("ORGANIZATION_DESC"));
            return organization;
        }
    }


    @Override
    public int addOrganization(Organization organization) {
        String sql = "insert into users.organization (organization_id,organization_name,organization_desc) values (nextval('USERS_SEQUENCE'),:organizationName,:organizationDesc)";
        SqlParameterSource parameterSource = new MapSqlParameterSource("organizationName",organization.getOrganizationName())
                .addValue("organizationDesc", organization.getOrganizationDesc());
        return namedParameterJdbcTemplate.update(sql, parameterSource);
    }

    @Override
    public int[] addListOfOrganization(List<Organization> organizationList) {
        String sql = "insert into users.organization (organization_id,organization_name,organization_desc) values (nextval('USERS_SEQUENCE'),:organizationName,:organizationDesc)";
        SqlParameterSource[] parameterSource = SqlParameterSourceUtils.createBatch(organizationList.toArray());
        return namedParameterJdbcTemplate.batchUpdate(sql, parameterSource);
    }

    @Override
    public Organization findOrganizationByName(String organizationName) {
        String sql = "select * from users.organization where organization_name = :organizationName";
        SqlParameterSource parameterSource = new MapSqlParameterSource("organizationName",organizationName);
        return namedParameterJdbcTemplate.queryForObject(sql,parameterSource,new OrganizationMapper());
    }

    @Override
    public Organization findOrganizationById(long id) {
       String sql = "select * from users.organization where organization_id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id",id);
        return namedParameterJdbcTemplate.queryForObject(sql, parameterSource, new OrganizationMapper());
    }

    @Override
    public List<Organization> findAllOrganizations() {
        String sql = "select * from users.organization";
        return namedParameterJdbcTemplate.query(sql, new OrganizationMapper());
    }

    @Override
    public int deleteOrganizationByOrganizationName(String organizationName) {
        String sql = "delete users.organization where organization_name = :organizationName";
        SqlParameterSource parameterSource = new MapSqlParameterSource("organizationName",organizationName);
        return namedParameterJdbcTemplate.update(sql, parameterSource);
    }

    @Override
    public long findOrganizationIdByOrganizationName(String organizationName) {
        String sql = "select organization_id from users.organization where organization_name=:organizationName";
        SqlParameterSource parameterSource = new MapSqlParameterSource("organizationName",organizationName);
        return namedParameterJdbcTemplate.queryForObject(sql,parameterSource,Long.class);
    }

    @Override
    public void deleteAllOrganizations() {
        String sql = "delete from users.organization";
        namedParameterJdbcTemplate.getJdbcOperations().execute(sql);
    }

    @Override
    public void updateOrganization(Organization newOrganization) {

        String sql = "update ";

    }
}
