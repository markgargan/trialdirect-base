package com.tekenable.config;

import com.tekenable.model.audit.TrialDirectTableDef;
import com.tekenable.trialdirect.config.AuditConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AuditConfig.class})
public class AuditConfiguratorTest {

    private AuditConfigurator auditConfigMock;
    private JdbcTemplate jdbcMock;

    protected static final Logger log = LoggerFactory.getLogger(AuditConfiguratorTest.class);

    @Before
    public void init() {
        log.info("*** start test ***");
        this.auditConfigMock = Mockito.mock(AuditConfigurator.class);
        this.jdbcMock = Mockito.mock(JdbcTemplate.class);
    }

    @After
    public void finish() {
        log.info("*** end of test ***");
    }


    @Value("classpath:sql/Answer_audit.sql")
    private Resource createAnswerAuditTable;

    private String getStatementFromFile(Resource resource) throws IOException {
        File file = resource.getFile();
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return new String(data, "UTF-8");
    }

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    public void testCheckAuditConfiguration() {
        log.info("testing the main audit method");

        List<String> tables = new ArrayList();
        tables.add("Answer");
        tables.add("User");
        when(this.auditConfigMock.getAuditedTables()).thenReturn(tables);

        List<TrialDirectTableDef> answerColumns = new ArrayList();
        answerColumns.add(new TrialDirectTableDef("id", "id int(11)"));
        answerColumns.add(new TrialDirectTableDef("sortOrder", "sortOrder int(11)"));
        answerColumns.add(new TrialDirectTableDef("answerText", "answerText varchar(255)"));
        when(this.auditConfigMock.getTableColumns("Answer")).thenReturn(answerColumns);

        List<TrialDirectTableDef> userColumns = new ArrayList();
        TrialDirectTableDef idCol = new TrialDirectTableDef("id", "id int(11)");
        userColumns.add(idCol);
        TrialDirectTableDef soCol = new TrialDirectTableDef("sortOrder", "sortOrder int(11)");
        userColumns.add(soCol);
        userColumns.add(new TrialDirectTableDef("pseudonym", "pseudonym varchar(300)"));
        userColumns.add(new TrialDirectTableDef("realName", "realName varchar(255)"));
        TrialDirectTableDef pseudoCol = new TrialDirectTableDef("pseudonym", "pseudonym varchar(255)");

        when(this.auditConfigMock.getTableColumns("User")).thenReturn(userColumns);

        when(this.auditConfigMock.setJdbcTemplate(this.jdbcMock)).thenCallRealMethod();
        when(this.auditConfigMock.checkAuditConfiguration()).thenCallRealMethod();

        when(this.auditConfigMock.checkTablePresence("Answer_audit")).thenReturn(0);
        when(this.auditConfigMock.getCreateTableQuery("Answer_audit", answerColumns)).thenCallRealMethod();

        when(this.auditConfigMock.getDropTriggerQuery("Answer", "_AI")).thenCallRealMethod();
        when(this.auditConfigMock.getDropTriggerQuery("Answer", "_AU")).thenCallRealMethod();
        when(this.auditConfigMock.getDropTriggerQuery("Answer", "_AD")).thenCallRealMethod();

        Map<String, String> operations = new HashMap();
        operations.put("_AI", "INSERT");
        operations.put("_AU", "UPDATE");
        operations.put("_AD", "DELETE");

        for (Map.Entry<String, String> operation : operations.entrySet()) {
            when(this.auditConfigMock.getCreateTriggerQuery("Answer", "Answer_audit", operation, answerColumns)).thenCallRealMethod();
        }

        when(this.auditConfigMock.checkTablePresence("User_audit")).thenReturn(1);
        when(this.auditConfigMock.getColumnDefinition("User_audit", "id")).thenReturn(idCol);
        when(this.auditConfigMock.getColumnDefinition("User_audit", "sortOrder")).thenReturn(soCol);
        when(this.auditConfigMock.getColumnDefinition("User_audit", "pseudonym")).thenReturn(pseudoCol);
        when(this.auditConfigMock.getColumnDefinition("User_audit", "realName")).thenReturn(null);
        when(this.auditConfigMock.getAlterTableQuery("add", "User_audit", "realName varchar(255)")).thenCallRealMethod();
        when(this.auditConfigMock.getAlterTableQuery("modify", "User_audit", "pseudonym varchar(300)")).thenCallRealMethod();

        this.auditConfigMock.setJdbcTemplate(this.jdbcMock);
        Map<String, List<String>> statements = this.auditConfigMock.checkAuditConfiguration();
        assertTrue(statements.size()==3);

        List<String> auditedTables = statements.get("AuditedTables");
        try {
            String statementFromFile = this.getStatementFromFile(createAnswerAuditTable);
            String statementToVerify = auditedTables.get(0);
            assertEquals(statementFromFile, statementToVerify);

        } catch (IOException e) {
            e.printStackTrace();
        }

        ResourcePatternResolver resolver = org.springframework.core.io.support.ResourcePatternUtils.getResourcePatternResolver(resourceLoader);

        List<String> auditedTriggers = statements.get("TriggersRecreated");
        Iterator<String> it = auditedTriggers.iterator();

        for (Map.Entry<String, String> operation : operations.entrySet()) {

            // drop trigger check
            Resource dropTriggerResource = resolver.getResource("classpath:sql/drop_trigger_Answer".concat(operation.getKey()).concat(".sql"));
            String statementFromFile = null;
            String auditedTrigger = null;
            try {
                statementFromFile = getStatementFromFile(dropTriggerResource);
                auditedTrigger = it.next();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assertEquals(auditedTrigger, statementFromFile);

            // create trigger check
            Resource createTriggerResource = resolver.getResource("classpath:sql/create_trigger_Answer".concat(operation.getKey()).concat(".sql"));
            try {
                statementFromFile = getStatementFromFile(createTriggerResource);
                auditedTrigger = it.next();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assertEquals(auditedTrigger, statementFromFile);
        }

        List<String> alteredTables = statements.get("TablesAltered");
        for (int i=0; i<alteredTables.size(); i++) {
            String statementToVerify = alteredTables.get(i);
            Resource alterTableResource = resolver.getResource("classpath:sql/User_audit_alter".concat(String.valueOf(i+1)).concat(".sql"));
            String statementFromFile = null;
            try {
                statementFromFile = getStatementFromFile(alterTableResource);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assertEquals(statementFromFile, statementToVerify);
        }
    }

}