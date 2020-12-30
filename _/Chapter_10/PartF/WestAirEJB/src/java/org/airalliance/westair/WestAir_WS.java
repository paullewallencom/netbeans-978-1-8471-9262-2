/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.airalliance.westair;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ejb.Stateless;

/**
 *
 * @author TOSHIBA
 */
@WebService()
@Stateless()
public class WestAir_WS {

/**
     * Web service operation
     */
    @WebMethod(operationName = "processReservation")
    public String processReservation(@WebParam(name = "firstName")
    String firstName, @WebParam(name = "lastName")
    String lastName, @WebParam(name = "source")
    String source, @WebParam(name = "destination")
    String destination, @WebParam(name = "travelDate")
    String travelDate, @WebParam(name = "seatPreference")
    String seatPreference, @WebParam(name = "foodPreference")
    String foodPreference, @WebParam(name = "seqID")
    int seqID, @WebParam(name = "guestID")
    String guestID) {
        //TODO write your implementation code here:
        return "Reservation Processed From WestAir";
    }
}
