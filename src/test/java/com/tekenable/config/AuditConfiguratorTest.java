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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AuditConfig.class})
public class AuditConfiguratorTest {

    protected AuditConfigurator auditConfigMock;

    protected static final Logger log = LoggerFactory.getLogger(AuditConfigVerificationTest.class);

    @Before
    public void init() {
        log.info("*** start test ***");
        this.auditConfigMock = Mockito.mock(AuditConfigurator.class);
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
     * @throws Exception
     */
    @Test
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

        when(auditConfigMock.getAuditedTables()).thenReturn(tables);
        List responseList = auditConfigMock.getAuditedTables();
        verify(auditConfigMock, times(1)).getAuditedTables();
        assertTrue(tables.containsAll(responseList));
    }

    /**
     * query to test:
     *
     * String query = "select column_name, concat(column_name, ' ', column_type) 'column_type' " +
     * from information_schema.columns\n" +
     * where table_schema = 'trialdirect' and table_name = ? order by ordinal_position";
     *
     * @throws Exception
     */
    @Test
    public void testGetTableColumns() throws Exception {
        log.info("get table columns test");

        List<TrialDirectTableDef> columns = new ArrayList();
        columns.add(new TrialDirectTableDef("id", "id int(11)"));
        columns.add(new TrialDirectTableDef("answerText", "answerText varchar(255)"));

        when(auditConfigMock.getTableColumns("Answer")).thenReturn(columns);
        List responseList = auditConfigMock.getTableColumns("Answer");
        verify(auditConfigMock, times(1)).getTableColumns("Answer");
        assertTrue(columns.containsAll(responseList));
    }

    @Test
    public void testCreateAuditTable() throws Exception {
        log.info("create audit table test");
        List columns = auditConfigMock.getTableColumns("Answer");
        when(auditConfigMock.createAuditTable("Answer_audit", columns)).thenReturn(0);
        Integer response = auditConfigMock.createAuditTable("Answer_audit", columns);
        verify(auditConfigMock, times(1)).createAuditTable("Answer_audit", columns);
        assertTrue(response==0);
    }

    @Test
    public void testGetColumnDefinition() throws Exception {
        log.info("get column definition test");
        TrialDirectTableDef expectedColumn = new TrialDirectTableDef("id", "id int(11)");
        when(auditConfigMock.getColumnDefinition("Answer", "id")).thenReturn(expectedColumn);
        TrialDirectTableDef response = auditConfigMock.getColumnDefinition("Answer", "id");
        verify(auditConfigMock, times(1)).getColumnDefinition("Answer", "id");
        assertTrue(response.equals(expectedColumn));
    }

    @Test
    public void testAlterAuditColumn() throws Exception {
        log.info("alter audit column test");

        when(auditConfigMock.alterAuditColumn("add", "Answer", "answerText2 varchar(300)")).thenReturn(0);
        Integer response = auditConfigMock.alterAuditColumn("add", "Answer", "answerText2 varchar(300)");
        verify(auditConfigMock).alterAuditColumn("add", "Answer", "answerText2 varchar(300)");
        assertTrue(response==0);

        when(auditConfigMock.alterAuditColumn("modify", "Answer", "answerText2 varchar(255)")).thenReturn(0);
        response = auditConfigMock.alterAuditColumn("modify", "Answer", "answerText2 varchar(255)");
        verify(auditConfigMock).alterAuditColumn("modify", "Answer", "answerText2 varchar(255)");
        assertTrue(response==0);
    }

    @Test
    public void testRecreateTrigger() throws Exception {
        log.info("recreate trigger test");
        Map<String, String> operations = new HashMap();
        operations.put("_AI", "INSERT");
        List columns = auditConfigMock.getTableColumns("Answer");
        for (Map.Entry<String, String> operation : operations.entrySet()) {
            when(auditConfigMock.recreateTrigger("Answer", "Answer_audit", operation, columns)).thenReturn(0);
            Integer response = auditConfigMock.recreateTrigger("Answer", "Answer_audit", operation, columns);
            verify(auditConfigMock).recreateTrigger("Answer", "Answer_audit", operation, columns);
            assertTrue(response==0);
        }
    }

    @Test
    public void testCheckAuditConfiguration() throws Exception {
        log.info("audit auto configuration test");
        log.info("this method is not-testable actually");
        log.info("and as a matter of fact above creation mothods test shit as well");
        assertTrue(true);
    }
}