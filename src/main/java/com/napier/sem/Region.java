package com.napier.sem;


/** Region Enum
 *  Used to pass region size information between the cli and queries
 */
public enum Region {
    WORLD,
    CONTINENT,
    REGION,
    COUNTRY,
    DISTRICT,


    /**
     * None field for when region is not applicable
     */
    NONE
}
