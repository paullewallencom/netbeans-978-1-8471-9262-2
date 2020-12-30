/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.airalliance.northair;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ejb.Stateless;

/**
 *
 * @author frank
 */
@WebService()
@Stateless()
public class NorthAirWS {

/**
     * Web service operation
     */
    @WebMethod(operationName = "processItinerary")
    public String processItinerary(@WebParam(name = "firstName")
    String firstName, @WebParam(name = "lastName")
    String lastName, @WebParam(name = "source")
    String source, @WebParam(name = "destination")
    String destination, @WebParam(name = "travelDate")
    String travelDate, @WebParam(name = "seatPreference")
    String seatPreference, @WebParam(name = "foodPreference")
    String foodPreference, @WebParam(name = "guestID")
    String guestID, @WebParam(name = "seqID")
    int seqID) {
        //TODO write your implementation code here:
        return "Reservation Processed From NorthAir";
    }

}
