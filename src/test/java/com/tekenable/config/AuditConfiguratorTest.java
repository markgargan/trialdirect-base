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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AuditConfig.class})
public class AuditConfiguratorTest {

    /*@Autowired
    private DataSource dataSource;*/

    //private JdbcTemplate jdbc;

    /*@Autowired
    private AuditConfigurator auditConfigurator;*/

    private AuditConfigurator auditConfigMock;
    private JdbcTemplate jdbcMock;

    protected static final Logger log = LoggerFactory.getLogger(AuditConfigVerificationTest.class);

    @Before
    public void init() {
        log.info("*** start test ***");
        //this.auditConfigurator.jdbc = new JdbcTemplate(this.auditConfigurator.dataSource);
        this.auditConfigMock = Mockito.mock(AuditConfigurator.class);
        this.jdbcMock = Mockito.mock(JdbcTemplate.class);
    }

    @After
    public void finish() {
        log.info("*** end of test ***");
    }

    /**
     * tested query:
     *
     * select table_name from information_schema.tables
     * where table_schema = 'trialdirect'
     * and table_name not in ('hibernate_sequences')
     * and table_name not like '%_audit'
     *
     * it should return the list of tables defined below
     *
     * @throws Exception
     */
    /*@Test
    public void testGetAuditedTables() throws Exception {
        log.info("get audit tables test");

        List<String> tables = new ArrayList();
        tables.add("Answer");
        tables.add("Question");
        tables.add("QuestionnaireEntry");
        tables.add("TherapeuticArea");
        tables.add("Trial");
        tables.add("TrialInfo");
        tables.add("TrialSelectorQuestionnaireEntry");
        tables.add("TrialSite");
        tables.add("User");
        tables.add("UserSelectorQuestionnaireEntry");
        tables.add("questionnaireEntry_answer");

        List<String> tablesToVerify = this.auditConfigurator.getAuditedTables();
        assertTrue(tablesToVerify.containsAll(tables));
        assertTrue(tablesToVerify.removeAll(tables));
        assertTrue(tablesToVerify.size()==0);
    }*/

    /**
     * query to test:
     *
     * String query = "select column_name, concat(column_name, ' ', column_type) 'column_type' " +
     * from information_schema.columns\n" +
     * where table_schema = 'trialdirect' and table_name = ? order by ordinal_position";
     *
     * it should return the list of columns defined below
     *
     * @throws Exception
     */
    /*@Test
    public void testGetTableColumns() throws Exception {
        log.info("get table columns test");

        List<TrialDirectTableDef> columns = new ArrayList();
        columns.add(new TrialDirectTableDef("id", "id int(11)"));
        columns.add(new TrialDirectTableDef("sortOrder", "sortOrder int(11)"));
        columns.add(new TrialDirectTableDef("answerText", "answerText varchar(255)"));

        List<TrialDirectTableDef> columnsToVerify = this.auditConfigurator.getTableColumns("Answer");

        assertTrue(columnsToVerify.containsAll(columns));
        assertTrue(columnsToVerify.removeAll(columns));
        assertTrue(columnsToVerify.size()==0);
    }*/

    /**
     * the following methods test the correctness of SQL statements generated during audit configuration
     * it is run against just 1 table but that's all the same for all of them
     *
     * @throws Exception
     */

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

    /*@Test
    public void testCreateAuditTableQuery() throws Exception {
        log.info("create Answer_audit table query test");
        List columns = this.auditConfigurator.getTableColumns("Answer");
        String queryToVerify = this.auditConfigurator.getCreateTableQuery("Answer_audit", columns);
        String statementFromFile = getStatementFromFile(createAnswerAuditTable);
        assertEquals(statementFromFile, queryToVerify);
    }*/

    @Autowired
    private ResourceLoader resourceLoader;

    /*@Test
    public void testRecreateTrigger() throws Exception {
        log.info("audit triggers for Answer table recreation test");

        ResourcePatternResolver resolver = org.springframework.core.io.support.ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        //Resource[] triggerFiles = resolver.getResources("classpath:sql*//*_trigger_Answer_*.sql");

        Map<String, String> operations = new HashMap();
        operations.put("_AI", "INSERT");
        operations.put("_AU", "UPDATE");
        operations.put("_AD", "DELETE");

        for (Map.Entry<String, String> operation : operations.entrySet()) {
            // drop trigger check
            Resource dropTriggerResource = resolver.getResource("classpath:sql/drop_trigger_Answer".concat(operation.getKey()).concat(".sql"));
            String statementFromFile = getStatementFromFile(dropTriggerResource);
            String sqlToVerify = this.auditConfigurator.getDropTriggerQuery("Answer", operation.getKey());
            assertEquals(sqlToVerify, statementFromFile);

            // create trigger check
            List columns = this.auditConfigurator.getTableColumns("Answer");
            Resource createTriggerResource = resolver.getResource("classpath:sql/create_trigger_Answer".concat(operation.getKey()).concat(".sql"));
            statementFromFile = getStatementFromFile(createTriggerResource);
            sqlToVerify = this.auditConfigurator.getCreateTriggerQuery("Answer", "Answer_audit", operation, columns);
            assertEquals(sqlToVerify, statementFromFile);
        }
    }*/

    @Test
    public void testCheckAuditConfiguration() {
        log.info("testing the main audit method");

        List<String> tables = new ArrayList();
        tables.add("Answer");
        when(this.auditConfigMock.getAuditedTables()).thenReturn(tables);


        List<TrialDirectTableDef> columns = new ArrayList();
        columns.add(new TrialDirectTableDef("id", "id int(11)"));
        columns.add(new TrialDirectTableDef("sortOrder", "sortOrder int(11)"));
        columns.add(new TrialDirectTableDef("answerText", "answerText varchar(255)"));
        when(this.auditConfigMock.getTableColumns("Answer")).thenReturn(columns);

        when(this.auditConfigMock.setJdbcTemplate(this.jdbcMock)).thenCallRealMethod();
        when(this.auditConfigMock.checkAuditConfiguration()).thenCallRealMethod();
        when(this.auditConfigMock.getCreateTableQuery("Answer", columns)).thenCallRealMethod();
        //when(this.auditConfigMock.getDropTriggerQuery(""))

        this.auditConfigMock.setJdbcTemplate(this.jdbcMock);
        Map<String, List<String>> statements = this.auditConfigMock.checkAuditConfiguration();
        assertTrue(statements.size()==3);

        List<String> auditedTables = statements.get("AuditedTables");
        tables.removeAll(auditedTables);
        //assertTrue(tables.size()==0);

        try {
            String statementFromFile = this.getStatementFromFile(createAnswerAuditTable);
            String statementToVerify = auditedTables.get(0);
            assertEquals(statementFromFile, statementToVerify);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}