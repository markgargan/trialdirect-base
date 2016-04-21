package com.tekenable.trialdirect.audit;

import com.tekenable.trialdirect.config.AuditConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AuditConfig.class})
public class AuditConfigVerificationTest {

    @Autowired
    protected DataSource dataSource;

    protected static final Logger log = LoggerFactory.getLogger(AuditConfiguratorTest.class);

    @Test
    public void verifyAuditConfiguration() {
        log.info("*** AUDIT CONFIGURATOR VERIFICATION START ***");
        log.info(" ");
        log.info("This test verifies audit configuration run against applicaiton intallation");
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        Map<String, Integer> stats = new HashMap();

        String countTables = "select count(*) from information_schema.tables\n" +
                             "where table_schema = 'trialdirect' " +
                             "and table_name <> 'hibernate_sequences'" + // sequence table is ouf od audit scope
                             "and table_name not like '%_audit'";
        Integer tabCount = jdbc.queryForObject(countTables, Integer.class);
        log.info("Overall number of audited tables: "+tabCount);
        stats.put("tabCount", tabCount); //11

        String countColumns = "select count(*) from information_schema.columns\n" +
                              "where table_schema = 'trialdirect'\n" +
                              "and table_name <> 'hibernate_sequences'\n" +
                              "and table_name not like '%_audit'";
        Integer colCount = jdbc.queryForObject(countColumns, Integer.class);
        log.info("Overall number of audited columns: "+colCount);
        stats.put("colCount", colCount); //47


        String auditTables = "select count(*) from information_schema.tables\n" +
                             "where table_schema = 'trialdirect' \n" +
                             "and table_name like '%_audit'";
        tabCount = jdbc.queryForObject(auditTables, Integer.class);
        log.info("Overall number of audit tables created(present): "+tabCount);
        assertTrue(stats.get("tabCount")==tabCount);

        String auditColumns = "select count(*) from information_schema.columns\n" +
                              "where table_schema = 'trialdirect'\n" +
                              "and table_name <> 'hibernate_sequences'\n" +
                              "and table_name like '%_audit'\n" +
                              "and column_name not in ('action', 'dbUser', 'appUser', 'createdTs')";
        colCount = jdbc.queryForObject(auditColumns, Integer.class);
        log.info("Overall number of audit columns created(present): "+colCount);
        assertTrue(stats.get("colCount")==colCount);

        String countTriggers = "select count(*) from information_schema.triggers\n" +
                               "where trigger_schema = 'trialdirect'";
        Integer trgCount = jdbc.queryForObject(countTriggers, Integer.class);
        log.info("Overall number of triggers created should be 3 times higher thatn the number of audited tables: "+trgCount);
        assertTrue(tabCount*3 == trgCount);
    }
}
