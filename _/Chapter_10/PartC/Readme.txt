PartC
-----
This sample code shows how you can use the File BC 
along with JDBC BC to store itinerary data in JavaDB 
and in the file system.

How it works
------------
Whenever, a request for reservation is made, the NorthAir WS
is invoked and the itinerary data is updated in the SouthAIr DB.
Also the itinerary information is updated in the file system using
the JBI File BC. This file can be constantly monitored by an external
process.

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


If you are making changes to the ReservationBP.bpel file in
ReservationBPEL project. You need to update the JBI module again 
in this project.
Right Click this project and choose Edit Application Configuration.

You can see 4 WSDL ports are configured. 2 ports use SOAP binding,
1 port uses JDBC binding and 1 port uses File binding.

In NetBeans drill down to Services > Servers > Glassfish V[x] > JBI
>Binding Components.
If you do not see sun-file-binding and sun-jdbc-binding, download the jars from:
http://download.java.net/jbi/binaries/open-jbi-components/main/nightly/latest/ojc/

Then right click on Services > Servers > Glassfish V[x] > JBI
>Binding Components 
and choose Install Binding Components and select
the BC jars that you downloaded from the above link.


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
8. Now check the Itinerary table of SouthAirDB. Also check the test.xml file under
IStore folder.

Additional Configuration
------------------------
A. Configuring File BC
----------------------
Open StoreItinerary.wsdl file under ReservationBPEL > Process Files
in NetBeans.
Note the following the code snippet:
-------------------------------------------------------------------------------------
<binding name="StoreItineraryBinding" type="tns:StoreItineraryPortType">
        <file:binding/>
        <operation name="StoreItineraryOperation">
            <file:operation/>
            <input name="input1">
                <file:message use="literal" fileName="GuestItinerary.xml" 
			      pollingInterval="1000" multipleRecordsPerFile="true" 
			      recordDelimiter="\r\n" addEOL="true"/>
            </input>
        </operation>
</binding>
-------------------------------------------------------------------------------------
GuestItinerary.xml will be the name of the file that will be created in IStore folder.
-------------------------------------------------------------------------------------
<service name="StoreItineraryService">
        <port name="StoreItineraryPort" binding="tns:StoreItineraryBinding">
            <file:address fileDirectory="E:\work\AirAlliance\IStore" 
			  lockName="filebc.lck" 
			  workArea="filebc_tmp" 
                          seqName="filebc.seq"/>
        </port>
</service>
-------------------------------------------------------------------------------------
Change the fileDirectory path to suite your system. 

IMPORTANT.................................................................................
After making changes to the WSDL files in ReservationBPEL project, you need 
to build and redploy the project to a Service Assembly. Right click ReservationBPEL and click Clean and Build. Go to AirAlliance_SA > JBI Modules and
delet ReservationBPEL.jar. Now right click AirAlliance_SA > Add JBI Module and select the 
ReservationBPEL project. Now Right click AirAlliance_SA and select Clean and Build and then
select Deploy.
..........................................................................................






