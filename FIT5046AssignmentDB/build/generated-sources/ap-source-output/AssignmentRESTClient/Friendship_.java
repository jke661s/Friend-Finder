package AssignmentRESTClient;

import AssignmentRESTClient.Studentprofile;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-03-30T16:55:52")
@StaticMetamodel(Friendship.class)
public class Friendship_ { 

    public static volatile SingularAttribute<Friendship, String> endingdate;
    public static volatile SingularAttribute<Friendship, Integer> friendshipid;
    public static volatile SingularAttribute<Friendship, Studentprofile> studentid2;
    public static volatile SingularAttribute<Friendship, Studentprofile> studentid1;
    public static volatile SingularAttribute<Friendship, String> startingdate;

}