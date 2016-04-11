package com.tekenable.config.prime.db;

/**
 * Created by mark on 08/04/2016.
 *
 * Iterate over all the available tables in the datasource
 * based on a regular expression
 *
 * For each table,
 *     check does a corresponding audit table exist,
 *     if not
 *         create it based on the structure of the existing table.
 *     otherwise
 *         check that all the columns exist.
 *
 *     create or replace a trigger based on the new columns
 */
public class AuditInitializer {

    public static void initialize() {



    }
}
