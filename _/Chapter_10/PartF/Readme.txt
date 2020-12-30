PartF
-----
This is the last part of the sample provided in the book.
The reservation process is updated to perform preprocessing before
invoking the partner services. This will be useful later
for building your own validation rules before invoking 
the partner services. AAPreProcessor WS performs a check
on the sectors and sends a reply back to the process stating
if the reservation could be processed. For this example
all reservation requests form 'BLR' to "SFO' are
rejected and and auto responder is sent to a predefined
Email address.
The preprocessing also includes checking if the source and destination 
sectors are same before ivoking the expensive partner services query
operations. You should have more of these checks in your real
applications.

How it works
------------
Whenever, a request for reservation is made, the AAPreProcessor WS 
is invoked. If flights are not available for the requested source
and destination, the BPEL process aborts after sending an Email
to a wait queue or a predefined address. The Email is sent through
an SMTP BC.

NetBeans Projects
-----------------
1. NorthAirEJB - Open this project in Netbeans. Deploy to a running
Glassfish application server. This project has one web service
by name NorthAirWS. NorthAirWS has one operation: processReservation.

2. WestAirEJB - Open this project in Netbeans. Deploy to a running
Glassfish application server. This project has one web service
by name WestAirWS. WestAirWS has one operation: processReservation.

3. AAPreProcessorEJB - Open this project in Netbeans. Deploy to a running
Glassfish application server. This project has one web service
by name AAPreProcessorWS. AAPreProcessorWS has one operation: areSectorsAvailable

4. ReservationBPEL - This project containes the BPEL and WSDL files.
After opening the project, build the project.

5. AirAlliance_SA - This project is the Service Assembly that you will
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

You can see 9 WSDL ports are configured. 4 ports use SOAP binding,
1 port uses JDBC binding, 1 port uses File binding, 1 port
uses JMS binding, 1 port uses SMTP binding and 1 more port uses FTP binding.

In NetBeans drill down to Services > Servers > Glassfish V[x] > JBI
>Binding Components.
If you do not see sun-file-binding, sun-jdbc-binding, sun-ftp-binding,
sun-smtp-binding and sun-jms-binding, download the jars from:
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

5. Deploy projects NorthAirEJB, WestAirEJB, AAPreProcessorEJB and AirAlliance_SA
6. In AirAlliance_SA project, under Test > TestReservation edit input.xml
with some new values.If you use the same record, then JavaDB will throw an exception
since guestID column is a primary key and records cannot be duplicated.
7. In AirAlliance_SA project, execute the TestReservation Test Case under Test Folder.
8. Now check the Itinerary table of SouthAirDB. Also check the test.xml file under
IStore folder. Check if the itinerary information is uploaded to the FTP server
running on the localhost or specified in the FTP URL provided
in the additional configuration section.

Note: If you enter the source as 'BLR' and destination as 'SFO', then
the process fails to proceed and mail will be sent to a configured Email address.
Also if you have the same values for source and destination, the process stops
processing the itinerary.

9 Open Glassfish Applicaion Server's Admin Console and go to:
Configuration > Java Message Service > Physical Destinations and check if 
EastAirQueue has been added as a destination.
10, Check if you get the reservation confirmation from NorthAir WS if the
destination is 'SFO'. If the destination is not 'SFO' the reservation
confirmation comes from WestAir WS.

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


B. Configuring JMS BC
---------------------
Open SendItinerary.wsdl file under ReservationBPEL > Process Files
in NetBeans.
Note the following the code snippet:
-------------------------------------------------------------------------------------
    <binding name="SendItineraryBinding" type="tns:SendItineraryPortType">
        <jms:binding/>
        <operation name="SendItineraryOperation">
            <jms:operation destination="EastAirQueue" destinationType="Queue"/>
            <input name="input1">
                <jms:message messageType="TextMessage" textPart="inputItinerary"/>
            </input>
        </operation>
    </binding>
-------------------------------------------------------------------------------------
The JMS Queue name for EastAir Airlines is EastAirQueue. If you have already
configured a physical destination in Glassfish, modify this destination name.
If you do not have a queue created, Glassfish will create a new queue by name
EastAirQueue upon receiving the first message. 
In an enterprise scenario, EastAir will already have a reachable Physical 
Destination that will not expire.

Look out for the following snippet in the same file:
-------------------------------------------------------------------------------------
    <service name="SendItineraryService">
        <port name="SendItineraryPort" binding="tns:SendItineraryBinding">
            <jms:address connectionURL="mq://localhost:7676" username="admin" password="admin"/>
        </port>
    </service>
-------------------------------------------------------------------------------------
If you are running an external MQ Server like Sun Java System Message Queue, 
provide the correct connection URL and authentication information.


C. Configuring FTP Binding
--------------------------
Open UploadItinerary.wsdl file under ReservationBPEL > Process Files
in NetBeans.
Note the following the code snippet:
-------------------------------------------------------------------------------------
    <binding name="UploadItineraryBinding" type="tns:UploadItineraryPortType">
        <ftp:binding/>
        <operation name="UploadItineraryOperation">
            <ftp:operation/>
            <input name="input1">
                <ftp:message messageName="" 
			     messageNamePrefixIB="" 
			     messageNamePrefixOB="" 
			     pollIntervalMillis="5000" 
			     archive="true" protect="true" stage="true" use="literal" 			     encodingStyle="" messageCorrelate="true" 			     messageRepository="aapartner\itinerary"/>
            </input>
        </operation>
    </binding>
-------------------------------------------------------------------------------------
You can change the messageRepository value to any folder name. Thid folder will be created
in the home dir configured for the FTP user acct.

Look out for the following snippet in the same file:
-------------------------------------------------------------------------------------
    <service name="UploadItineraryService">
        <port name="UploadItineraryPort" binding="tns:UploadItineraryBinding">
            <ftp:address url="ftp://aapartner:aapartner@localhost:21" dirListStyle="UNIX" 			 			 useUserDefinedHeuristics="false" userDefDirListStyle="" 			 			 userDefDirListHeuristics="" mode="BINARY" cmdChannelTimeout="45000" 		 			 dataChannelTimeout="45000"/>
        </port>
    </service>
-------------------------------------------------------------------------------------
In the url field, the syntax should be ftp://<user>:<password>@<host>:<port>


After running the TestReservation test case, examing the aapartner\itinerary directory
in the FTP Server. There will be an inbox subfolder that will contain the 
itinerary information mentioned in the input.xml.

D. Configuring SMTP Binding Component
-------------------------------------
Open MailResponder.wsdl file under ReservationBPEL > Process Files
in NetBeans.
Note the following the code snippet:
-------------------------------------------------------------------------------------
    <service name="MailResponderService">
        <port name="MailResponderPort" binding="tns:MailResponderBinding">
            <smtp:address location="mailto:frank@jennings.in" smtpserver="jennings.in" useSSL="false" 			  username="frank@jennings.in" password="************"/>
        </port>
    </service>
-------------------------------------------------------------------------------------
You can configure your SMTP server information above.

IMPORTANT.................................................................................
After making changes to the WSDL files in ReservationBPEL project, you need 
to build and redploy the project to a Service Assembly. Right click ReservationBPEL and click Clean and Build. Go to AirAlliance_SA > JBI Modules and
delet ReservationBPEL.jar. Now right click AirAlliance_SA > Add JBI Module and select the 
ReservationBPEL project. Now Right click AirAlliance_SA and select Clean and Build and then
select Deploy.
..........................................................................................

