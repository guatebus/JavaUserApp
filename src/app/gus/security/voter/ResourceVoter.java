package app.gus.security.voter;

import java.util.HashMap;
import java.util.Map;

/**
 * Voter pattern to determine if a user is allowed access to a resource.
 */
public class ResourceVoter implements ResourceVoterInterface {

    /**
	 * THIS DATA SHOULD BE STORED BY A PERSISTENCE COMPONENT
	 * 
     * Maps user ids to roles
     * ** In production, this data would be in the persistence layer (DB) **
     */
    private static final Map<String, String> userRole;
    static
    {
    	userRole = new HashMap<String, String>();
    	userRole.put("1", "PAG_1");
    	userRole.put("2", "PAG_2");
    	userRole.put("3", "PAG_3");
    }

    /**
	 * THIS DATA SHOULD BE STORED BY A PERSISTENCE COMPONENT
	 * 
     * Maps resource (page) ids to roles required to view it
     * ** In production, this data would be in the persistence layer (DB) **
     */
    private static final Map<String, String> resourceRole;
    static
    {
    	resourceRole = new HashMap<String, String>();
    	resourceRole.put("1", "PAG_1");
    	resourceRole.put("2", "PAG_2");
    	resourceRole.put("3", "PAG_3");
    }
    
    public boolean doVote(String userID, String resourceID) {
        String roleRequesting = userRole.get(userID);
        
    	if (roleRequesting != null) {
    		String roleRequiredForResource = resourceRole.get(resourceID);
        	if (roleRequesting.equals(roleRequiredForResource)) {
        		return true;
        	}
    	}
    	
    	return false;
    }

}
