package AssignmentRESTClient;

import AssignmentRESTClient.Friendship;
import AssignmentRESTClient.Location;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-03-30T16:55:52")
@StaticMetamodel(Studentprofile.class)
public class Studentprofile_ { 

    public static volatile SingularAttribute<Studentprofile, String> favouritesport;
    public static volatile SingularAttribute<Studentprofile, String> firstname;
    public static volatile SingularAttribute<Studentprofile, String> address;
    public static volatile SingularAttribute<Studentprofile, String> gender;
    public static volatile SingularAttribute<Studentprofile, String> nativelanguage;
    public static volatile SingularAttribute<Studentprofile, String> monashemail;
    public static volatile SingularAttribute<Studentprofile, String> subscriptiondateandtime;
    public static volatile SingularAttribute<Studentprofile, String> studymode;
    public static volatile CollectionAttribute<Studentprofile, Friendship> friendshipCollection1;
    public static volatile SingularAttribute<Studentprofile, String> favouritemovie;
    public static volatile SingularAttribute<Studentprofile, String> password;
    public static volatile SingularAttribute<Studentprofile, String> currentjob;
    public static volatile SingularAttribute<Studentprofile, String> nationality;
    public static volatile SingularAttribute<Studentprofile, String> favouriteunit;
    public static volatile SingularAttribute<Studentprofile, String> surname;
    public static volatile SingularAttribute<Studentprofile, String> dob;
    public static volatile SingularAttribute<Studentprofile, String> course;
    public static volatile SingularAttribute<Studentprofile, String> suburb;
    public static volatile SingularAttribute<Studentprofile, Integer> studid;
    public static volatile CollectionAttribute<Studentprofile, Location> locationCollection;
    public static volatile CollectionAttribute<Studentprofile, Friendship> friendshipCollection;

}