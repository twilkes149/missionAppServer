package test;

import serverModel.AuthTokenTest;
import serverModel.EventTest;
import serverModel.LocationTest;
import serverModel.PersonTest;
import serverModel.UserTest;

public class TestDriver {

    public static void main(String[] args) {
    	/*AuthTokenTest authTest = new AuthTokenTest();
    	LocationTest locationTest = new LocationTest();
    	EventTest eventTest = new EventTest();
    	UserTest userTest = new UserTest();
    	PersonTest personTest = new PersonTest();
    	
    	authTest.test();
    	
    	locationTest.test();
    	
    	eventTest.testConstructor();
    	eventTest.testGettingLocation();
    	
    	personTest.testConstructor();
    	personTest.testEvent();
    	personTest.testParents();
    	
    	userTest.testConstructor();
    	userTest.testEvent();*/
    	
    	if (args.length > 1) {
            System.out.println("*host: " + args[0]);
            System.out.println("*port: " + args[1]);
            
            System.setProperty("host", args[0]);
            System.setProperty("port", args[1]);
            
        }
    	
    	org.junit.runner.JUnitCore.main(
                "serverModel.AuthTokenTest", "serverModel.LocationTest", "serverModel.EventTest", 
                "serverModel.PersonTest", "serverModel.UserTest", "database.EventDAOTest", "database.LocationDAOTest", 
                "database.AuthTokenDAOTest", "database.PersonDAOTest", 
                "database.DatabaseDriverTest", "database.UserDAOTest", "serverProxy.ServerProxyTest");
    
    
        
        //org.junit.runner.JUnitCore.main("serverProxy.ServerProxyTest");
    
    }
}