package com.napier.sem;

import java.sql.Connection;

/**
 * Query interface for generic query execution in CLI
 */
public interface Query {
    /**
     * @return Name of query
     */
    String name();
    /**
     * @return Description of query
     */
    String description();


    /**
     * @return True if a size N is required
     */
    boolean needsN();
    /**
     * @return True if a Region size is required
     */
    boolean needsRegion();

    /**
     * Executes the query using provided connection
     */
    void execute(Connection con, int n, Region area);
}
