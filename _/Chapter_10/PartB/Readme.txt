PartB
-----
This sample code shows how you can use the JDBC BC to update
JavaDB from the BPEL process.

How it works
------------
Whenever, a request for reservation is made, the NorthAir WS
is invoked and the itinerary data is updated in the SouthAIr DB.
through the JDBC Binding Component.

NetBeans Projects
-----------------
1. NorthAirEJB - Open this project in Netbeans. Deploy to a running
Glassfish application server. This project has one web service
by name NorthAirWS. NorthAirWS has one operation: processReservation.

2. ReservationBPEL - This project containes the BPEL and WSDL files.
After opening the project, build the project.

3. AirAlliance_SA - This project is the Service Assembly that you will
deploy to the Glassfish application server. This SA already has
ReservationBPEL as a JBI module.

IMPORTANT
---------
After opening AirAlliance_SA project in NetBeans, right click on the
project and select 'Resolve Reference Problems' Click Ressolve and select
ReservationBPEL NetBeans project from your file system.

IMPORTANT
---------
After opening AirAlliance_SA project in NetBeans, right click on the
project and select 'Resolve Reference Problems' Click Ressolve and select
ReservationBPEL NetBeans project from your file system.

If you are making changes to the ReservationBP.bpel file in
ReservationBPEL project. You need to update the JBI module again 
in this project.
Right Click this project and choose Edit Application Configuration.

You can see 3 WSDL ports are configured. 2 ports use SOAP binding and
1 port uses JDBC binding.

In NetBeans drill down to Services > Servers > Glassfish V[x] > JBI
>Binding Components.
If you do not see sun-jdbc-binding, 
download the jar from:
http://download.java.net/jbi/binaries/open-jbi-components/main/nightly/latest/ojc/

Then right click on Services > Servers > Glassfish V[x] > JBI
>Binding Components 
and choose Install Binding Components and select
the BC jar that you downloaded from the above link.


How to test
-----------
1. Setup SouthAirDB - In NetBeans select Tools > JavaDB Database > Settings.
In the Database Location Field, provide the full path to the SouthAirDB folder.
2. Tools >Java DB Database > Stop Server and Tools >Java DB Database > Start Server.
3. Under Services tab, right click Databases > New Connection.
	a. Provide the Database URL as jdbc:derby://localhost:1527/SouthAirDB
	b. Username: southair Password: southair
	c. Connect and View the record in ITINERARY table.
4. Create JDBC Resources in Glassfish
	a. In Glassfish AS, create a connection pool as shown in the book.
	b. In Glassfish AS, create a JDBC Resource with JNDI name jdbc:/southair

5. Deploy project AirAlliance_SA
6. In AirAlliance_SA project, under Test > TestReservation edit input.xml
with some new values.If you use the same record, then JavaDB will throw an exception
since guestID column is a primary key and cannot be duplicated.
7. In AirAlliance_SA project, execute the TestReservation Test Case under Test Folder.
8. Now check the Itinerary table of SouthAirDB

