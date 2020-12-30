PartA
-----
We will create a simple web service for NorthAir Airlines from an EJB project. We will create a business process that sends guest itinerary information to the web service and receives a confirmation. Basic BPEL activities including receive, assign, invoke and reply are depicted through this part. 

How it works
------------
Whenever, a request for reservation is made, the NorthAir WS
is invoked. The NorthAir WS confirms the reservation with a success message.

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

You can see 2 WSDL ports are configured with SOAP bindings.

How to test
-----------
5. Deploy project AirAlliance_SA
6. In AirAlliance_SA project, under Test > TestReservation edit input.xml
with some new values.If you use the same record, then JavaDB will throw an exception
since guestID column is a primary key and cannot be duplicated.
7. In AirAlliance_SA project, execute the TestReservation Test Case under Test Folder.
The output.xml under Test > TestReservation should be similiar to the following output:

-------------------------------------------------------------------------------------------------------------------------------------------------
<?xml version="1.0" encoding="UTF-8"?>
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://schemas.xmlsoap.org/soap/envelope/ http://schemas.xmlsoap.org/soap/envelope/">
  <SOAP-ENV:Body>
    <someNS:processItineraryResponse xmlns:someNS="http://northair.airalliance.org/">
      <return xmlns:msgns="http://northair.airalliance.org/" xmlns:ns2="http://northair.airalliance.org/" xmlns="">Processed Reservation</return>
    </someNS:processItineraryResponse>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
-------------------------------------------------------------------------------------------------------------------------------------------------

